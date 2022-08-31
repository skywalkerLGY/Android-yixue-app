package com.example.curriculum_design;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.ChatRoom.Static_Msg;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.PyqList;
import com.example.curriculum_design.DB_Help.Pyq_Info;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.Find.Send_Image_Pyq;
import com.example.curriculum_design.Find.Send_Pyq;
import com.example.curriculum_design.Video_DouYin.DouYinActivity;
import com.example.curriculum_design.message.Util;
import com.mengpeng.mphelper.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static java.lang.Thread.sleep;

public class FindFragment extends Fragment {
    View view;
    ListView listView;
    public MyListAdapter myListAdapter = null;
    static int top = 0;
    int flag_pyq = 1;
    Button btn_refresh;
    ImageView person_photo;
    RelativeLayout rel_top;
    ImageView goback, camera;
    NestedScrollView nestedScrollView;
    ImageView view_pyq_back;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x00) {
                Refresh_pyq();
            }
            if (msg.what == 0x01) {
//                Toast.makeText(getActivity(), "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.find_page, container, false);
        MyInit();

//        btn_refresh.performClick();
        return view;
    }

    void Refresh_pyq() {
        Pyq_Info pyq_info = new Pyq_Info();
        myListAdapter.notifyDataSetChanged();
    }

    private void MyInit() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //代码中控制显隐藏
                ProgressBar mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_main);

                mProgressBar.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(500);
                    if(PyqList.list.size()>0)
                        mProgressBar.setVisibility(View.INVISIBLE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        view_pyq_back = view.findViewById(R.id.pyq_user_back);
        photo_init();
        listView = view.findViewById(R.id.pyq_listview);
        myListAdapter = new MyListAdapter(getActivity());
        listView.setAdapter(myListAdapter);
        nestedScrollView = view.findViewById(R.id.pyq_scrollview);
        rel_top = view.findViewById(R.id.rel_pyq_top);
        goback = view.findViewById(R.id.goback);
        camera = view.findViewById(R.id.camera);
        final TextView pyq_text = view.findViewById(R.id.pyq_text);
        pyq_text.setTextColor(Color.argb(0, 0, 0, 0));
        rel_top.getBackground().setAlpha(0);
        goback.getBackground().setAlpha(0);
        camera.getBackground().setAlpha(0);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((int) (scrollY / 2.9) <= 255) {
                    rel_top.getBackground().setAlpha((int) (scrollY / 2.9));
                    pyq_text.setTextColor(Color.argb((int) (scrollY / 2.9), 0, 0, 0));
                    goback.getBackground().setAlpha((int) (scrollY / 2.9));
                    camera.getBackground().setAlpha((int) (scrollY / 2.9));
                } else {
                    rel_top.getBackground().setAlpha(255);
                    goback.getBackground().setAlpha(255);
                    camera.getBackground().setAlpha(255);
                    pyq_text.setTextColor(Color.argb(255, 0, 0, 0));
                }
            }
        });
        person_photo = view.findViewById(R.id.user_photo);
        if (Now_User.photo != null) {
//            person_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
            Glide.with(getActivity()).load(Now_User.photo).into(person_photo);
        }
        TextView name_nicheng = view.findViewById(R.id.find_nicheng);
        name_nicheng.setText(Now_User.nicheng);
        final Button btn_add = view.findViewById(R.id.add_pyq);
        final Button btn_tu, btn_wenben, btn_video;
        btn_tu = view.findViewById(R.id.btn_tu);
        btn_wenben = view.findViewById(R.id.btn_wenben);
        btn_video = view.findViewById(R.id.btn_video);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        final List<Button> buttonItems = new ArrayList<Button>();
        buttonItems.add(btn_tu);
        buttonItems.add(btn_wenben);
        buttonItems.add(btn_video);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnimation(buttonItems, 550, flag_pyq);
                flag_pyq = -flag_pyq;
            }
        });
        btn_wenben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Send_Pyq.class);
                intent.putExtra("send_name", Now_User.User_Name);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
                buttonAnimation(buttonItems, 550, flag_pyq);
                flag_pyq = -flag_pyq;
            }
        });
        btn_tu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Send_Image_Pyq.class);
                intent.putExtra("send_name", Now_User.User_Name);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
                buttonAnimation(buttonItems, 550, flag_pyq);
                flag_pyq = -flag_pyq;
            }
        });
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DouYinActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
                buttonAnimation(buttonItems, 550, flag_pyq);
                flag_pyq = -flag_pyq;
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DbHelper.Query_Pyq();//重新刷新朋友圈列表
                        } catch (Exception e) {

                        }
                    }
                }).start();
                handler.sendEmptyMessageDelayed(0x00, 1500);
                // 按钮旋转
                final RotateAnimation animation = new RotateAnimation(0.0f, 180.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(300);
                btn_refresh.startAnimation(animation);
            }
        });
        String imagePath= (String) ObjectSaveUtils.getObject(getActivity(),"pyq_image_back");
        if(imagePath!=null){
            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            view_pyq_back.setImageDrawable(null);
            view_pyq_back.setBackgroundResource(0);
            view_pyq_back.setImageBitmap(bitmap);
        }
    }

    String Select_UserPhoto_By_QQ(String qq){
        for(int i=0;i< UserInfo_List.userInfo_List.size();i++){
            if(qq.equals(UserInfo_List.userInfo_List.get(i).user_qq)){
                return UserInfo_List.userInfo_List.get(i).photo;
            }
        }
        return "123";
    }
    class MyListAdapter extends BaseAdapter implements Animation.AnimationListener {

        Context context;
        TextView mAdd1;

        public MyListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return PyqList.list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pyq_list_layout, parent, false);
            Button btn_zhiding = convertView.findViewById(R.id.pyq_zhiding);
            final TextView nametext = convertView.findViewById(R.id.pyq_name);
            final TextView texttext = convertView.findViewById(R.id.pyq_text);
            TextView text_time = convertView.findViewById(R.id.pyq_time);
            ImageView imageView = convertView.findViewById(R.id.pyq_photo);
            final ImageView image = convertView.findViewById(R.id.pyq_image);
            final Button btn_pinglun = convertView.findViewById(R.id.btn_pinglun);
            final Button btn_zan = convertView.findViewById(R.id.btn_zan);
            final TextView mAdd = convertView.findViewById(R.id.zan);
            final ListView listView_pl = convertView.findViewById(R.id.pinglun_listview);
            final TextView text_zan = convertView.findViewById(R.id.zan_text);
            listView_pl.setDivider(null);

            if (PyqList.list != null && PyqList.list.size() > position) {
                if (PyqList.list.size() > position)
                    nametext.setText(PyqList.list.get(position).pyq_name);
                if (PyqList.list.size() > position)
                    texttext.setText(PyqList.list.get(position).pyq_text);
                if (PyqList.list.size() > position)
                    text_time.setText(PyqList.list.get(position).pyq_time);
                /**
                 * 适配评论
                 */
                String plplpl = "";
                if (PyqList.list.size() > position && PyqList.list.get(position).pyq_pl != null && !PyqList.list.get(position).pyq_pl.equals("")) {
                    plplpl = PyqList.list.get(position).pyq_pl;
                }
                String[] plpl_shuzu = new String[0];
                if (!plplpl.equals("")) {
                    plpl_shuzu = plplpl.split("-");
                }
                if (plpl_shuzu.length > 0) {
                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context,
                            R.layout.simple_listlayout, plpl_shuzu);//适配器
                    listView_pl.setAdapter(myAdapter);
                }
                /**
                 * 适配赞
                 */
                String zanzanzan = "";
                if (PyqList.list.size() > position && PyqList.list.get(position).pyq_zan != null && !PyqList.list.get(position).pyq_zan.equals("")) {
                    zanzanzan = PyqList.list.get(position).pyq_zan;
                    text_zan.setText(zanzanzan + "等人觉得很赞");
                }
                final String name_string = nametext.getText().toString();
