package com.sam.main.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper{
//    创建学生表的语句
    private final String CREATE_TABLE_STUDENT  = "create table student (" +
            "stu_id text primary key" +
            ")";
//    创建课表的语句
    private final String CREATE_TABLE_CLASS = "create table class(" +
        "id integer primary key autoincrement," +
        "classname text," +
        "day text," +
        "stu_id text," +
        "tea_id text" +
        ")";
//    创建老师表的语句
    private final String CREATE_TABLE_TEACHER = "create table teacher(" +
        "tea_id text primary key" +
        ")";

    public MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_CLASS);
        db.execSQL(CREATE_TABLE_TEACHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

}
