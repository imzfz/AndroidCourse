package cn.imzfz.wordbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by zfz on 2017/10/9.
 * <p>
 * 主界面Fragment
 */

public class Index extends Fragment {
    private TextView showWord;
    private TextView showPhonetic;
    private Data data;
    private SQLiteDatabase database;
    private TextView change;
    private TextView addWord;
    public TextView wordCount;
    public TextView todayAdd;
    private Intent intent;
    private String newWord;
    private String newMean;
    private String newPhonetic;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index, container, false);

        showWord = (TextView) view.findViewById(R.id.show_word);
        showPhonetic = (TextView) view.findViewById(R.id.show_phonetic);
        change = (TextView) view.findViewById(R.id.change);
        addWord = (TextView) view.findViewById(R.id.add_word_text);
        wordCount = (TextView) view.findViewById(R.id.word_count);
        todayAdd = (TextView) view.findViewById(R.id.today_add);

        data = new Data(getActivity().getApplication().getApplicationContext());

        addWord();
        changeWord();
        moreAction();

        return view;
    }

    /**
     * 主界面随机换一个单词事件响应
     */
    public void moreAction() {
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeWord();
                Log.v("changeeeee", "233333");
            }
        });
    }

    /**
     * 主界面随机换一个单词
     * 从数据直接随机
     * 采用随机数
     */
    public void changeWord() {
        database = data.getReadableDatabase();
        database.beginTransaction();
        int i = getTotal();
        setTotal(i);
        try {
            if(i > 0) {
                Random r = new Random();
                int t = r.nextInt(i);
                Cursor cursor = database.rawQuery("select word, phonetic from words where id = " + t, null);
                database.setTransactionSuccessful();
                while (cursor.moveToNext()) {
                    showWord.setText(cursor.getString(0));
                    showPhonetic.setText(cursor.getString(1));
                }
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
        }
    }


    /**
     * 主界面总单词数显示
     */
    public void setTotal(int i){
        wordCount.setText(i + "");
    }

    public static void setToday(){

    }

    public void addWord(){
        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), AddWordActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    /**
     * 从数据库中查询单词数量
     */
    public int getTotal(){
        database = data.getReadableDatabase();
        database.beginTransaction();
        int i = (int)DatabaseUtils.queryNumEntries(database, "words");
        database.setTransactionSuccessful();
        database.endTransaction();
        return i;
    }

    /**
     * activity交换数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 1){
            newWord = data.getStringExtra("newWord");
            newMean = data.getStringExtra("newMean");
            newPhonetic = data.getStringExtra("newPhonetic");
            update();
            wordCount.setText("" + getTotal());
        }
    }

    /**
     * 更新单词显示界面的数据
     */
    public void update(){
        try {
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("word", newWord);
            values.put("meaning", newMean);
            values.put("phonetic", newPhonetic);
            database.insert(Data.TABLE_NAME, "", values);
            database.setTransactionSuccessful();
    //        database.endTransaction();
            /*if(newPhonetic.contains("\'")){
                newPhonetic = newPhonetic.replace("\'", "?");
            }
            database = data.getWritableDatabase();
            database.beginTransaction();
            database.execSQL("insert into " + Data.TABLE_NAME +
                    " (word, meaning, phonetic) values ('" + newWord + "','" + newMean + "','" + newPhonetic + "')");
            database.setTransactionSuccessful();*/
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        finally {
            database.endTransaction();
        }
    }
}
