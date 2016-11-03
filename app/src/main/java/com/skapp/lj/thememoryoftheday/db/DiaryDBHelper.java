package com.skapp.lj.thememoryoftheday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a on 2016-11-02.
 */

public class DiaryDBHelper extends SQLiteOpenHelper {

    public DiaryDBHelper(Context context)
    {
        super(context, "Diary.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table diary (_id integer primary key autoincrement, "
                + "title integer not null, body text not null, date text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS diary");
        onCreate(db);
    }
}
//    private  static final  int ID_HEART = 1;
//    private  static final  int ID_QUESTION = 2;
//    private  static final  int ID_EM = 3;
//    private  static final  int ID_COMMA = 4;
//    private  static final  int ID_PERIOD = 5;
//    private  static final  int ID_STAR = 6;
//    private  static final  int ID_SD = 7;
