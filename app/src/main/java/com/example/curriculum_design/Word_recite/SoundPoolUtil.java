package com.example.curriculum_design.Word_recite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import com.example.curriculum_design.R;

public class SoundPoolUtil {
    private static SoundPoolUtil soundPoolUtil;
    private SoundPool soundPool;

    //单例模式
    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null)
            soundPoolUtil = new SoundPoolUtil(context);
        return soundPoolUtil;
    }

    @SuppressLint("NewApi")//这里初始化SoundPool的方法是安卓5.0以后提供的新方式
    private SoundPoolUtil(Context context) {
//        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        soundPool = new SoundPool.Builder().build();
        //加载音频文件
        soundPool.load(context, R.raw.back_music, 1);
        soundPool.load(context, R.raw.correct_tip, 2);
        soundPool.load(context, R.raw.error_tip, 3);
        soundPool.load(context, R.raw.relax, 4);

    }

    public void play(int number) {
        Log.d("tag", "number " + number);
        //播放音频
        soundPool.play(number, 1, 1, 0, 0, 1);
    }
}
