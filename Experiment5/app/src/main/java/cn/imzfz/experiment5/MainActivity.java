package cn.imzfz.experiment5;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Thread count;
    Button button;
    Handler handler;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.btn);
        textView = (TextView)findViewById(R.id.show);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String a = String.valueOf(msg.what);
                textView.setText(a);
            }
        };

        button.setOnClickListener(view -> {
            count = new Thread(new Info(handler));
            count.start();
        });
    }
}
