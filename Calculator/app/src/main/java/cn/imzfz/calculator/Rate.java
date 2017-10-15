package cn.imzfz.calculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by zfz on 2017/10/12.
 */

public class Rate extends AppCompatActivity {

    Thread thread;
    EditText price11, price22;
    Spinner currency11, currency22;
    String[] type = {"EUR", "GBP", "HKD", "INR", "JPY",
            "KRW", "MOP", "TWD", "USD"};
    String type1 = "USD";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        price11 = (EditText)findViewById(R.id.price11);
        price22 = (EditText)findViewById(R.id.price22);
//        currency11 = (Spinner) findViewById(R.id.currency11);
        currency22 = (Spinner) findViewById(R.id.currency22);
        thread = new Thread(new InterNet(price11, price22, currency22));
        thread.start();
        go();
    }

    public void go(){
        currency22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        type1 = type[0];
                        break;
                    case 1:
                        type1 = type[1];
                        break;
                    case 2:
                        type1 = type[2];
                        break;
                    case 3:
                        type1 = type[3];
                        break;
                    case 4:
                        type1 = type[4];
                        break;
                    case 5:
                        type1 = type[5];
                        break;
                    case 6:
                        type1 = type[6];
                        break;
                    case 7:
                        type1 = type[7];
                        break;
                    case 8:
                        type1 = type[8];
                        break;
                }
                thread = new Thread(new InterNet(price11, price22, currency22));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