//                String XIN = name_string.substring(name_string.length() - 1, name_string.length());
//                imageView.setText(XIN);
//                PyqList.list.get(position).pyq_pl;
//                if (position % 5 == 0) {
//                    imageView.setBackgroundResource(R.drawable.pyq_p1);
//                }
//                if (position % 5 == 1) {
//                    imageView.setBackgroundResource(R.drawable.pyq_p2);
//                }
//                if (position % 5 == 2) {
//                    imageView.setBackgroundResource(R.drawable.pyq_p3);
//                }
//                if (position % 5 == 3) {
//                    imageView.setBackgroundResource(R.drawable.pyq_p4);
//                }
//                if (position % 5 == 4) {
//                    imageView.setBackgroundResource(R.drawable.pyq_p5);
//                }

                if (PyqList.list != null && PyqList.list.size() > position) {
                    String photo =Select_UserPhoto_By_QQ(PyqList.list.get(position).qq);
                    Glide.with(getActivity()).load(photo).into(imageView);
                    btn_zhiding.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (PyqList.list.size() > 1)
                                Collections.swap(PyqList.list, 0, position);
                            myListAdapter.notifyDataSetChanged();
                        }
                    });
                    final String finalPlplpl = plplpl;
                    final String[] finalPlpl_shuzu = plpl_shuzu;
                    btn_pinglun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(getActivity());
                            //去掉title
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();
                            Window window = dialog.getWindow();
                            // 设置布局
                            window.setContentView(R.layout.dialog_pyq_pl);
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            // 设置宽高
                            window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                            // 设置弹出的动画效果
                            window.setWindowAnimations(R.style.lxr_dialog);
                            window.setGravity(Gravity.BOTTOM);
                            TextView txt_receive_name = window.findViewById(R.id.txt_receive_name);
                            txt_receive_name.setText(name_string);
                            Button fason = window.findViewById(R.id.send_btn);
                            Button cancel_btn_pl = window.findViewById(R.id.cancel_btn_pl);
                            cancel_btn_pl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            final EditText edt_pl = window.findViewById(R.id.send_text);
                            fason.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final String name = nametext.getText().toString();
                                    final String pl = edt_pl.getText().toString();
                                    final String text = texttext.getText().toString();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                DbHelper.Insert_Pyq_PL(finalPlplpl + Now_User.User_Name + ":" + pl + "-", name, text);
                                            } catch (Exception e) {

                                            }
                                        }
                                    }).start();
                                    /**
                                     * 适配评论
                                     */
                                    String[] shuzu = new String[finalPlpl_shuzu.length + 1];
                                    System.arraycopy(finalPlpl_shuzu, 0, shuzu, 0, finalPlpl_shuzu.length);
                                    shuzu[finalPlpl_shuzu.length] = Now_User.User_Name + ":" + pl;
                                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context,
                                            R.layout.simple_listlayout, shuzu);//适配器
                                    listView_pl.setAdapter(myAdapter);
                                    dialog.cancel();
                                    ToastUtils.onSuccessShowToast("评论成功");
