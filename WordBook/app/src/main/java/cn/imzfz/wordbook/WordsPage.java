package cn.imzfz.wordbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zfz on 2017/10/8.
 */

public class WordsPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        ListView listView = (ListView)findViewById(R.id.listView);
        SimpleAdapter adapter;

        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> test = new HashMap<>();
        test.put("word", "111");
        test.put("phonetic", "111");
        test.put("pronounce", "111");
        list.add(test);


        adapter = new SimpleAdapter(this, list, R.layout.word,
                new String[]{"word", "phonetic", "pronounce"}, new int[]{R.id.word, R.id.meaning, R.id.phonetic});
        listView.setAdapter(adapter);
    }

    public void getData(){

    }

    public void setData(){

    }
}
