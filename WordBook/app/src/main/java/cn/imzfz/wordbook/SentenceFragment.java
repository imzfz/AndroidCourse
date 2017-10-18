package cn.imzfz.wordbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zfz on 2017/10/16.
 */

public class SentenceFragment extends Fragment{

    TextView sentence;
    TextView trans;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sentence, container, false);

        sentence = (TextView) view.findViewById(R.id.sentence);
        trans = (TextView) view.findViewById(R.id.note);
        image = (ImageView) view.findViewById(R.id.image);

        ShowSentence show = new ShowSentence(getContext());

        Thread thread = new Thread(show);
        thread.start();
        try {
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!ShowSentence.getSentence().equals("")) {
            sentence.setText(ShowSentence.getSentence());
        }

        if(!ShowSentence.getNote().equals("")) {
            trans.setText(ShowSentence.getNote());
        }

        image.setImageBitmap(ShowSentence.getImage());

        return view;
    }

}
