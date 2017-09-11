package cn.imzfz.experiment5;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zfz on 2017/9/11.
 */

public class Info implements Runnable {

    int i = 0;
    Message msg;
    Handler handler;


    public Info(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        msg = new Message();
        while(i < 100) {
            try {
                Thread.sleep(2000);
            }catch (Exception e ){

            }
            handler.obtainMessage();
            msg.what = i;
            handler.obtainMessage();
            handler.sendMessage(msg);
            i++;
        }
    }
}
