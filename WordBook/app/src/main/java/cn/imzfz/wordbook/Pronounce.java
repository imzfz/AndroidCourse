package cn.imzfz.wordbook;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

/**
 * Created by zfz on 2017/10/16.
 */

public class Pronounce extends Service {
    private MediaPlayer mp;
    private String query;

    public Pronounce(){}

    @Override
    public void onCreate() {
        Log.v("audio", "begin");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        query = intent.getStringExtra("query");
        if (query != null && !query.equals(intent.getStringExtra("query")) && mp != null) {
            mp.start();
        } else {
            String query = intent.getStringExtra("query");
            Uri location = Uri.parse("http://dict.youdao.com/dictvoice?audio=" + query);

            mp = MediaPlayer.create(this, location);
            mp.start();

            // 音乐播放完毕的事件处理
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // 不循环播放
                    try {
                        // mp.start();
                        System.out.println("stopped");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 播放音乐时发生错误的事件处理
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 释放资源
                    try {
                        mp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }

        return super.onStartCommand(intent, flag, startId);
        // super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        // 服务停止时停止播放音乐并释放资源
        mp.stop();
        mp.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
