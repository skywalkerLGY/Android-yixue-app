package com.example.curriculum_design.message;
import com.github.promeg.pinyinhelper.Pinyin;

public class Contact {
    public String mName;
    public String mPinyin;
    public String qq;
    public Contact(String name,String qq) {
        this.mName = name;
        this.mPinyin = Pinyin.toPinyin(mName,"/");
        this.qq=qq;
    }

    public String getName() {
        return mName;
    }

    public String getPinyin() {
        return mPinyin;
    }
}
