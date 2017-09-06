package cn.imzfz.experiment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zfz on 2017/9/6.
 */

public class EditName extends AppCompatActivity {

    String name;
    EditText editName;
    Intent intent;
    Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        intent = getIntent();
        editName = (EditText) findViewById(R.id.edit_name);
        confirm = (Button)findViewById(R.id.confirm);
        name = intent.getStringExtra("name");
        editName.setText(name);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
