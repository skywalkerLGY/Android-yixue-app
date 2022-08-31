package com.example.curriculum_design.mykcb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KCBHelper extends SQLiteOpenHelper {

    public KCBHelper(Context context){
        super(context,"mykcb.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table kcb(_id integer primary key autoincrement,kc_name text,kc_teacher text," +
                "kc_zhou text,kc_start text,kc_end text,kc_location text)";
        db.execSQL(sql);
        db.execSQL("delete from kcb");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('嵌入式应用开发','许老师','一','1','2','图C 603')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('嵌入式应用开发','许老师','三','1','2','图C 603')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('数据结构','王老师','一','3','4','图C 605')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('数据结构','王老师','三','3','4','图C 605')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('计算机网络基础','乐老师','一','7','8','教3 406')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('计算机网络基础','乐老师','四','7','8','教3 406')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('单片机','谷老师','五','1','4','图B 606')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('毛泽东思想','王老师','四','3','4','教8 501')");
        db.execSQL("insert into kcb(kc_name,kc_teacher,kc_zhou,kc_start,kc_end,kc_location)" +
                " values('形势与政策','刘老师','三','7','8','国教')");
    }
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("kcb", null, null, null, null, null, null);
        return cursor;
    }
    public void insert(Course course){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("kc_name", course.kc_name);
        cv.put("kc_teacher", course.kc_teacher);
        cv.put("kc_zhou", course.kc_zhou);
        cv.put("kc_start", course.kc_start);
        cv.put("kc_end", course.kc_end);
        cv.put("kc_location", course.kc_location);
        db.insert("kcb", null, cv);
    }
    public void  delete(String ke_name,String zhou){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from kcb where kc_name='"+ke_name+"' and kc_zhou='"+zhou+"'");
    }
    public int update(Course course){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("kc_name", course.kc_name);
        cv.put("kc_teacher", course.kc_teacher);
        cv.put("kc_zhou", course.kc_zhou);
        cv.put("kc_start", course.kc_start);
        cv.put("kc_end", course.kc_end);
        cv.put("kc_location", course.kc_location);
        int id=db.update("kcb", cv, "kc_name='"+course.kc_name+"' and kc_zhou='"+course.kc_zhou+"'", null);
        return id;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
