package com.example.curriculum_design;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.InitSql;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.UserInfo;
import com.githang.statusbar.StatusBarCompat;
import com.iflytek.cloud.FaceRequest;
import com.iflytek.cloud.RequestListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.mengpeng.mphelper.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Face_Register extends AppCompatActivity {
    private ImageView imageView;
    private static final int REGISTER = 0x1;
    Button btn_register;
    static int pos=0;
    Button go_login;
    Button goback;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_face_register);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white), false);
        Request();
        imageView = findViewById(R.id.imageView);
        btn_register = findViewById(R.id.button_reg);
        goback=findViewById(R.id.goback);
        //人脸注册
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //捕获照片
                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(getImageByCamera, REGISTER);
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Request() {
        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
            } else {
                //说明已经获取到摄像头权限了
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //是否返回正确
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REGISTER) {
            //即拍照所得的Bitmap
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            //开始请求平台识别
            //请求对象
            FaceRequest face = new FaceRequest(this);
            //设置参数
            face.setParameter(SpeechConstant.WFR_SST, "reg");
            //唯一ID
            face.setParameter(SpeechConstant.AUTH_ID, Now_User.User_id);
            //把bitmap转换成字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //一定要是JPEG，
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            byte[] imgData = out.toByteArray();
            face.sendRequest(imgData, mRequestListener);
        }
    }

    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onEvent(int i, Bundle bundle) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            //获取数据
            String json = new String(bytes);
            try {
                JSONObject jsonObject = new JSONObject(json);
                final UserInfo userInfo=new UserInfo();
                userInfo.user_id=Now_User.User_id;
                userInfo.uuid=(String)jsonObject.get("uid");
                Now_User.uuid=userInfo.uuid;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DbHelper.Update_User(userInfo);
                            InitSql.Init_Sql();
                        } catch (Exception e) {
                        }
                    }
                }).start();
                ToastUtils.onSuccessShowToast("注册成功");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            //完成，存在
            if (speechError == null) {
            } else {
                ToastUtils.onErrorShowToast("无网络或人脸数据已存在");
            }
        }

    };

}