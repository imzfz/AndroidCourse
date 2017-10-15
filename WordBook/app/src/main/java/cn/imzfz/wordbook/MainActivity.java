package cn.imzfz.wordbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView wordsList;
    private TextView index;
    private TextView reading;
    private TextView me;
    private Intent intent;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment fIndex;
    private Fragment fWordsList;
    private Fragment fReading;
    private Fragment fMe;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fIndex = new Index();
        fWordsList = new WordFragment();

        index = (TextView) findViewById(R.id.index);
        wordsList = (TextView) findViewById(R.id.open_book);
        reading = (TextView) findViewById(R.id.reading);
        me = (TextView) findViewById(R.id.me);
        searchView = (SearchView)findViewById(R.id.searchview);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.container, fIndex, "index").commit();

        moreAction();

    }

    //注册众多监听事件
    public void moreAction() {
        wordsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                if (fm.findFragmentByTag("wordslist") == null) {
                    ft.add(R.id.container, fWordsList, "wordslist").commit();
                } else {
                    ft.replace(R.id.container, fWordsList, "wordslist").commit();
                }
            }
        });

        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();

                if (fm.findFragmentByTag("index") == null) {
                    ft.add(R.id.container, fIndex, "index").commit();
                } else {
                    ft.replace(R.id.container, fIndex, "index").commit();
                }
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread thread = new Thread(new Translate("good"));
                    thread.start();
                    Log.v("RRRRRRRes", Translate.getRes());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
