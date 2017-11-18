package cn.imzfz.wordbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.BaseColumns;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
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
    private Fragment fWordDetail;
    private Fragment fMe;
    private SearchView searchView;
    private FrameLayout frameLayout;
    public static final String AUTHORITY = "cn.imzfz.wordbook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fIndex = new Index();
        fWordsList = new WordFragment();
        fReading = new SentenceFragment();


        index = (TextView) findViewById(R.id.index);
        wordsList = (TextView) findViewById(R.id.open_book);
        reading = (TextView) findViewById(R.id.reading);
        //    me = (TextView) findViewById(R.id.me);
        searchView = (SearchView) findViewById(R.id.searchview);
        frameLayout = (FrameLayout) findViewById(R.id.container1);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.container, fIndex, "index").commit();

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0f));
            fWordDetail = new DetailFragment();
        }

        moreAction();

    }

    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
        public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
        public static final String MINE_ITEM = "vnd.cn.imzfz.wordbook";
        public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MINE_ITEM;
        public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MINE_ITEM;
        public static final String PATH_SINGLE = "words/#";
        public static final String PATH_MULTIPLE = "words";
        public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    }


    //注册众多监听事件
    public void moreAction() {
        wordsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();

                if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    frameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

                    if (fm.findFragmentByTag("wordslist") == null) {
                        ft.add(R.id.container, fWordsList, "wordslist");
                    } else {
                        ft.replace(R.id.container, fWordsList, "wordslist");
                    }
                    if (fm.findFragmentByTag("worddetail") == null) {
                        ft.add(R.id.container1, fWordDetail, "worddetail");
                    } else {
                        ft.replace(R.id.container1, fWordDetail, "worddetail");
                    }
                    ft.commit();

                } else {
                    if (fm.findFragmentByTag("wordslist") == null) {
                        ft.add(R.id.container, fWordsList, "wordslist").commit();
                    } else {
                        ft.replace(R.id.container, fWordsList, "wordslist").commit();
                    }
                }
            }
        });

        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();

                if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0f));
                }

                if (fm.findFragmentByTag("index") == null) {
                    ft.add(R.id.container, fIndex, "index").commit();
                } else {
                    ft.replace(R.id.container, fIndex, "index").commit();
                }
            }
        });


        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();

                if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0f));
                }

                if (fm.findFragmentByTag("sentence") == null) {
                    ft.add(R.id.container, fReading, "sentence").commit();
                } else {
                    ft.replace(R.id.container, fReading, "sentence").commit();
                }
            }
        });


        /*me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread thread = new Thread(new Translate("good"));
                    thread.start();
                    Log.v("RRRRRRRes", Translate.getRes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
}
