package com.example.curriculum_design.DB_Help;

import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.PlayGame.Games_List;
import com.mob.wrappers.UMSSDKWrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbHelper {

    public static String IP = com.example.curriculum_design.IP.IP.ip;
    public static String duankou = "3306";
    public static String username = "root";
    public static String password = "Daohaozhesi26";
    public static String db_url = "jdbc:mysql://" + IP + ":" + duankou + "/android_db?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

    public static void Query_User() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "select * from user_info";
            ResultSet rs = st.executeQuery(sql);
            UserInfo_List.userInfo_List = new ArrayList<>();
            while (rs.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.user_id = rs.getString("user_id");
                userInfo.user_name = rs.getString("user_name");
                userInfo.photo = rs.getString("user_photo");
                userInfo.user_pass = rs.getString("user_pass");
                userInfo.user_phone = rs.getString("user_phone");
                userInfo.user_school_number = rs.getString("user_school_number");
                userInfo.user_qq = rs.getString("user_qq");
                userInfo.user_birth = rs.getString("user_birth");
                userInfo.user_sex = rs.getString("user_sex");
                userInfo.uuid = rs.getString("uuid");
                UserInfo_List.userInfo_List.add(userInfo);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String Select_UserPhoto_By_QQ(String qq) {
        String photo="123";
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "select * from user_info where user_qq='"+qq+"'";
            ResultSet rs = st.executeQuery(sql);
            UserInfo_List.userInfo_List = new ArrayList<>();
            while (rs.next()) {
                photo = rs.getString("user_photo");
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }

    public static void Insert_User(UserInfo userInfo) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "insert into user_info (user_photo,user_name,user_pass,user_phone,user_school_number,user_qq,user_birth,user_sex) values('" + userInfo.photo + "','" + userInfo.user_name + "','" + userInfo.user_pass + "','" + userInfo.user_phone + "','" + userInfo.user_school_number + "','" + userInfo.user_qq + "','" + userInfo.user_birth + "','" + userInfo.user_sex + "');";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Update_User(UserInfo userInfo) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "update user_info set uuid='"+userInfo.uuid+"' where user_id='"+userInfo.user_id+"'";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Submit_Eidt_User(UserInfo userInfo) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "update user_info set user_name='"+userInfo.user_name+"'," +
                    "user_pass='"+userInfo.user_pass+"'" +
                    ",user_phone='"+userInfo.user_phone+"'," +
                    "user_school_number='"+userInfo.user_school_number+"'," +
                    "user_qq='"+userInfo.user_qq+"'," +
                    "user_photo='"+userInfo.photo+"'," +
                    "user_birth='"+userInfo.user_birth+"' where user_id='"+userInfo.user_id+"'";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Insert_KD(Static_KD static_kd) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "insert into androidkuaidi(ji_name,ji_phone,ji_address,shou_name,shou_phone,shou_address,worker_name,kd_status) values('" + static_kd.ji_name + "','" + static_kd.ji_phone + "','" + static_kd.ji_address + "','" + static_kd.shou_name + "','" + static_kd.shou_phone + "','" + static_kd.shou_address + "','" + static_kd.worker_name + "','运输中')";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Query_KD() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "select * from androidkuaidi";
            ResultSet rs = st.executeQuery(sql);
            KD_List.kd_list = new ArrayList<>();
            while (rs.next()) {
                Kd_information kd_information = new Kd_information();
                kd_information.kd_id = rs.getInt("kuaidi_id") + "";
                kd_information.ji_name = rs.getString("ji_name");
                kd_information.ji_phone = rs.getString("ji_phone");
                kd_information.ji_address = rs.getString("ji_address");
                kd_information.shou_name = rs.getString("shou_name");
                kd_information.shou_phone = rs.getString("shou_phone");
                kd_information.shou_address = rs.getString("shou_address");
                kd_information.worker_name = rs.getString("worker_name");
                kd_information.kd_status = rs.getString("kd_status");
                KD_List.kd_list.add(kd_information);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Query_Pyq() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "select *from pyq_table order by pyq_time desc";
            ResultSet rs = st.executeQuery(sql);
            PyqList.list = new ArrayList<>();
            while (rs.next()) {
                Pyq_Info pyq_info = new Pyq_Info();
                pyq_info.pyq_name = rs.getString("user_name");
                pyq_info.pyq_time = rs.getString("pyq_time");
                pyq_info.pyq_text = rs.getString("pyq_text");
                pyq_info.pyq_photo = rs.getString("pyq_photo");
                pyq_info.pyq_pl = rs.getString("pyq_pl");
                pyq_info.pyq_zan = rs.getString("pyq_zan");
                pyq_info.qq = rs.getString("qq");
                PyqList.list.add(pyq_info);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Insert_Pyq(Pyq_Info pyq_info) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "  insert into pyq_table (pyq_table.user_name,pyq_time,pyq_text,pyq_photo,pyq_pl,pyq_zan,qq) values('" + pyq_info.pyq_name + "','" + pyq_info.pyq_time + "','" + pyq_info.pyq_text + "','" + pyq_info.pyq_photo + "','','','"+pyq_info.qq +"')";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Insert_Pyq_PL(String pl, String name, String pyq_text) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = " update pyq_table set pyq_pl='" + pl + "' where user_name='" + name + "' and pyq_text='" + pyq_text + "'";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Insert_Pyq_Zan(String zan, String name, String pyq_text) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = " update pyq_table set pyq_zan='" + zan + "' where user_name='" + name + "' and pyq_text='" + pyq_text + "'";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Insert_Games(String name, String across, String speed) {
        boolean flag = false;
        String across_old = "";
        for (int i = 0; i < Games_List.names.size(); i++) {
            if (name.equals(Games_List.names.get(i))) {
                flag = true;
                across_old=Games_List.across.get(i);
            }
        }
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql="";
            if (!flag)
                sql = "insert into play_game(username,across,speed) values('" + name + "','" + across + "','" + speed + "')";
            else {
                if(across.compareTo(across_old)>=0)
                    sql = "update play_game set across='" + across + "',speed='" + speed + "' where username='" + name + "'";
            }
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Query_Games() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "  select * from play_game order by play_game.across desc";
            ResultSet rs = st.executeQuery(sql);
            Games_List.names = new ArrayList<>();
            Games_List.across = new ArrayList<>();
            Games_List.speeds = new ArrayList<>();
            while (rs.next()) {
                Games_List.names.add(rs.getString("username"));
                Games_List.across.add(rs.getString("across"));
                Games_List.speeds.add(rs.getString("speed"));
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Insert_Msg(Msg_info msg_info) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "insert into message_table(send_name,send_text,receive_name,send_time,huifu) " +
                    "values('" + msg_info.send_name + "','" + msg_info.send_text + "','" + msg_info.receive_name + "','" + msg_info.send_time + "','" + msg_info.huifu + "')";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Query_Msg() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "select * from message_table";
            ResultSet rs = st.executeQuery(sql);
            Msg_List.msg_list = new ArrayList<>();
            while (rs.next()) {
                Msg_info msg_info = new Msg_info();
                msg_info.send_name = rs.getString("send_name");
                msg_info.send_text = rs.getString("send_text");
                msg_info.receive_name = rs.getString("receive_name");
                msg_info.send_time = rs.getString("send_time");
                msg_info.huifu = rs.getString("huifu");
                Msg_List.msg_list.add(msg_info);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void Insert_Chat(Chat chat) {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            String sql = "insert into androidchat(username,content,time,qq)" +
                    "values('"+chat.username+"','"+chat.content+"','"+chat.time+"','"+chat.qq +"')";
            Statement st = cn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Query_Chat() {
        Chat_List.chat_list.clear();
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection cn = DriverManager.getConnection(db_url, username, password);
            Statement st = cn.createStatement();
            String sql = "select * from androidchat";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Chat chat = new Chat();
                chat.id=rs.getInt("id");
                chat.username=rs.getString("username");
                chat.content=rs.getString("content");
                chat.time=rs.getString("time");
                chat.qq=rs.getString("qq");
                Chat_List.chat_list.add(chat);
            }
            rs.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

