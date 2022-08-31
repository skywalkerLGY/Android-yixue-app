package com.example.curriculum_design.DB_Help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Init {
    static String namename="";
    public static void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("ClassforName成功");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("ClassforName失败");
                }
                try {
                    Connection cn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/android_db", "root", "");
                    Statement st = cn.createStatement();
                    String sql = "select * from user_info";
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        namename += rs.getString("user_name");
                        namename += rs.getString("user_phone");
                        namename += rs.getString("user_id");
                        namename += "\n";
                    }
                    for(int i=0;i<10;i++)
                        System.out.println(namename + "+++++++++++");
                    cn.close();
                    System.out.println("Connection连接数据库成功");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Connection连接数据库失败");
                }
            }
        }).start();
    }
}
