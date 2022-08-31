package com.example.curriculum_design.DB_Help;

import java.io.Serializable;

public class Now_User implements Serializable {
    public static boolean Flag=false;
    public static String User_id=null;
    public static String User_Name=null;
    public static String user_pass=null;
    public static String user_phone=null;
    public static String user_school_number=null;
    public static String user_qq=null;
    public static String user_birth=null;
    public static String user_sex=null;
    public static String receive_text=null;
    public static String send_text=null;
    public static String uuid=null;
    public static void Set_Value(UserInfo userInfo){
        if(userInfo!=null){
            Flag=true;
            photo=userInfo.photo;
            User_id=userInfo.user_id;
            User_Name=userInfo.user_name;
            user_pass=userInfo.user_pass;
            user_phone=userInfo.user_phone;
            user_school_number=userInfo.user_school_number;
            user_qq=userInfo.user_qq;
            user_birth=userInfo.user_birth;
            receive_text=userInfo.receive_text;
            user_sex=userInfo.user_sex;
            send_text=userInfo.send_text;
            uuid=userInfo.uuid;
        }
    }
    public static String photo=null;
    public static String nicheng=null;
    public static String qianming=null;
    public static String back=null;
}
