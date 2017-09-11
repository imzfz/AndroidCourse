package cn.imzfz.experiment4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class MainActivity extends Activity {

    Button read;
    Button write;
    String name = "", id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        read = (Button)findViewById(R.id.read);
        write = (Button)findViewById(R.id.write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream out = null;
                name = ((EditText)findViewById(R.id.name)).getText().toString();
                id = ((EditText)findViewById(R.id.number)).getText().toString();
                try {
                    FileOutputStream fileOutputStream = openFileOutput("111", MODE_PRIVATE);
                    out = new BufferedOutputStream(fileOutputStream);
                    String content = id + "\n" + name;
                    out.write(content.getBytes(StandardCharsets.UTF_8));
                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                }catch (Exception e){

                }
                finally {
                    if(out != null)
                        try {
                            out.close();
                        }catch (Exception ex){

                        }
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream in = null;
                BufferedReader reader;
                String t = "";
                String str = "";
                try {
                    FileInputStream fileInputStream = openFileInput("111");
                    in = new BufferedInputStream(fileInputStream);
                    reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    try {
                        while((t = reader.readLine()) != null)
                            str = str + "\n" + t;
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                    }
                    finally {
                        if(in != null)
                            in.close();
                    }

                }catch (Exception e){

                }
            }
        });
    }
}
