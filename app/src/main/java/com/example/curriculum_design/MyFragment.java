package com.example.curriculum_design;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.PyqList;
import com.example.curriculum_design.DB_Help.UserInfo;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.Lock.Static_LOCK;
import com.example.curriculum_design.My.VDrawerLayout;
import com.example.curriculum_design.Tools.UploadTools;
import com.example.curriculum_design.Video_DouYin.DouYinActivity;
import com.example.curriculum_design.Word_recite.Word_Recite;
import com.mengpeng.mphelper.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

public class MyFragment extends Fragment {

    View view;
    VDrawerLayout vd;
    LinearLayout line_dark_light;
    Button btn_dark_light, go_back_btn;
    CheckBox xiala_btn;
    TextView txt_nicheng, txt_qianming;
    TextView txt1, txt2, txt3, txt4, txt6;
    Switch aSwitch;
    Button choose_photo, btn_camera, btn_save, btn_face_register;
    ImageView imageView;
    LinearLayout change_gone;
    EditText edt_nicheng, edt_qianming;

    ImageView head_photo;
    private TextView pyq_sum;
    private TextView pyq;
    private TextView haoyou_sum;
    private TextView haoyou;
    private TextView dengji;
    private TextView dengji2;
    private TextView my_xiangce;
    private TextView zan;
    private TextView liulanjilu;
    private TextView caogao;
    private TextView kabao;
    private TextView shiping;
    private TextView kuaidi;
    private TextView kefu;
    String path="";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button btn1;
    LinearLayout btn_my_xiangce,btn_my_zan,btn_my_lishi,btn_my_caogao
            ,btn_my_kabao,btn_my_shiping,btn_my_kd,btn_my_kefu;
    LinearLayout pyq_btn,haoyou_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.my_page, container, false);
        sp = getActivity().getSharedPreferences("dark", Activity.MODE_PRIVATE);
        editor = sp.edit();
        MyInit();
        HeadInit();
        PersonInit();
        boolean isdark = sp.getBoolean("dark", false);
        if (isdark) {
            SetAlph_TXT(220);
            btn1.setBackgroundResource(R.drawable.light);
            btn_dark_light.setText("日间模式");
            line_dark_light.getBackground().setAlpha(220);
        } else {
            SetAlph_TXT(0);
            btn1.setBackgroundResource(R.drawable.dark);
            btn_dark_light.setText("夜间模式");
            line_dark_light.getBackground().setAlpha(0);
        }
        return view;
    }

    void SetAlph_TXT(int a) {
        btn_dark_light.setTextColor(Color.argb(255, a, a, a));
        txt_nicheng.setTextColor(Color.argb(255, a, a, a));
        txt_qianming.setTextColor(Color.argb(255, a, a, a));

        txt3.setTextColor(Color.argb(255, a, a, a));
        txt4.setTextColor(Color.argb(255, a, a, a));
        txt6.setTextColor(Color.argb(255, a, a, a));
        pyq_sum.setTextColor(Color.argb(255, a, a, a));
        pyq.setTextColor(Color.argb(255, a, a, a));
        haoyou_sum.setTextColor(Color.argb(255, a, a, a));
        haoyou.setTextColor(Color.argb(255, a, a, a));
        dengji.setTextColor(Color.argb(255, a, a, a));
        dengji2.setTextColor(Color.argb(255, a, a, a));
        my_xiangce.setTextColor(Color.argb(255, a, a, a));
        zan.setTextColor(Color.argb(255, a, a, a));
        liulanjilu.setTextColor(Color.argb(255, a, a, a));
        caogao.setTextColor(Color.argb(255, a, a, a));
        kabao.setTextColor(Color.argb(255, a, a, a));
        shiping.setTextColor(Color.argb(255, a, a, a));
        kuaidi.setTextColor(Color.argb(255, a, a, a));
        kefu.setTextColor(Color.argb(255, a, a, a));
    }

    private void MyInit() {
        vd = view.findViewById(R.id.vd);
        view.findViewById(R.id.bm).getBackground().setAlpha(255);
        view.findViewById(R.id.the_gone).setVisibility(GONE);
    }

    EditText my_password, my_qq, my_school_id, my_phone_number, my_birth;

    void PersonInit() {
        choose_photo = view.findViewById(R.id.btn_photo);
        btn_camera = view.findViewById(R.id.btn_camera);
        imageView = view.findViewById(R.id.image_show);
        go_back_btn = view.findViewById(R.id.go_back_btn);
        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vd.openDrawer();
            }
        });
        RelativeLayout change_photo = view.findViewById(R.id.change_photo);
        change_gone = view.findViewById(R.id.change_gone);
        edt_nicheng = view.findViewById(R.id.edt_nicheng);
        edt_qianming = view.findViewById(R.id.edt_qianming);
        btn_save = view.findViewById(R.id.btn_save);
        btn_face_register = view.findViewById(R.id.btn_register_face);

        my_password = view.findViewById(R.id.my_password);
        my_school_id = view.findViewById(R.id.my_school_id);
        my_qq = view.findViewById(R.id.my_qq);
        my_phone_number = view.findViewById(R.id.my_phone_number);
        my_birth = view.findViewById(R.id.my_birth);
        my_password.setText(Now_User.user_pass);
        my_school_id.setText(Now_User.user_school_number);
        my_qq.setText(Now_User.user_qq);
        my_phone_number.setText(Now_User.user_phone);
        my_birth.setText(Now_User.user_birth);

        btn_my_xiangce=view.findViewById(R.id.btn_my_xiangce);
        btn_my_zan=view.findViewById(R.id.btn_my_zan);
        btn_my_lishi=view.findViewById(R.id.btn_my_lishi);
        btn_my_caogao=view.findViewById(R.id.btn_my_caogao);
        btn_my_kabao=view.findViewById(R.id.btn_my_kabao);
        btn_my_shiping=view.findViewById(R.id.btn_my_shiping);
        btn_my_kd=view.findViewById(R.id.btn_my_kd);
        btn_my_kefu=view.findViewById(R.id.btn_my_kefu);
        pyq_btn=view.findViewById(R.id.pyq_btn);
        haoyou_btn=view.findViewById(R.id.haoyou_btn);

        pyq_sum = view.findViewById(R.id.pyq_sum);
        pyq = view.findViewById(R.id.pyq);
        haoyou_sum = view.findViewById(R.id.haoyou_sum);
        haoyou = view.findViewById(R.id.haoyou);
        dengji = view.findViewById(R.id.dengji);
        dengji2 = view.findViewById(R.id.dengji2);
        my_xiangce = view.findViewById(R.id.my_xiangce);
        zan = view.findViewById(R.id.zan);
        liulanjilu = view.findViewById(R.id.liulanjilu);
        caogao = view.findViewById(R.id.caogao);
        kabao = view.findViewById(R.id.kabao);
        shiping = view.findViewById(R.id.shiping);
        kuaidi = view.findViewById(R.id.kuaidi);
        kefu = view.findViewById(R.id.kefu);
        pyq_sum.setText(PyqList.list.size() + "");
        haoyou_sum.setText(UserInfo_List.userInfo_List.size() + "");
        btn_my_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(getActivity(),gaokaoActivity.class);
                 startActivity(intent);
            }
        });
        btn_my_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),kaoyanActivity.class);
                startActivity(intent);
            }
        });
        btn_my_lishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Word_Recite.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
        btn_my_caogao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),bookshopActivity.class);
                startActivity(intent);
            }
        });
        btn_my_kabao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Web_Activity_about.class);
                intent.putExtra("web_url", "https://support.qq.com/product/401853");
                startActivity(intent);
            }
        });
        btn_my_shiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DouYinActivity.class);
                startActivity(intent);
            }
        });
        btn_my_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1469199086";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    Toast.makeText(getActivity(), "正在跳转至与客服的聊天", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "请检查是否安装QQ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_my_kd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), My_KdActivity.class);
                startActivity(intent);
            }
        });
        pyq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.btn_find.performClick();
            }
        });
        haoyou_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.btn_message.performClick();
            }
        });
        if (Now_User.nicheng != null)
            edt_nicheng.setText(Now_User.nicheng);
        if (Now_User.qianming != null)
            edt_qianming.setText(Now_User.qianming);
        if (Now_User.photo != null) {
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(0);
//            imageView.setImageBitmap(convertStringToIcon(Now_User.photo));
            Glide.with(getActivity()).load(Now_User.photo).into(imageView);
        }


        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change_gone.getVisibility() == View.INVISIBLE) {
                    change_gone.setVisibility(View.VISIBLE);
                    photo_init();

                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
final UserInfo userInfo=new UserInfo();
userInfo.user_id=Now_User.User_id;
userInfo.user_pass=my_password.getText().toString();
userInfo.user_school_number=my_school_id.getText().toString();
userInfo.user_qq=my_qq.getText().toString();
userInfo.user_phone=my_phone_number.getText().toString();
userInfo.user_birth=my_birth.getText().toString();
userInfo.user_name=edt_nicheng.getText().toString();
if(!path.equals(""))
    userInfo.photo=path;
else
    userInfo.photo=Now_User.photo;

Now_User.user_pass=userInfo.user_pass;
Now_User.user_school_number=userInfo.user_school_number;
Now_User.user_qq=userInfo.user_qq;
Now_User.user_phone=userInfo.user_phone;
Now_User.user_birth=userInfo.user_birth;
Now_User.User_Name=userInfo.user_name;
if(!path.equals(""))
    Now_User.photo=path;

Now_User.nicheng = edt_nicheng.getText().toString();
Now_User.qianming = edt_qianming.getText().toString();
/**
 * 更新head的三个元素
 */
txt_nicheng.setText(Now_User.nicheng);
txt_qianming.setText(Now_User.qianming);
if (Now_User.photo != null) {
    head_photo.setImageDrawable(null);
    head_photo.setBackgroundResource(0);
    Glide.with(getActivity()).load(Now_User.photo).into(head_photo);
    new Thread(new Runnable() {
        @Override
        public void run() {
            DbHelper.Submit_Eidt_User(userInfo);
            DbHelper.Query_User();
        }
    }).start();
}
ToastUtils.onSuccessShowToast("修改成功");
            }
        });
        btn_face_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Face_Register.class);
                startActivity(intent);
            }
        });
    }

    void HeadInit() {
        xiala_btn = view.findViewById(R.id.xiala_btn);
        head_photo = view.findViewById(R.id.photo);
        line_dark_light = view.findViewById(R.id.line_light_dark);
        line_dark_light.getBackground().setAlpha(0);
        txt_nicheng = view.findViewById(R.id.txt_nicheng);
        txt_qianming = view.findViewById(R.id.txt_qianming);
        /**
         * 初始化头像和签名昵称
         */
        if (Now_User.nicheng != null)
            txt_nicheng.setText(Now_User.nicheng);
        if (Now_User.qianming != null)
            txt_qianming.setText(Now_User.qianming);
        if (Now_User.photo != null) {
            head_photo.setImageDrawable(null);
            head_photo.setBackgroundResource(0);
//            head_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
            Glide.with(getActivity()).load(Now_User.photo).into(head_photo);
        }

        txt3 = view.findViewById(R.id.text2_0);
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.btn_find.performClick();
            }
        });
        txt4 = view.findViewById(R.id.text2_1);
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.onErrorShowToast("此功能尚未完成");
            }
        });
        aSwitch = view.findViewById(R.id.lock_open_close);
        txt6 = view.findViewById(R.id.txt6);
