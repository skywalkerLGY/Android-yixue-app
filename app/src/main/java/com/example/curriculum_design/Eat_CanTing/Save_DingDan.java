package com.example.curriculum_design.Eat_CanTing;

import java.io.Serializable;
import java.util.ArrayList;

public class Save_DingDan implements Serializable {
    public ArrayList<String> time = new ArrayList<>();
    public ArrayList<String> eat_name = new ArrayList<>();
    public ArrayList<String> eat_money = new ArrayList<>();
    public ArrayList<String> eat_sum = new ArrayList<>();
    public ArrayList<Integer> eat_image = new ArrayList<>();
    public ArrayList<Boolean> status = new ArrayList<>();
    public ArrayList<Boolean> hp = new ArrayList<>();
}
