package com.example.curriculum_design.DB_Help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitSql {
    static String namename = "";

    public static void Init_Sql() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DbHelper.Query_User();
                    DbHelper.Query_Pyq();
                    DbHelper.Query_KD();
                    DbHelper.Query_Msg();
                    DbHelper.Query_Chat();
                } catch (Exception e) {
                }
            }
        }).start();
//        System.out.println("执行了---------------------------------------------");
    }
}