//        txt_zhou = view.findViewById(R.id.txt_zhou);
//        txt_date = view.findViewById(R.id.txt_date);
        if (Static_LOCK.flag == true) {
            aSwitch.setChecked(true);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //开启锁屏服务
                    Static_LOCK.flag = true;
                    MainActivity.startLockScreen();
                } else {
                    //关闭锁屏服务
                    Static_LOCK.flag = false;
                    MainActivity.stopLockScreen();
                }
            }
        });
        btn_dark_light = view.findViewById(R.id.btn_dark_light);
        btn1 = view.findViewById(R.id.btn1);
        btn_dark_light.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (line_dark_light.getBackground().getAlpha() == 0) {
                    btn1.setBackgroundResource(R.drawable.light);
                    btn_dark_light.setText("日间模式");
                    //此刻是夜间
                    editor.putBoolean("dark", true);
                    editor.commit();
                } else if (line_dark_light.getBackground().getAlpha() == 220) {
                    btn1.setBackgroundResource(R.drawable.dark);
                    btn_dark_light.setText("夜间模式");
                    //此刻是日间
                    editor.putBoolean("dark", false);
                    editor.commit();
                }
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        if (line_dark_light.getBackground().getAlpha() == 0) {
                            for (int i = 0; i <= 220; i++) {//夜间
                                line_dark_light.getBackground().setAlpha(i);
                                final int finalI = i;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SetAlph_TXT(finalI);
                                    }
                                });
                                try {
                                    Thread.sleep(4);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (line_dark_light.getBackground().getAlpha() == 220) {
                            for (int i = 220; i >= 0; i--) {
                                line_dark_light.getBackground().setAlpha(i);
                                final int finalI = i;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SetAlph_TXT(finalI);
                                    }
                                });
                                try {
                                    Thread.sleep(4);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }).start();
            }
        });
        xiala_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vd.closeDrawer();
            }
        });
    }


    /**
     * 以下是选取图片或者拍照的方法----------------------------------------
     */
    public static final int TAKE_CAMERA = 101;
    public static final int PICK_PHOTO = 102;
    private Uri imageUri;

    void photo_init() {

        //通过摄像头拍照
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用相机拍照
                // 创建File对象，用于存储拍照后的图片
                //存放在手机SD卡的应用关联缓存目录下
                File outputImage = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
               /* 从Android 6.0系统开始，读写SD卡被列为了危险权限，如果将图片存放在SD卡的任何其他目录，
                  都要进行运行时权限处理才行，而使用应用关联 目录则可以跳过这一步
                */
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*
                   7.0系统开始，直接使用本地真实路径的Uri被认为是不安全的，会抛 出一个FileUriExposedException异常。
                   而FileProvider则是一种特殊的内容提供器，它使用了和内 容提供器类似的机制来对数据进行保护，
                   可以选择性地将封装过的Uri共享给外部，从而提高了 应用的安全性
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //大于等于版本24（7.0）的场合
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.example.curriculum_design", outputImage);
                } else {
                    //小于android 版本7.0（24）的场合
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_CAMERA);
            }
        });
        //从相册选择图片
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请获取访问 读写磁盘的权限
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    //打开相册
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO); // 打开相册
                }
            }
        });
    }
    public File saveFile(Bitmap bm) throws IOException {
        String fileName =UUID.randomUUID()+".jpg";
        String path = Environment.getExternalStorageDirectory() + "/cads/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        final Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Now_User.photo = convertIconToString(bitmap);
                            }
                        }).start();
                        imageView.setImageDrawable(null);
                        imageView.setBackgroundResource(0);
                        imageView.setImageBitmap(bitmap);
                        File file = saveFile(bitmap);
                        /**
                         * 上传
                         */
                        UploadTools.UpLoad(IP.file_ip+"/myloadfile",file);//okhttp的上传
                        /**
                         * 得到图片的path，存储下来
                         */
                        path = IP.file_ip+"/myfile/" + file.getName();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                change_gone.setVisibility(View.INVISIBLE);
                break;
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) { // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                change_gone.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        // 根据图片路径显示图片
        displayImage(imagePath);
    }

    /**
     * android 4.4以前的处理方式
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Now_User.photo = convertIconToString(bitmap);
                }
            }).start();
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(0);
            imageView.setImageBitmap(bitmap);
            File file = new File(imagePath);
            /**
             * 上传
             */
            UploadTools.UpLoad(IP.file_ip+"/myloadfile",file);//okhttp的上传
            /**
             * 得到图片的path，存储下来
             */
            path = IP.file_ip+"/myfile/" + file.getName();
        } else {
            Toast.makeText(getActivity(), "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        String img = Base64.encodeToString(appicon, Base64.DEFAULT);
        return img;

    }


    /**
     * string转成bitmap
     *
     * @param str
     */
    public static Bitmap convertStringToIcon(String str) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
}