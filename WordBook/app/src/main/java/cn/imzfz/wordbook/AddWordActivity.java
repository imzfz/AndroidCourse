package cn.imzfz.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zfz on 2017/10/12.
 * 添加词语类
 */

public class AddWordActivity extends AppCompatActivity {
    private String word;
    private String meaning;
    private String phonetic;
    private EditText newWord;
    private EditText newMean;
    private TextView newPhonetic;
    private Button cancle;
    private Button confirm;
    private Intent intent;
    private Button getInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_word);
        newWord = (EditText)findViewById(R.id.editword);
        newMean = (EditText)findViewById(R.id.editmean);
        newPhonetic = (TextView)findViewById(R.id.editphonetic);
        cancle = (Button)findViewById(R.id.edit_btn_cancle);
        confirm = (Button)findViewById(R.id.edit_btn_confirm);
        getInfo = (Button)findViewById(R.id.getInfo);

        intent = getIntent();
        word = intent.getStringExtra("word");
        meaning = intent.getStringExtra("meaning");
        phonetic = intent.getStringExtra("phonetic");
        moreAction();
    }

    /**
     * 点击事件传输数据
     */
    public void moreAction(){
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("newWord", newWord.getText().toString());
                intent.putExtra("newMean", newMean.getText().toString());
                intent.putExtra("newPhonetic", newPhonetic.getText().toString());
                setResult(1, intent);
                finish();
            }
        });

        getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread thread = new Thread(new Translate(newWord.getText().toString()));
                    thread.start();
                    Thread.sleep(1000);
                    newPhonetic.setText(Translate.getPhonetic());
                    newMean.setText(Translate.getRes());
                    Log.v("RRRRRRRes", Translate.getRes());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
