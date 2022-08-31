package com.example.curriculum_design.message;

public class Util {

    /**
     * 返回每个按钮应该出现的角度(弧度单位)
     * @param index
     * @return double 角度(弧度单位)
     */
    public static double getAngle(int total,int index){

        return Math.toRadians(90/(total-1)*index+90);
    }
}
