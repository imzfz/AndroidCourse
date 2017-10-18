package cn.imzfz.wordbook;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zfz on 2017/10/9.
 * <p>
 * 单词列表页Fragment
 */

public class WordFragment extends Fragment {

    private ListView listView;
    private Data data;
    private SQLiteDatabase database;
    public static List<Map<String, String>> list;
    private Map<String, String> vocabulary;
    private Intent intent;
    private String newWord = "";
    private String newMean = "";
    private String newPhonetic = "";
    private int position = 0;
    private SimpleAdapter adapter;
    private DetailFragment detailFragment;
    private Index indexFragment;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);

        data = new Data(getActivity().getApplication().getApplicationContext());
        database = data.getWritableDatabase();
        listView = (ListView) view.findViewById(R.id.listView);
        searchView = (SearchView) view.findViewById(R.id.searchview);
        listView.setTextFilterEnabled(true);
        list = new ArrayList<>();
        this.registerForContextMenu(listView);

        indexFragment = (Index) getActivity().getSupportFragmentManager().findFragmentByTag("index");

        showWords();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            adapter = new SimpleAdapter(getContext(), list, R.layout.word,
                    new String[]{"word", "meaning", "phonetic"}, new int[]{R.id.word, R.id.meaning, R.id.phonetic});
            listView.setAdapter(adapter);
        }
        else {
            adapter = new SimpleAdapter(getContext(), list, R.layout.word,
                    new String[]{"word"}, new int[]{R.id.word});
            listView.setAdapter(adapter);
        }
        moreAction();
        searchWord();

        return view;
    }


    public void moreAction() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    detailFragment = (DetailFragment) getActivity()
                                    .getSupportFragmentManager()
                                    .findFragmentByTag("worddetail");
                    onLand(i);
                }else {
                    Map<String,String> map =  (Map<String, String>) adapterView.getItemAtPosition(i);
                    editWord(map.get("word"), map.get("meaning"), map.get("phonetic"));
                }
            }
        });
    }

    /**
     * 上下文菜单，编辑和删除单词
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("操作");
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            menu.add(0, 1, Menu.NONE, "删除");
            menu.add(0, 2, Menu.NONE, "添加");
        }else {
            menu.add(0, 0, Menu.NONE, "编辑");
            menu.add(0, 1, Menu.NONE, "删除");
            menu.add(0, 2, Menu.NONE, "添加");
        }
    }


    /**
     * 菜单的事件
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case 0:
                position = menuInfo.position;
                editWord(position);
                break;
            case 1:
                position = menuInfo.position;
                delWord();
                break;
            case 2:
                addWord();
                break;
        }

        return super.onContextItemSelected(item);

    }

    public void addWord() {
        try {
            intent = new Intent(getContext(), AddWordActivity.class);
            startActivityForResult(intent, 2);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void addChange() {
        try {
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
            adapter.notifyDataSetChanged();
            indexFragment.setTotal(indexFragment.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    /**
     * 删除单词
     */
    public void delWord() {
        String id = list.get(position).get("id");
        String word = list.get(position).get("word");
        String meaning = list.get(position).get("meaning");
        String phonetic = list.get(position).get("phonetic");
        Toast.makeText(getContext(), id, Toast.LENGTH_LONG).show();
        try {
            database.beginTransaction();
            database.execSQL("DELETE from " + Data.TABLE_NAME + " WHERE id = '" + id + "'");
            database.setTransactionSuccessful();
            database.endTransaction();
            list.remove(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    public void delWord(int id) {
        String sql = "DROP TABLE IF EXISTS " + Data.TABLE_NAME;
        database.execSQL(sql);
    }


    /**
     * 编辑单词
     */
    public void editWord(int position) {
        intent = new Intent(getContext(), EditWordActivity.class);
        String word = list.get(position).get("word");
        String meaning = list.get(position).get("meaning");
        String phonetic = list.get(position).get("phonetic");
        intent.putExtra("word", word);
        intent.putExtra("meaning", meaning);
        intent.putExtra("phonetic", phonetic);
        startActivityForResult(intent, 0);
    }

    public void editWord(String wword, String mmeaning, String pphonetic) {
        intent = new Intent(getContext(), EditWordActivity.class);
        intent.putExtra("word", wword);
        intent.putExtra("meaning", mmeaning);
        intent.putExtra("phonetic", pphonetic);
        startActivityForResult(intent, 0);
    }

    /**
     * 横屏点击事件
     */
    public void onLand(int position){
        String word = list.get(position).get("word");
        String meaning = list.get(position).get("meaning");
        String phonetic = list.get(position).get("phonetic");
        detailFragment.setData(word, meaning, phonetic);
    }

    /**
     * 更新数据库
     */
    public void update() {
        try {
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("word", newWord);
            values.put("meaning", newMean);
            values.put("phonetic", newPhonetic);
            database.update(Data.TABLE_NAME, values, "id = ?", new String[]{list.get(position).get("id")});
            database.setTransactionSuccessful();
            database.endTransaction();
            list.get(position).put("word", newWord);
            list.get(position).put("meaning", newMean);
            list.get(position).put("phonetic", newPhonetic);
            adapter.notifyDataSetChanged();
            //         showWords();
            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
//            database.endTransaction();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 0 && resultCode == 0) {
                newWord = data.getStringExtra("newWord");
                newMean = data.getStringExtra("newMean");
                newPhonetic = data.getStringExtra("newPhonetic");
                update();
            }

            if (requestCode == 2 && resultCode == 1) {
                newWord = data.getStringExtra("newWord");
                newMean = data.getStringExtra("newMean");
                newPhonetic = data.getStringExtra("newPhonetic");
                addChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchWord() {
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
       //         for(int i = 0; i < list.size(); i++){
                    if(list.get(0).get("word").equals(query)){
                        editWord(0);
                        Log.v("test","23333");
       //                 break;
                    }
       //         }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)) {
                    listView.setFilterText(newText);
                }
                else {
                    listView.clearTextFilter();
                }
                return false;
            }
        });

    }

    /**
     * 单词列表页显示单词
     */
    public void showWords() {
        String replace = "";
        try {
            database.beginTransaction();

            Cursor cursor = database.rawQuery("select id, word, meaning, phonetic from words", null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    vocabulary = new HashMap<>();
                    vocabulary.put("id", cursor.getInt(0) + "");
                    vocabulary.put("word", cursor.getString(1));
                    vocabulary.put("meaning", cursor.getString(2));
                    vocabulary.put("phonetic", cursor.getString(3));
                    list.add(vocabulary);
                }
            }
            cursor.close();
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void setNewWord(String newWord) {
        this.newWord = newWord;
    }

    public void setNewMean(String newMean) {
        this.newMean = newMean;
    }

    public void setNewPhonetic(String newPhonetic) {
        this.newPhonetic = newPhonetic;
    }

    public ListView getListView() {
        return listView;
    }
}
