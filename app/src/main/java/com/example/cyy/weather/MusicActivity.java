package com.example.cyy.weather;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cyy.weather.fragment.LocalMusicFragment;
import com.example.cyy.weather.fragment.OnLineMusicFragment;
import com.example.cyy.weather.service.MusicPlayService;

/**
 * Created by cyy on 2017/1/16.
 *
 * 音乐播放
 */
public class MusicActivity extends AppCompatActivity{
    //本地，在线
    private TextView mTvLocal, mTvOnLine;
    //fragment容器
    private LinearLayout mFLContainer;
    private FragmentManager manager;
    private Fragment localMusicFragment, onLineMusicFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        showDefaultFragment();
    }

    private void initView() {
        mTvLocal = (TextView) findViewById(R.id.tv_activity_music_local);
        mTvOnLine = (TextView) findViewById(R.id.tv_activity_music_on_line);
        mFLContainer = (LinearLayout) findViewById(R.id.fl_activity_music_container);
        manager = getFragmentManager();
        localMusicFragment = new LocalMusicFragment();
        onLineMusicFragment = new OnLineMusicFragment();
    }

    private void showDefaultFragment() {
        //开始fragment事务
        FragmentTransaction action = manager.beginTransaction();
        //添加默认fragment
        action.replace(R.id.fl_activity_music_container, localMusicFragment == null ? new LocalMusicFragment() : localMusicFragment);
        //提交事务
        action.commit();
    }

    public void doClick(View v){
        //开始fragment事务
        FragmentTransaction action = manager.beginTransaction();
        if(v.getId() == R.id.tv_activity_music_local){
            mTvLocal.setTextColor(Color.BLUE);
            mTvOnLine.setTextColor(Color.GRAY);
            //当切换按钮时替换fragment
            action.replace(R.id.fl_activity_music_container, localMusicFragment == null ? new LocalMusicFragment() : localMusicFragment);
        }else if(v.getId() == R.id.tv_activity_music_on_line){
            mTvLocal.setTextColor(Color.GRAY);
            mTvOnLine.setTextColor(Color.BLUE);
            //当切换按钮时替换fragment
            action.replace(R.id.fl_activity_music_container, onLineMusicFragment == null ? new OnLineMusicFragment() : onLineMusicFragment);
        }
        //提交事务
        action.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == event.ACTION_DOWN){
           new AlertDialog.Builder(this)
                   .setTitle("您确定要退出音乐播放器吗？")
                   .setNegativeButton("No", null)
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
                           //关闭服务
                           Intent intent = new Intent(MusicActivity.this, MusicPlayService.class);
                           stopService(intent);
                       }
                   }).show();
        }
        return false;
    }
}
