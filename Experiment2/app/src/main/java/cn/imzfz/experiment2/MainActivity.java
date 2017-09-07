package cn.imzfz.experiment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Intent intent1;
    Button editName;
    Button editTel;
    TextView user;
    TextView tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (TextView)findViewById(R.id.user_name);
        tel = (TextView)findViewById(R.id.tel_number);
        editName = (Button)findViewById(R.id.btn);
        editTel = (Button)findViewById(R.id.edit_tel);
        intent = new Intent(this, EditName.class);
        intent1 = new Intent(this, EditTel.class);

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name", user.getText().toString());
                startActivityForResult(intent, 0);
            }
        });

        editTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.putExtra("tel", tel.getText().toString());
                startActivityForResult(intent1, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == 0){
            String res = data.getStringExtra("res");
            user.setText(res);
        }
        if(requestCode == 0 && resultCode == 1){
            String res = data.getStringExtra("res");
            tel.setText(res);
        }
    }

}
