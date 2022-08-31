package com.example.curriculum_design.Word_recite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Word_Helper extends SQLiteOpenHelper {
    public Word_Helper(@Nullable Context context) {
        super(context, "yao_word.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table sc_word(_id integer primary key autoincrement," + "name varchar(50),english_word varchar(50),chinese_word varchar(50))";
        db.execSQL(sql);
        db.execSQL("delete from sc_word");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor query() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("sc_word", null, null, null, null, null, null);
        return cursor;
    }
    public void insert(String name,String english_word,String chinese_word){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name", name);
        cv.put("english_word", english_word);
        cv.put("chinese_word", chinese_word);
        db.insert("sc_word", null, cv);
    }
    public void delete(String english_word){
        SQLiteDatabase db = this.getWritableDatabase();
        int id=db.delete("sc_word", "english_word=?", new String[]{english_word});
    }
    public boolean Container(String english_word){
        Cursor cursor=query();
        if(cursor != null && cursor.getCount() > 0){
            //判断cursor中是否存在数据
            while (cursor.moveToNext()){
                String english = cursor.getString(2);
                if(english.equals(english_word))
                    return true;
            }
            cursor.close();
        }
        return false;
    }
    public int getlength(){
        int sum=0;
        Cursor cursor=query();
        if(cursor != null && cursor.getCount() > 0){
            //判断cursor中是否存在数据
            while (cursor.moveToNext()){
                sum++;
            }
            cursor.close();
        }
        return sum;
    }
}
