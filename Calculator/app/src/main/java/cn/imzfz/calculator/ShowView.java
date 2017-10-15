package cn.imzfz.calculator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by zfz on 2017/9/13.
 */

public class ShowView extends android.support.v7.widget.AppCompatTextView {

    static CallBack callBack;
    int x, endx, backControl = 0;//防止连续后退

    public ShowView(Context context) {
        super(context);
    }

    public ShowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = (int)event.getRawX();

            case MotionEvent.ACTION_MOVE:
                endx = (int)event.getRawX();
                Log.v("Move", "" + x + "  " + endx);
                if(Math.abs(endx - x) > 100 && backControl == 0) {
                    Log.v("Move", "callback");
                    backControl = 1;
                    runCallBack();
                }
                break;
            case MotionEvent.ACTION_UP:
                backControl = 0;
        }
        return true;
    }

    public void setCallBack(CallBack callBack){
        ShowView.callBack = callBack;
    }

    public void runCallBack(){
        callBack.showCallBack();
    }
}
