package cn.imzfz.calculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by zfz on 2017/10/12.
 */

public class Hex extends AppCompatActivity {

    EditText value0, value2, value8, value10, value16;
    Spinner bin0;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hex_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        value0 = (EditText)findViewById(R.id.value0);
        value2 = (EditText)findViewById(R.id.value2);
        value8 = (EditText)findViewById(R.id.value8);
        value10 = (EditText)findViewById(R.id.value10);
    //    value16= (EditText)findViewById(R.id.value16);
        bin0 = (Spinner)findViewById(R.id.bin0);

        value0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cal();
                Log.v("change2", bin0.getSelectedItemId() + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void cal(){
        String ten;

        try {
            switch ((int) bin0.getSelectedItemId()) {
                case 0:
                    value2.setText(Integer.toBinaryString(Integer.parseInt(value0.getText().toString())));
                    value8.setText(Integer.toOctalString(Integer.parseInt(value0.getText().toString())));
                    value10.setText(value0.getText().toString());
                    //        value16.setText(Integer.toHexString(Integer.parseInt(value0.getText().toString())));
                    break;
                case 1:
                    ten = Integer.valueOf(value0.getText().toString(), 2).toString();
                    value10.setText(ten);
                    value8.setText(Integer.toOctalString(Integer.parseInt(ten)));
                    value2.setText(Integer.toBinaryString(Integer.parseInt(ten)));
                    //        value16.setText(Integer.toHexString(Integer.parseInt(ten)));
                    break;
                case 2:
                    ten = Integer.valueOf(value0.getText().toString(), 8).toString();
                    value10.setText(ten);
                    value8.setText(Integer.toOctalString(Integer.parseInt(ten)));
                    value2.setText(Integer.toBinaryString(Integer.parseInt(ten)));
                    //        value16.setText(Integer.toHexString(Integer.parseInt(ten)));
                    break;
                case 3:
                    ten = Integer.valueOf(value0.getText().toString(), 16).toString();
                    value10.setText(ten);
                    value8.setText(Integer.toOctalString(Integer.parseInt(ten)));
                    value2.setText(Integer.toBinaryString(Integer.parseInt(ten)));
                    //        value16.setText(Integer.toHexString(Integer.parseInt(ten)));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
