package cn.imzfz.calculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

/**
 * Created by zfz on 2017/10/2.
 */

public class InterNet extends Rate implements Runnable {
    EditText price1;
    EditText price2;
 //   Spinner currency1;
    Spinner currency2;
    String middle;
    /*String[] type = {"EUR", "GBP", "HKD", "INR", "JPY",
            "KRW", "MOP", "TWD", "USD"};
    String type1 = "USD";*/

    public InterNet(EditText price1, EditText price2, Spinner currency2) {
        this.price1 = price1;
        this.price2 = price2;
  //      this.currency1 = currency1;
        this.currency2 = currency2;
  //      currency1.setSelection(0);
        currency2.setSelection(8);
        price1.setText("100");
        price2.setText("0");

/*        currency2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        price1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void run() {
        connect();
    }


    public void connect() {
        JSONObject object;
        JSONObject object1;
        JSONObject object2;
        try {
     //       URL u = new URL("https://sapi.k780.com/?app=finance.rate_cnyquot&curno=" + getCur() + "&bankno=BOC&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json");
            URL u = new URL("http://api.k780.com/?app=finance.rate_cnyquot&curno=" + type1 + "&bankno=BOC&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json");
            InputStream inn = u.openStream();
            ByteArrayOutputStream outt = new ByteArrayOutputStream();

            byte buf[] = new byte[1024];
            int read = 0;
            while ((read = inn.read(buf)) > 0) {
                outt.write(buf, 0, read);
            }
            byte b[] = outt.toByteArray();
            String res = new String(b, "utf-8");
            object = new JSONObject(res);
            object1 = object.getJSONObject("result");
            object2 = object1.getJSONObject(getCur()).getJSONObject("BOC");
            middle = object2.getString("middle");

            Log.v("result", res);

            price2.setText(middle);
            inn.close();
            outt.close();

        } catch (Exception e) {
            Log.v("Exc", e.getMessage() + "");

        } finally {

        }
    }

    public String getMuch(){
        return price1.getText().toString();
    }

    public String getCur(){
        return type[(int)currency2.getSelectedItemId()];
    }

    public void cal() {
        BigDecimal rate = null, pri1 = null, pri2 = null;
        if(middle != null && !middle.equals("") && !price1.getText().toString().equals("") && price1.getText() != null) {
            rate = new BigDecimal(middle);
            pri1 = new BigDecimal(price1.getText().toString());
        }
        else {
            price1.setText("0");
            price2.setText("0");
            return;
        }
        try {
            if (pri1.compareTo(new BigDecimal(0)) == 1) {
                pri2 = rate.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_DOWN).multiply(pri1);
                price2.setText(pri2 + "");
            }
        } catch (Exception e) {
            price1.setText("0");
            price2.setText("0");
        }
    }

}
