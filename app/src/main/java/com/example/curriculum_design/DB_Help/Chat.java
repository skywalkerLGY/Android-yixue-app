package com.example.curriculum_design.DB_Help;

import java.io.Serializable;

public class Chat implements Serializable {
    public String username;
    public String time;
    public String content;
    public Integer id;
    public String qq;

    public Chat() {
    }

    @Override
    public String toString() {
        return "Chat{" +
                "username='" + username + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                ", qq='" + qq + '\'' +
                '}';
    }
}
