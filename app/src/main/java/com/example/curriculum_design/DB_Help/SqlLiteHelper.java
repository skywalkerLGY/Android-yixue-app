package com.example.curriculum_design.DB_Help;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlLiteHelper extends SQLiteOpenHelper {
    public SqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "usergl.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(_id integer primary key autoincrement," + "user_name varchar(20),user_photo varchar(200),user_qianming varchar(60),user_nicheng varchar(20))";
        db.execSQL(sql);
        db.execSQL("delete from user");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor Select_by_name(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", null, "name ='" + name + "%'", null, null, null, null);
        return cursor;
    }

//    public Cursor query() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("user", null, null, null, null, null, null);
//        return cursor;
//    }
    public void insert(String user_name,String user_photo,String user_qianming,String user_nicheng){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("user_name", user_name);
        cv.put("user_photo", user_photo);
        cv.put("user_qianming", user_qianming);
        cv.put("user_nicheng", user_nicheng);
        db.insert("user", null, cv);
    }
//    public int delete(int _id){
//        SQLiteDatabase db = this.getWritableDatabase();
////        int id=db.delete("user", "_id="+_id, null);
//        int id=db.delete("user", "_id=?", new String[]{_id+""});
//        return id;
//    }
    public int update(String user_name,String user_photo,String user_qianming,String user_nicheng){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("user_name", user_name);
        cv.put("user_photo", user_photo);
        cv.put("user_qianming", user_qianming);
        cv.put("user_nicheng", user_nicheng);
        int id=db.update("user", cv, "user_name=?", new String[]{user_name+""});
        return id;
    }

//    public Cursor queryByphone(String phoneFind) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("user", null, "phone like '" + phoneFind + "%'", null, null, null, null);
//        return cursor;
//    }
}
