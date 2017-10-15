package cn.imzfz.calculator;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by zfz on 2017/9/25.
 */

public class Exchange extends AppCompatActivity {
    private EditText price1;
    private EditText price2;
    private Spinner currency1;
    private Spinner currency2;
    private Button button;
    String[] type = {"km", "m", "dm", "cm", "mm", "um", "nm"};
    int[] rate = {0, 1000, 10, 10, 10, 1000, 1000};
    String type1 = "m";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_rate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        price1 = (EditText) findViewById(R.id.price1);
        price2 = (EditText) findViewById(R.id.price2);
        currency1 = (Spinner) findViewById(R.id.currency1);
        currency2 = (Spinner) findViewById(R.id.currency2);
        button = (Button)findViewById(R.id.swap);
        currency2.setSelection(1);
        focus();
    }

    public void focus() {
        price1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cal();
                Log.v("change2", currency1.getSelectedItemId() + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        price2.setFocusable(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String swap = "";
                swap = price1.getText().toString();
                price1.setText(price2.getText().toString());
                cal();
            }
        });
    }

    public void cal() {
        int c1, c2, max, min;
        int ra = 1;
        c1 = (int) currency1.getSelectedItemId();
        c2 = (int) currency2.getSelectedItemId();
        max = c1 >= c2 ? c1 : c2;
        min = c1 <= c2 ? c1 : c2;

        price2.setText("");
        for(int i = min + 1; i < max + 1; i ++){
            ra *= rate[i];
        }

        try {
            if(price1.getText().toString().equals("")){
                price2.setText("");
                return;
            }
            if (c2 > c1) {
                price2.setText(ra * Double.parseDouble(price1.getText().toString()) + "");
            } else {
                price2.setText(Double.parseDouble(price1.getText().toString()) / ra + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            price2.setText("错误");
        }

        /*switch (Math.abs(c2 - c1)) {
            case 0:
                price2.setText(price1.getText());
                break;
            case 1:
                try {
                    if(price1.getText().toString().equals("")){
                        price2.setText("");
                        break;
                    }
                    if (c2 > c1) {
                        price2.setText(rate[max] * Double.parseDouble(price1.getText().toString()) + "");
                    } else {
                        price2.setText(rate[max] / Double.parseDouble(price1.getText().toString()) + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    price2.setText("错误");
                }
            case 2:
                int ra = 1;
                for(int i = max - 1; i > 0; i--){
                    ra *= rate[i];
                }
                try {
                    if(price1.getText().toString().equals("")){
                        price2.setText("");
                        break;
                    }
                    if (c2 > c1) {
                        price2.setText(ra * Double.parseDouble(price1.getText().toString()) + "");
                    } else {
                        price2.setText(ra * rate[max] / Double.parseDouble(price1.getText().toString()) + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    price2.setText("错误");
                }
            case 3:
            case 4:
            case 5:
            case 6:
        }*/
    }
}
