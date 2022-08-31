package com.example.curriculum_design.DB_Help;

import java.io.Serializable;

public class UserInfo implements Serializable{
    public boolean flag;
    public String user_id;
    public String user_name;
    public String user_pass;
    public String user_phone;
    public String user_school_number;
    public String user_qq;
    public String user_birth;
    public String user_sex;
    public String receive_text;
    public String send_text;
    public String uuid=null;
    public String nicheng=null;
    public String qianming=null;
    public String photo=null;
    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "flag=" + flag +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_pass='" + user_pass + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_school_number='" + user_school_number + '\'' +
                ", user_qq='" + user_qq + '\'' +
                ", user_birth='" + user_birth + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", receive_text='" + receive_text + '\'' +
                ", send_text='" + send_text + '\'' +
                ", uuid='" + uuid + '\'' +
                ", nicheng='" + nicheng + '\'' +
                ", qianming='" + qianming + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
