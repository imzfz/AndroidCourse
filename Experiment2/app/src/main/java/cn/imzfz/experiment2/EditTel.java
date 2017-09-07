package cn.imzfz.experiment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zfz on 2017/9/7.
 */

public class EditTel extends AppCompatActivity {

    String tel;
    EditText editTel;
    Intent intent;
    Button confirm;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        intent = getIntent();
        tv = (TextView)findViewById(R.id.tips);
        tv.setText(R.string.edit_tel);
        editTel = (EditText) findViewById(R.id.edit_name);
        confirm = (Button) findViewById(R.id.confirm);
        tel = intent.getStringExtra("tel");
        editTel.setText(tel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("res", editTel.getText().toString());
                setResult(1, intent);
                finish();
            }
        });
    }
}
