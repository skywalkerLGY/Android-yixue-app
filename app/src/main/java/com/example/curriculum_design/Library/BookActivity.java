package com.example.curriculum_design.Library;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.bumptech.glide.Glide;
import com.example.curriculum_design.ChatRoom.ChatRoom;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.example.curriculum_design.Eat_CanTing.DianCanFragment.convertStringToIcon;

public class BookActivity extends AppCompatActivity implements CardStackView.ItemExpendListener{
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15
    };
    private CardStackView mStackView;
    private TestStackAdapter mTestStackAdapter;
    private ImageView user_photo;
    private TextView user_name;
    private Button btn;
    Button location_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_library);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        PermisionUtils.verifyStoragePermissions(BookActivity.this);
        ActivityCompat.checkSelfPermission(BookActivity.this,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        location_btn=findViewById(R.id.location_book);
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(this);
        mStackView.setAdapter(mTestStackAdapter);
        mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
        for(int i=0;i<15;i++){
            copyAssetAndWrite(Book_Path_Name.book_names[i]+".txt");
            File dataFile = new File(getCacheDir(), Book_Path_Name.book_names[i]+".txt");
            Book_Path_Name.path[i] = dataFile.getAbsolutePath();
        }
        user_photo=findViewById(R.id.user_photo);
        user_name=findViewById(R.id.user_name);
        user_name.setText(Now_User.nicheng);
//        if (Now_User.photo != null) {
//            user_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
//        }
        Glide.with(BookActivity.this).load("https://q1.qlogo.cn/g?b=qq&nk="+Now_User.user_qq+"&s=140").into(user_photo);
    }
    EditText edt_address_all;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String[] pros = {MediaStore.Files.FileColumns.DATA};
            try {
                Cursor cursor = managedQuery(uri, pros, null, null, null);
                int actual_txt_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(actual_txt_column_index);
                edt_address_all.setText(path);
            } catch (Exception e) {
            }
        }
    }
    public void showDialog() {
        final Dialog dialog = new Dialog(BookActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_choose_book);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.chat_Scale);
        window.setGravity(Gravity.CENTER);
        final EditText edt_address=window.findViewById(R.id.edit_address);
        edt_address_all=edt_address;
        Button btn_select_address =  window.findViewById(R.id.btn_select_address);
        Button cancel_btn =  window.findViewById(R.id.cancel_dialog);
        Button join_btn =  window.findViewById(R.id.join_dialog);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookActivity.this, "已确定", Toast.LENGTH_SHORT).show();
                TxtConfig.saveIsOnVerticalPageMode(BookActivity.this,false);
                HwTxtPlayActivity.loadTxtFile(BookActivity.this, edt_address.getText().toString().trim());
            }
        });
        btn_select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/plain");//设置类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 3);
            }
        });
    }
    /**
     * 将asset文件写入缓存
     */
    private boolean copyAssetAndWrite(String fileName) {
        try {
            File cacheDir = getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File outFile = new File(cacheDir, fileName);
            if (!outFile.exists()) {
                boolean res = outFile.createNewFile();
                if (!res) {
                    return false;
                }
            } else {
                if (outFile.length() > 10) {//表示已经写入一次
                    return true;
                }
            }
            InputStream is = getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void onItemExpend(boolean expend) {

    }
}