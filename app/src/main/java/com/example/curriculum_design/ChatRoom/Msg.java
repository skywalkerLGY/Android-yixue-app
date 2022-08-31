package com.example.curriculum_design.ChatRoom;

import java.io.Serializable;

public class Msg implements Serializable {
    public static final int RECEIVE=0;
    public static final int SENT =1;
    public String getContent(){
        return content;
    }
    public int getType(){
        return type;
    }
    public String content;
    public int type;
    public String user_name;
    public String time;
    public String qq;
    public Msg(String content,int type,String user_name){
        this.content=content;
        this.type=type;
        this.user_name=user_name;
    }
    public Msg(){
    }
}
