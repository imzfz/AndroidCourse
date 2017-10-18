package cn.imzfz.wordbook;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

/**
 * Created by zfz on 2017/10/17.
 */

public class DetailFragment extends Fragment {
    private String word = "";
    private String meaning = "";
    private String phonetic = "";
    private EditText newWord;
    private EditText newMean;
    private TextView newPhonetic;
    private Button cancle;
    private TextView confirm;
    private Intent intent;
    private Button getInfo;
    private int save = 0;
    private WordFragment wordFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_word, container, false);

        newWord = (EditText)view.findViewById(R.id.editword);
        newMean = (EditText)view.findViewById(R.id.editmean);
        newPhonetic = (TextView)view.findViewById(R.id.editphonetic);
        cancle = (Button)view.findViewById(R.id.edit_btn_cancle);
        confirm = (TextView)view.findViewById(R.id.edit_confirm);
        getInfo = (Button)view.findViewById(R.id.getInfo);

        wordFragment = (WordFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag("wordslist");

        moreAction();
        return view;
    }

    public void setData(String word, String meaning, String phonetic){
        this.word = word;
        this.meaning = meaning;
        this.phonetic = phonetic;
        show();
    }

    public void show(){
        newWord.setText(word);
        newMean.setText(meaning);
        newPhonetic.setText(phonetic);
    }

    public void moreAction(){
        if(confirm != null) {
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wordFragment.setNewWord(newWord.getText().toString());
                    wordFragment.setNewMean(newMean.getText().toString());
                    wordFragment.setNewPhonetic(newPhonetic.getText().toString());
                    wordFragment.update();
                }
            });
        }
    }
}
