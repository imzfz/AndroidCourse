package cn.imzfz.wordbook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.content;

/**
 * Created by zfz on 2017/10/16.
 */

public class SentenceFragment extends Fragment {

    TextView sentence;
    TextView trans;
    ImageView image;
    private WebView webView;
    MenuItem.OnMenuItemClickListener handler;
    private ActionMode mActionMode = null;
    ActionMode.Callback2 textSelectionActionModeCallback;
    private String newWord, newMean, newPhonetic;
    private Data data;
    private SQLiteDatabase database;
    public static List<Map<String, String>> list;
    private Map<String, String> vocabulary;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sentence, container, false);

        sentence = (TextView) view.findViewById(R.id.sentence);
        trans = (TextView) view.findViewById(R.id.note);
        image = (ImageView) view.findViewById(R.id.image);

        ShowSentence show = new ShowSentence(getContext());
        data = new Data(getActivity().getApplication().getApplicationContext());
        database = data.getWritableDatabase();

        Thread thread = new Thread(show);
        thread.start();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!ShowSentence.getSentence().equals("")) {
            sentence.setText(ShowSentence.getSentence());
        }

        if (!ShowSentence.getNote().equals("")) {
            trans.setText(ShowSentence.getNote());
        }
        menu_add();

        image.setImageBitmap(ShowSentence.getImage());
        sentence.setCustomSelectionActionModeCallback(textSelectionActionModeCallback = new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
          //      menuInflater.inflate(R.menu.selection, menu);
                return true;//返回false则不会显示弹窗
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                //根据item的ID处理点击事件
                /*switch (menuItem.getItemId()) {
                    case R.id.menu_add:
                        Intent intent = new Intent(getContext(), EditWordActivity.class);
                        startActivityForResult(intent, 0);
                        actionMode.finish();//收起操作菜单
                        break;
                }*/
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
        return view;
    }

    public void menu_add() {
        try {
            newWord = getActivity().getIntent().getStringExtra("newWord");
            newMean = getActivity().getIntent().getStringExtra("newMean");
            newPhonetic = getActivity().getIntent().getStringExtra("newPhonetic");
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("word", newWord);
            values.put("meaning", newMean);
            values.put("phonetic", newPhonetic);
            database.insert(Data.TABLE_NAME, "", values);
            database.setTransactionSuccessful();
            vocabulary = new HashMap<>();
            vocabulary.put("word", newWord);
            vocabulary.put("meaning", newMean);
            vocabulary.put("phonetic", newPhonetic);
            list.add(vocabulary);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v("ressssssss", requestCode + resultCode + "");
        try {
            if (requestCode == 0 && resultCode == 0) {
                newWord = data.getStringExtra("newWord");
                newMean = data.getStringExtra("newMean");
                newPhonetic = data.getStringExtra("newPhonetic");
   //             menu_add();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}