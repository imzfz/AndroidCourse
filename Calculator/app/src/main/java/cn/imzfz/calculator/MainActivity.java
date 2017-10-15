package cn.imzfz.calculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   /* TextView textView;
    Logic logic;
    String inputText, viewText;
    KeyView acKey;*/

    Logic_New logic;
    TextView textView;
    String inputText = "", nowText = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*logic = new Logic();
        textView = (TextView) findViewById(R.id.display);
        viewText = textView.getText().toString();
        acKey = (KeyView) findViewById(R.id.ac);*/

        logic = new Logic_New(getApplicationContext());
        textView = (TextView) findViewById(R.id.display);
        setTextView();
        back();
    }

    //Callback 方法 关联逻辑与控制器
    public void setTextView(){
        new KeyView(getApplicationContext()).setCallBack(new CallBack() {
            @Override
            public void showCallBack() {
                //....................
            }

            @Override
            public void onCallBack(String text) {
                /*inputText = text;
                viewText = textView.getText().toString();
                logic.setTransStr(inputText);
                logic.setviewText(viewText);
                Log.v("AAA", text);
                logic.check();
                textView.setText(logic.getViewText());
                setAC();*/
    //            textView.append(logic.getInputText());

                inputText = text;
                Log.v("Inputtext", text);
                logic.setInputText(inputText);
                logic.calculate();
                nowText = logic.getViewText();
                textView.setText(nowText);

            }
        });
    }


    //Callback 方法 关联视图与控制器
    public void back(){
        new ShowView(getApplicationContext()).setCallBack(new CallBack() {
            @Override
            public void showCallBack() {
               /* logic.back();
                textView.setText(logic.getViewText());
                setAC();*/
                logic.back();
                nowText = logic.getViewText();
                textView.setText(nowText);
            }

            @Override
            public void onCallBack(String text) {

            }
        });
    }

   /* public void setAC(){
        if(!logic.getViewText().equals("0")){
            acKey.setText("C");
        }
        if(logic.getViewText().equals("0")){
            acKey.setText("AC");
        }
    }*/
}
