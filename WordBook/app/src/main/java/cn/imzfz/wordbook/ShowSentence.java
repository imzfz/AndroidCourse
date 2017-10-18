package cn.imzfz.wordbook;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zfz on 2017/10/16.
 */

public class ShowSentence implements Runnable {
    private static String content = "";
    private static String note = "";
    private static Bitmap image;
    private static String picture;
    private static Context con;


    public ShowSentence(Context context){
        con = context;
    }

    @Override
    public void run() {
        getRes();
    }

    public static void getRes(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(date);
        JSONObject mainObject;
        try {
            URL u = new URL("http://open.iciba.com/dsapi/?date=" + day);
            InputStream inn = u.openStream();
            ByteArrayOutputStream outt = new ByteArrayOutputStream();

            byte buf[] = new byte[2048];
            int read = 0;
            while ((read = inn.read(buf)) > 0) {
                outt.write(buf, 0, read);
            }
            byte b[] = outt.toByteArray();
            String res = new String(b, "utf-8");
            System.out.println(res);
            mainObject = new JSONObject(res);
            content = mainObject.getString("content");
            note = mainObject.getString("note");
            if (con.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                picture = mainObject.getString("picture");
            }
            else {
                picture = mainObject.getString("picture2");
            }
            setImage();
            Log.v("ASCII", note);

            inn.close();
            outt.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getSentence(){
        return content;
    }

    public static String getNote(){
        return note;
    }

    public static void setImage(){
        try {
            URL url = new URL(picture);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                image = BitmapFactory.decodeStream(inputStream);
                Log.v("aaa", "aaaaaaa");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap getImage(){
        Log.v("bbb", "bbbbbb");
        return image;
    }
}
