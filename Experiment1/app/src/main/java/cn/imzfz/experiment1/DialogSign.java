package cn.imzfz.experiment1;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zfz on 2017/9/5.
 */

public class DialogSign extends DialogFragment {

    AlertDialog.Builder builder;
    LayoutInflater inflater;
    EditText user, password;
    String id = "", pwd = "";
    View view;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog, null);
        user = (EditText)view.findViewById(R.id.uid);
        password = (EditText)view.findViewById(R.id.pwd);


        builder.setView(view)
                .setTitle("登录")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setInfo();
                        valid();
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
        return inflater.inflate(R.layout.dialog, null);
    }

    public void valid(){
        if(id.equals("abc") && pwd.equals("123")){
            Toast.makeText(getContext(), "成功", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(), "失败", Toast.LENGTH_LONG).show();
        }
    }

    public void setInfo(){
        id = user.getText().toString();
        pwd = password.getText().toString();
    }
}
