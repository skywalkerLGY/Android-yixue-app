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
            btn_dark_light.setText("????????????");
            line_dark_light.getBackground().setAlpha(220);
        } else {
            SetAlph_TXT(0);
            btn1.setBackgroundResource(R.drawable.dark);
            btn_dark_light.setText("????????????");
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
                    //??????????????????????????????????????????????????????qq??????????????????????????????
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1469199086";//uin??????????????????qq??????
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    Toast.makeText(getActivity(), "?????????????????????????????????", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "?????????????????????QQ", Toast.LENGTH_SHORT).show();
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
 * ??????head???????????????
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
ToastUtils.onSuccessShowToast("????????????");
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
         * ??????????????????????????????
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
                ToastUtils.onErrorShowToast("?????????????????????");
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
                    //??????????????????
                    Static_LOCK.flag = true;
                    MainActivity.startLockScreen();
                } else {
                    //??????????????????
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
                    btn_dark_light.setText("????????????");
                    //???????????????
                    editor.putBoolean("dark", true);
                    editor.commit();
                } else if (line_dark_light.getBackground().getAlpha() == 220) {
                    btn1.setBackgroundResource(R.drawable.dark);
                    btn_dark_light.setText("????????????");
                    //???????????????
                    editor.putBoolean("dark", false);
                    editor.commit();
                }
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        if (line_dark_light.getBackground().getAlpha() == 0) {
                            for (int i = 0; i <= 220; i++) {//??????
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
     * ??????????????????????????????????????????----------------------------------------
     */
    public static final int TAKE_CAMERA = 101;
    public static final int PICK_PHOTO = 102;
    private Uri imageUri;

    void photo_init() {

        //?????????????????????
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                // ??????File???????????????????????????????????????
                //???????????????SD?????????????????????????????????
                File outputImage = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
               /* ???Android 6.0?????????????????????SD??????????????????????????????????????????????????????SD???????????????????????????
                  ??????????????????????????????????????????????????????????????? ??????????????????????????????
                */
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*
                   7.0????????????????????????????????????????????????Uri????????????????????????????????? ?????????FileUriExposedException?????????
                   ???FileProvider????????????????????????????????????????????????????????? ??????????????????????????????????????????????????????
                   ?????????????????????????????????Uri????????????????????????????????? ??????????????????
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //??????????????????24???7.0????????????
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.example.curriculum_design", outputImage);
                } else {
                    //??????android ??????7.0???24????????????
                    imageUri = Uri.fromFile(outputImage);
                }
                //??????????????????
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_CAMERA);
            }
        });
        //?????????????????????
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????? ?????????????????????
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    //????????????
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO); // ????????????
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
                        // ??????????????????????????????
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
                         * ??????
                         */
                        UploadTools.UpLoad(IP.file_ip+"/myloadfile",file);//okhttp?????????
                        /**
                         * ???????????????path???????????????
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
                if (resultCode == RESULT_OK) { // ???????????????????????????
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4?????????????????????????????????????????????
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4??????????????????????????????????????????
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
            // ?????????document?????????Uri????????????document id??????
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // ????????????????????????id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // ?????????content?????????Uri??????????????????????????????
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // ?????????file?????????Uri?????????????????????????????????
            imagePath = uri.getPath();
        }
        // ??????????????????????????????
        displayImage(imagePath);
    }

    /**
     * android 4.4?????????????????????
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
        // ??????Uri???selection??????????????????????????????
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
             * ??????
             */
            UploadTools.UpLoad(IP.file_ip+"/myloadfile",file);//okhttp?????????
            /**
             * ???????????????path???????????????
             */
            path = IP.file_ip+"/myfile/" + file.getName();
        } else {
            Toast.makeText(getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * ????????????string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] appicon = baos.toByteArray();// ??????byte??????
        String img = Base64.encodeToString(appicon, Base64.DEFAULT);
        return img;

    }


    /**
     * string??????bitmap
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