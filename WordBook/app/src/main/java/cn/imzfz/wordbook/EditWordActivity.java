package cn.imzfz.wordbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zfz on 2017/10/11.
 * 修改单词类
 */

public class EditWordActivity extends AppCompatActivity {

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
    private Data data;
    private SQLiteDatabase database;

    private Map<String, String> vocabulary;
    CharSequence text;

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

        data = new Data(getApplication().getApplicationContext());
        database = data.getWritableDatabase();

        intent = getIntent();
        word = intent.getStringExtra("word");
        meaning = intent.getStringExtra("meaning");
        phonetic = intent.getStringExtra("phonetic");
        text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        if(text != null)
        word = text.toString();
        newWord.setText(word);
        newMean.setText(meaning);
        newPhonetic.setText(phonetic);
        moreAction();
    }

    public void setWord() {
        word = newWord.getText().toString();
    }

    public void setMeaning() {
        meaning = newMean.getText().toString();
    }

    public String getWord(){
        return word;
    }

    public String getMean(){
        return meaning;
    }

    /**
     * 鼠标点击事件响应 传输数据
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
                if(text != null){
                    menu_add();
                }
                intent.putExtra("newWord", newWord.getText().toString());
                intent.putExtra("newMean", newMean.getText().toString());
                intent.putExtra("newPhonetic", newPhonetic.getText().toString());
                setResult(0, intent);
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
                    Log.v("RRRRRRRes1", Translate.getRes());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void menu_add() {
        try {
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("word", newWord.getText().toString());
            values.put("meaning", newMean.getText().toString());
            values.put("phonetic", newPhonetic.getText().toString());
            database.insert(Data.TABLE_NAME, "", values);
            database.setTransactionSuccessful();
            vocabulary = new HashMap<>();
            vocabulary.put("word", newWord.getText().toString());
            vocabulary.put("meaning", newMean.getText().toString());
            vocabulary.put("phonetic", newPhonetic.getText().toString());
            WordFragment.list.add(vocabulary);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

    }
}
