package cn.imzfz.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zfz on 2017/10/10.
 * 数据库创建与链接
 */

public class Data extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "vocabulary";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "words";
    private Context context;

    public Data(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create table " + TABLE_NAME + "(id integer primary key autoincrement, " +
                "word text, meaning text, phonetic text)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String createTable = "create table if not exists " + TABLE_NAME + "(id integer primary key autoincrement, " +
                "word text, meaning text, phonetic text)";
        db.execSQL(createTable);
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }


}
