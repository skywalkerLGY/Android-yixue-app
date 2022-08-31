package com.example.curriculum_design.Word_recite;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Word_List {
    public static ArrayList<String> english_list=new ArrayList<>();
    public static ArrayList<String> chinese_list=new ArrayList<>();

    public static int now_position_4 =0;
    public static int now_position_6 =0;



    public static void Init(Context context){
        try {
            InputStream is = context.getResources().getAssets().open("CET4.txt");
            InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
            BufferedReader bReader = new BufferedReader(isReader);
            String mimeTypeLine = null;
            while ((mimeTypeLine = bReader.readLine()) != null) {
                String[] mimeTypes = mimeTypeLine.trim().split("#");
                Word_List.english_list.add(mimeTypes[0]);
                Word_List.chinese_list.add(mimeTypes[1]);
            }
        } catch (Exception e) {
        }
    }
}
