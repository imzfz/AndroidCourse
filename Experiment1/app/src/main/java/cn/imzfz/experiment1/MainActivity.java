package cn.imzfz.experiment1;


import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    LayoutInflater inflater;
    Button prompt, login;
    DialogFragment fragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        prompt = (Button)findViewById(R.id.prompt);
        login = (Button)findViewById(R.id.login);

        prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogPrompt().show(getSupportFragmentManager(), "tips");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogSign().show(getSupportFragmentManager(), "fragment");
            }
        });
    }
}
