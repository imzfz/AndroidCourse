package cn.imzfz.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by zfz on 2017/9/8.
 */

public class KeyView extends android.support.v7.widget.AppCompatTextView {
    private Context context;
    private AttributeSet attrs;
    private ColorDrawable drawable;
    private int color;
    private String text;
    private static CallBack callBack;
    private int x, endx, y, endy, block = 0;

    public KeyView(Context context){
        super(context);
        this.context = context;
    }

    public KeyView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        drawable = (ColorDrawable) this.getBackground();
        color = drawable.getColor();
        text = (String)this.getText();
    }

    public KeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                x = (int)ev.getRawX();
                y = (int)ev.getRawY();
                this.setBackgroundColor(Color.rgb(164,164,164));
                break;

            case MotionEvent.ACTION_MOVE:
                endx = (int)ev.getRawX();
                endy = (int)ev.getRawY();
                Log.v("Move", "" + y + "  " + endy);
                if(Math.abs(endy - y) > 500 && block == 0) {
                    block = 1;
        //             Intent intent = new Intent(getContext(), Exchange.class);
                    Intent intent = new Intent(getContext(), Hex.class);
                    getContext().startActivity(intent);
                    Log.v("Fragment", "done");
                    return false;
                }
                else if(Math.abs(endx - x) > 300 && block == 0){
                    block = 1;
       //             Intent intent = new Intent(getContext(), Hex.class);
                    Intent intent = new Intent(getContext(), Rate.class);
                    getContext().startActivity(intent);
                    Log.v("Fragment", "done");
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
                block = 0;
                this.setBackgroundColor(color);
                runCallBack();
                break;
        }

        return true;
    }

    public void setCallBack(CallBack callBack){
        KeyView.callBack = callBack;
    }

    public void runCallBack(){
        callBack.onCallBack(text);
        Log.v("CallBack", text);
    }


}
