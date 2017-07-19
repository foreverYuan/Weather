package com.example.cyy.weather.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import com.example.cyy.weather.utils.ConstantUtils;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cyy on 2017/1/18.
 *
 * 音乐后台服务
 */
public class MusicPlayService extends Service{
    private MediaPlayer player;//媒体播放器对象
    private String url;//音乐播放路径
    Intent intent = new Intent();

    private Handler timeHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 123){
                if(player != null) {
                    int currentTime = player.getCurrentPosition();
                    int durarion = player.getDuration();
                    intent.setAction(ConstantUtils.TIME_ACTION);
                    intent.putExtra(ConstantUtils.CURRENT_TIME, currentTime);
                    intent.putExtra(ConstantUtils.MUSIC_DURATION, durarion);
                    sendBroadcast(intent);
                    timeHanlder.sendEmptyMessageDelayed(123, 500);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        if(player == null) {
            player = new MediaPlayer();
        }
        //实例化一个广播并注册广播
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch(action){
                    case ConstantUtils.CHANGE_MUSIC_ACTION:
                        changeMusic(intent);
                        break;
                    case ConstantUtils.PLAY_ACTION:
                        playMusic();
                        break;
                    case ConstantUtils.PAUSE_ACTION:
                        pauseMusic();
                        break;
                    case ConstantUtils.CHANGE_PROGRESS:
                        int progress = intent.getIntExtra("progress", 0);
                        player.seekTo(progress);
                        break;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantUtils.CHANGE_MUSIC_ACTION);
        filter.addAction(ConstantUtils.PLAY_ACTION);
        filter.addAction(ConstantUtils.PAUSE_ACTION);
        filter.addAction(ConstantUtils.CHANGE_PROGRESS);
        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra(ConstantUtils.CURRENT_MUSIC_URL);
        play();
//        sendMessagePeriod();
        return super.onStartCommand(intent, flags, startId);
    }

    private void play(){
        try {
            player.setDataSource(url);
            player.prepare();//资源缓冲准备中
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //资源缓冲完毕，开始播放
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    timeHanlder.sendEmptyMessageDelayed(123, 1000);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(player != null){
            player.stop();
            player = null;
        }
    }

    /**
     * 切换歌曲，包括切换上一首，下一首和点击列表的指定歌曲(非当前歌曲)
     * @param intent
     */
    private void changeMusic(Intent intent){
        url = intent.getStringExtra(ConstantUtils.CURRENT_MUSIC_URL);
        player.reset();
        try {
            player.setDataSource(url);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    /**
     *
     */
    private void playMusic(){
        player.start();
    }

    /**
     *
     */
    private void pauseMusic(){
        if(player.isPlaying()) {
            player.pause();
        }
    }

    private void sendMessagePeriod(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timeHanlder.sendEmptyMessage(123);
            }
        }, 0, 1000);
    }
}