//                                    Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }
                if (PyqList.list != null && PyqList.list.size() > position) {
                    final String finalZanzanzan = zanzanzan;
                    btn_zan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 按钮旋转
                            final RotateAnimation animation = new RotateAnimation(0.0f, 180.0f,
                                    Animation.RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_SELF, 0.5f);
                            animation.setDuration(300);
                            btn_zan.startAnimation(animation);
                            if (text_zan.getText().toString().contains(Now_User.User_Name)) {
                                ToastUtils.onErrorShowToast("不能重复点赞");
                            } else {
                                final String name = nametext.getText().toString();
                                final String text = texttext.getText().toString();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            DbHelper.Insert_Pyq_Zan(finalZanzanzan + Now_User.User_Name + ",", name, text);
                                        } catch (Exception e) {

                                        }
                                    }
                                }).start();
                                sumAdd(mAdd);
                                text_zan.setText(finalZanzanzan + Now_User.User_Name + "," + "等人觉得很赞");
//                                Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            if (PyqList.list.size() > position) {
                if (PyqList.list.get(position).pyq_photo != null) {
                    if (PyqList.list.get(position).pyq_photo.equals("0")) {
                        image.setVisibility(View.GONE);
                    } else if (PyqList.list.get(position).pyq_photo.equals("show_default")) {
                        image.setImageDrawable(null);
                        image.setBackgroundResource(R.drawable.thankyou);
                    } else if (!PyqList.list.get(position).pyq_photo.equals("1")) {
//                        if (PyqList.list.get(position).pyq_photo.contains("http")) {
//                            image.setImageDrawable(null);
//                            image.setBackgroundResource(0);
//
//                        } else {
//                            final Bitmap bitmap = BitmapFactory.decodeFile(PyqList.list.get(position).pyq_photo);
//                            image.setImageDrawable(null);
//                            image.setBackgroundResource(0);
//                            image.setImageBitmap(bitmap);
//                        }
                        image.setImageDrawable(null);
                        image.setBackgroundResource(0);
                        Glide.with(getActivity()).load(PyqList.list.get(position).pyq_photo).into(image);
                    }
                }
            }
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog_Big_Image(image);
                }
            });
            return convertView;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mAdd1.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        public void sumAdd(TextView mAdd) {
            this.mAdd1 = mAdd;
            mAdd.setVisibility(View.VISIBLE);
            Animation animation = new AnimationUtils().loadAnimation(getActivity(), R.anim.btn_add);
            mAdd.startAnimation(animation);
            animation.setAnimationListener(this);
        }
    }
    public void ShowDialog_Big_Image(ImageView image) {
        final Dialog dialog = new Dialog(getActivity());
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_image_show);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.dialog_scale);
        window.setGravity(Gravity.CENTER);
        ImageView imageView = window.findViewById(R.id.big_image);
        image.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(image.getDrawingCache());
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
//        MyImageDialog myImageDialog = new MyImageDialog(MainActivity2.this, R.style.dialog_scale, 0, -300, bitmap);
//        myImageDialog.show();
    }
    /**
     * 按钮移动动画
     *
     * @params 子按钮列表
     * @params 弹出时圆形半径radius
     */
    public void buttonAnimation(final List<Button> buttonList, int radius, int flag) {

        for (int i = 0; i < buttonList.size(); i++) {

            ObjectAnimator objAnimatorX;
            ObjectAnimator objAnimatorY;
            ObjectAnimator objAnimatorRotate;

            // 将按钮设为可见
            buttonList.get(i).setVisibility(View.VISIBLE);

            // 按钮在X、Y方向的移动距离
            float distanceX = -(float) (flag * radius * (Math.cos(Util.getAngle(buttonList.size(), i))));
            float distanceY = (float) (flag * radius * (Math.sin(Util.getAngle(buttonList.size(), i))));

            // X方向移动
            objAnimatorX = ObjectAnimator.ofFloat(buttonList.get(i), "x", buttonList.get(i).getX(), buttonList.get(i).getX() + distanceX);
            objAnimatorX.setDuration(400);
            objAnimatorX.setStartDelay(100);
            objAnimatorX.start();

            // Y方向移动
            objAnimatorY = ObjectAnimator.ofFloat(buttonList.get(i), "y", buttonList.get(i).getY(), buttonList.get(i).getY() + distanceY);
            objAnimatorY.setDuration(400);
            objAnimatorY.setStartDelay(100);
            objAnimatorY.start();

            // 按钮旋转
            objAnimatorRotate = ObjectAnimator.ofFloat(buttonList.get(i), "rotation", 0, 360);
            objAnimatorRotate.setDuration(400);
            objAnimatorY.setStartDelay(100);
            objAnimatorRotate.start();

            if (flag == -1) {
                objAnimatorX.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // TODO Auto-generated method stub
                        // 将按钮设为可见
                        for (int i = 0; i < buttonList.size(); i++) {
                            buttonList.get(i).setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }
    }

    /**
     * 以下是选取图片或者拍照的方法----------------------------------------
     */
    public static final int PICK_PHOTO = 102;
    private Uri imageUri;

    void photo_init() {
        //从相册选择图片
        view_pyq_back.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
            view_pyq_back.setImageDrawable(null);
            view_pyq_back.setBackgroundResource(0);
            view_pyq_back.setImageBitmap(bitmap);
            ObjectSaveUtils.saveObject(getActivity(),"pyq_image_back",imagePath);
        } else {
            Toast.makeText(getActivity(), "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

}