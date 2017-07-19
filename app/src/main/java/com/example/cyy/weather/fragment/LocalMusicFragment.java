package com.example.cyy.weather.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.cyy.weather.R;
import com.example.cyy.weather.adapter.MusicAdapter;
import com.example.cyy.weather.entity.obj.LocalSongObj;
import com.example.cyy.weather.service.MusicPlayService;
import com.example.cyy.weather.utils.ConstantUtils;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/16.
 *
 * 本地音乐播放列表
 */
public class LocalMusicFragment extends Fragment{
    private View view;
    //展示列表的ListView
    private ListView mLvLocalMusic;
    //存放歌曲的list集合
    private ArrayList<LocalSongObj> songList;
    //当前音乐的名称和歌手名称
    private TextView mTvMusicName, mTvArtist;
    //进度条
    private SeekBar mSbDuration;
    //上一首，播放，下一首，切换播放模式的按钮
    private Button mBtnPrevious, mBtnPlay, mBtnNext, mBtnPattern;
    private boolean isPlaying = false;//是否是播放状态
    private int mPlayPos;//记录播放的歌曲位置
    private int pos;//记录是否是同一首歌曲，如果是同一首点击列表item暂停或者播放音乐，如果点击的item不是当前音乐就切换歌曲
    private String url;//播放的地址
    private boolean isFirstTime = true;//是否首次播放音乐
    private SharedPreferences preferences;
    private String songName, artist;//从sharedPrefrences里获取的文本信息
    private Intent intent;//broadcact传递给service的意图

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_music, null);
        initView();
        //获得手机数据库里存的音乐数据
        getSqliteMusicData();
        getSharepreData();
        //适配数据
        setData();
        setViewClick();
        receiveBroadcast();
        return view;
    }

    private void initView() {
        mLvLocalMusic = (ListView) view.findViewById(R.id.lv_fragment_local_music);
        songList = new ArrayList();
        mTvMusicName = (TextView) view.findViewById(R.id.tv_fragment_local_music_playing_music_name);
        mTvArtist = (TextView) view.findViewById(R.id.tv_fragment_local_music_artist);
        mSbDuration = (SeekBar) view.findViewById(R.id.sb_fragment_local_music_duration);
        mBtnPrevious = (Button) view.findViewById(R.id.btn_fragment_local_music_previous);
        mBtnPlay = (Button) view.findViewById(R.id.btn_fragment_local_music_play);
        mBtnNext = (Button) view.findViewById(R.id.btn_fragment_local_music_next);
        mBtnPattern = (Button) view.findViewById(R.id.btn_fragment_local_music_pattern);
        intent = new Intent();
    }

    private void getSharepreData() {
        if(preferences == null){
            preferences = getActivity().getSharedPreferences("LOCAL_MUSIC", Context.MODE_PRIVATE);
        }
        songName = preferences.getString("music_name", songList.get(0).songname);
        artist = preferences.getString("music_artist", songList.get(0).singername);
        mPlayPos = preferences.getInt("music_pos", 0);
    }

    /**
     * 为各个按钮设置点击事件，为listview设置单条点击事件
     */
    private void setViewClick() {
        ViewClickListener listener = new ViewClickListener();
        mBtnPrevious.setOnClickListener(listener);
        mBtnPlay.setOnClickListener(listener);
        mBtnNext.setOnClickListener(listener);
        mBtnPattern.setOnClickListener(listener);
        mLvLocalMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录当前音乐的位置
                mPlayPos = position;
                //点击列表中的某一项，如果是未播放状态点击后切换成播放状态，如果是播放状态切换后音乐暂停
                if(position == pos || isFirstTime) {
                    if(!isPlaying){
                        play(ConstantUtils.PLAY_ACTION, false);
                    } else{
                        pauseMusic();
                    }
                }else{
                    //如果点击的item不是当前的音乐，就切换成点击的这个位置的音乐，发送广播给service通知它切换音乐
                    play(ConstantUtils.CHANGE_MUSIC_ACTION, true);
                }
                //记录上次音乐的位置
                pos = position;
            }
        });
    }

    /**
     * 事件点击监听内部类
     */
    class ViewClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_fragment_local_music_previous://上一首
                    if(mPlayPos > 0) {
                        mPlayPos--;
                        pos = mPlayPos;
                        play(ConstantUtils.CHANGE_MUSIC_ACTION, true);
                    }
                    break;
                case R.id.btn_fragment_local_music_play://播放或者暂停
                    if(!isPlaying){
                        play(ConstantUtils.PLAY_ACTION, false);
                    } else{
                        pauseMusic();
                    }
                    break;
                case R.id.btn_fragment_local_music_next://下一首
                    if(mPlayPos < songList.size() - 1){
                        mPlayPos++;
                        pos = mPlayPos;
                        play(ConstantUtils.CHANGE_MUSIC_ACTION, true);
                    }
                    break;
                case R.id.btn_fragment_local_music_pattern://切换歌曲模式，随机，顺序，单曲
                    break;
            }
        }
    }

    /**
     * 暂停音乐
     */
    private void pauseMusic(){
        intent.setAction(ConstantUtils.PAUSE_ACTION);
        getActivity().sendBroadcast(intent);
        mBtnPlay.setBackgroundResource(R.mipmap.music_play_normal);
        isPlaying = false;
    }

    /**
     * 通过ContentResolver来获取手机里的音乐数据
     */
    private void getSqliteMusicData() {
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            LocalSongObj songObj = new LocalSongObj();
            songObj.songid = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)); //歌曲id
            songObj.songname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)); //歌曲名称
            songObj.seconds = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)); //歌曲时长
            songObj.albumid = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //专辑id
            songObj.albummid = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)); //专辑名称
            songObj.singerid = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)); //歌手id
            songObj.singername = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)); //歌手名
