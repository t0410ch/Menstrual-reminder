package com.hzw.signcalendarprogect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class Mydatebasehelper extends SQLiteOpenHelper {
    public static final String CREAT_Data="create table Dataset("
            +"id integer primary key autoincrement,"
            +"datebegin text,"
            +"peace text,"
            +"dateend text)";
    private Context mContext;
    public Mydatebasehelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL((CREAT_Data));
        Toast.makeText(mContext, "创建成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
