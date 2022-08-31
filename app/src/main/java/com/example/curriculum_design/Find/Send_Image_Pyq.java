package com.example.curriculum_design.Find;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.Pyq_Info;
import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.R;
import com.example.curriculum_design.Tools.RealPathFromUriUtils;
import com.githang.statusbar.StatusBarCompat;
import com.mengpeng.mphelper.ToastUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Send_Image_Pyq extends AppCompatActivity {

    Button goback;
    Button send;
    ImageView choose_photo;
    EditText send_text, send_time;
    static String imagepAth = null;
    public static File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_send__image__pyq);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);

        final String send_name = getIntent().getStringExtra("send_name");
        send = findViewById(R.id.send);
        goback = findViewById(R.id.goback);
        send_text = findViewById(R.id.send_text);
        send_time = findViewById(R.id.send_time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd日 HH:mm");
        Date date = new Date(System.currentTimeMillis());
        send_time.setText(simpleDateFormat.format(date));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Pyq_Info pyq_info = new Pyq_Info();
                pyq_info.pyq_text = send_text.getText().toString();
                pyq_info.pyq_photo = imagepAth;
                pyq_info.pyq_name = send_name;
                pyq_info.pyq_time = send_time.getText().toString();
                pyq_info.qq = Now_User.user_qq;
                Toast.makeText(Send_Image_Pyq.this, "图片朋友圈发表成功", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper.Insert_Pyq(pyq_info);
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            // 隐藏软键盘
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                        overridePendingTransition(R.anim.null_in, R.anim.login_out);
                    }
                }).start();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
        choose_photo = findViewById(R.id.send_image);
        photo_init();
    }

    /**
     * 以下是选取图片或者拍照的方法----------------------------------------
     */
    public static final int PICK_PHOTO = 102;
    private Uri imageUri;

    void photo_init() {
        //从相册选择图片
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请获取访问 读写磁盘的权限
                if (ContextCompat.checkSelfPermission(Send_Image_Pyq.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Send_Image_Pyq.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 2) {
            if (intent != null) {
                // 得到图片的全路径
                Uri uri = intent.getData();
                String imagePath = null;//=handleImageOnKitKat(intent);
                if (Build.VERSION.SDK_INT >= 24) {
                    imagePath = getFilePathFromURI(this, uri);
                } else {
                    imagePath = RealPathFromUriUtils.getRealPathFromUri(this, uri);
                }
                file = new File(imagePath);
                if (imagePath != null) {
                    imagepAth = imagePath;
                    final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    choose_photo.setImageDrawable(null);
                    choose_photo.setBackgroundResource(0);
                    choose_photo.setImageBitmap(bitmap);
                } else {
                    ToastUtils.onErrorShowToast("图片获取失败");
                }
//                Toast.makeText(Send_Image_Pyq.this, file.getName(), Toast.LENGTH_SHORT).show();
                UpLoad(IP.file_ip + "/myloadfile");
                imagepAth = IP.file_ip + "/myfile/" + file.getName();
            }
        }
    }


    private String getFilePathFromURI(Context context, Uri contentUri) {
        File rootDataDir = context.getFilesDir();
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(rootDataDir + File.separator + fileName + ".jpg");
            copyFile(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    private String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return System.currentTimeMillis() + fileName;
    }

    private void copyFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int copyStream(InputStream input, OutputStream output) throws Exception, IOException {
        final int BUFFER_SIZE = 1024 * 2;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return count;
    }

    void UpLoad(String urls) {
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img_file", file.getName(), fileBody)
                .addFormDataPart("filename", file.getName())
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        //下面是为了返回执行结果,可以不看
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Message message = new Message();
                message.obj = response.body().string();
                System.out.println(message);
                Looper.prepare();
                Looper.loop();
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(Send_Image_Pyq.this, e.toString(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
}