//            songObj.albumpic_big = getValue(cursor, MediaStore.Audio.Media.ALBUM_KEY)
            songObj.url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)); //文件路径
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); //是否为音乐
            //isMusic != 0代表是音乐
            if(isMusic != 0){
                songList.add(songObj);
            }
        }
    }

    /**
     * 适配数据
     */
    private void setData() {
        MusicAdapter adapter = new MusicAdapter(getActivity(), songList, R.layout.fragment_music_lv_item);
        mLvLocalMusic.setAdapter(adapter);
        mTvMusicName.setText(songName);
        mTvArtist.setText(artist);
    }

    /**
     * 当切换音乐时设置底部音乐名称和歌手的文案
     */
    private void setBottomText(){
        mTvMusicName.setText(songList.get(mPlayPos).songname);
        mTvArtist.setText(songList.get(mPlayPos).singername);
    }

    /**
     * 播放音乐，如果是首次播放调用startService()开启服务，如果非首次播放则通过广播与service进行交互
     * @param action
     * @param isChanged 是否切换歌曲
     */
    private void play(String action, boolean isChanged){
        if(isFirstTime) {
            //首次播放
            url = songList.get(mPlayPos).url;
            Intent intent = new Intent(getActivity(), MusicPlayService.class);
            intent.putExtra(ConstantUtils.CURRENT_MUSIC_URL, url);
            getActivity().startService(intent);
            isFirstTime = false;
            //设置底部文案
            setBottomText();
        } else {
            //不是第一次播放
            intent.setAction(action);
            intent.putExtra(ConstantUtils.CURRENT_MUSIC_URL, isChanged ? updateUrl() : url);
            getActivity().sendBroadcast(intent);
            if(isChanged){
                setBottomText();
            }
        }
        isPlaying = true;
        mBtnPlay.setBackgroundResource(R.mipmap.music_pause_normal);
    }

    /**
     * 更新url
     */
    private String updateUrl(){
        url = songList.get(mPlayPos).url;
        return url;
    }

    /**
     * 接收来自service发送的广播
     */
    private void receiveBroadcast(){
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                String action = intent.getAction();
                switch (action){
                    case ConstantUtils.TIME_ACTION:
                        int currentTime = intent.getIntExtra(ConstantUtils.CURRENT_TIME, 0);
                        int duration = intent.getIntExtra(ConstantUtils.MUSIC_DURATION, 0);
                        mSbDuration.setMax(duration);
                        mSbDuration.setProgress(currentTime);
                        mSbDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                intent.setAction(ConstantUtils.CHANGE_PROGRESS);
                                intent.putExtra("progress", seekBar.getProgress());
                                getActivity().sendBroadcast(intent);
                            }
                        });
                        break;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantUtils.TIME_ACTION);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //用sharedPreferences保存一些信息
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("music_name", mTvMusicName.getText().toString());
        edit.putString("music_artist", mTvArtist.getText().toString());
        edit.putInt("music_pos", mPlayPos);
        edit.commit();
        //置空intent
        intent = null;

    }
}
