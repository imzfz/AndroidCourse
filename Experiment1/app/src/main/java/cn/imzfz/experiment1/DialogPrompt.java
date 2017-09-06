package cn.imzfz.experiment1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zfz on 2017/9/6.
 */

public class DialogPrompt  extends DialogFragment {

    AlertDialog.Builder builder;
    LayoutInflater inflater;
    View view;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("提示")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //        Toast.makeText(getContext(), id, Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_dialog, null);
    }
}
