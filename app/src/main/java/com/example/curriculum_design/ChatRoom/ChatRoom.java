package com.example.curriculum_design.ChatRoom;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.curriculum_design.DB_Help.Chat;
import com.example.curriculum_design.DB_Help.Chat_List;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.MainActivity;
import com.example.curriculum_design.R;
import com.example.curriculum_design.message.HiddenAnimUtils;
import com.githang.statusbar.StatusBarCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Button back;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    Button btn_caidan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_chat_room);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
//        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
        //name就是对话框传过来的name昵称，最好不用吧

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        /**
                         * 每隔三秒钟刷新一次数据
                         */
                        for (int i = msgList.size(); i < Chat_List.chat_list.size(); i++) {
                            final Msg message = new Msg();
                            Chat chat = Chat_List.chat_list.get(i);
                            message.user_name = chat.username;
                            message.content = chat.content;
                            if (Now_User.User_Name.equals(chat.username)) {
                                message.type = Msg.SENT;
                            } else
                                message.type = Msg.RECEIVE;
                            message.time = chat.time;
                            message.qq = chat.qq;
                            //给message的数据
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    msgList.add(message);
                                    adapter.notifyItemInserted(msgList.size() - 1);
                                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                                }
                            });
                        }
                        DbHelper.Query_Chat();
                        /**
                         * 与老数据相比较，若多出，则刷新，否则不刷新
                         */
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        adapter = new MsgAdapter(msgList, ChatRoom.this);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        msgRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoom.this);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ChatRoom.this, msgRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onLongItemClick(View view, final int position) {
                        if (view.findViewById(R.id.left_dialog).getVisibility() == View.VISIBLE) {
                            view.findViewById(R.id.left_dialog).setVisibility(View.INVISIBLE);
                        } else if (view.findViewById(R.id.right_dialog).getVisibility() == View.VISIBLE) {
                            view.findViewById(R.id.right_dialog).setVisibility(View.INVISIBLE);
                        } else {
                            Button btn_delete = null;
                            Button btn_high = null;
                            LinearLayout linearLayout = null;
                            TextView textView = null;
                            if (view.findViewById(R.id.left_layout).getVisibility() == View.GONE) {
                                btn_delete = view.findViewById(R.id.btn_delete2);
                                btn_high = view.findViewById(R.id.btn_high_color2);
                                linearLayout = view.findViewById(R.id.right_dialog);
                                textView = view.findViewById(R.id.right_msg);
                            } else if (view.findViewById(R.id.right_layout).getVisibility() == View.GONE) {
                                btn_delete = view.findViewById(R.id.btn_delete1);
                                btn_high = view.findViewById(R.id.btn_high_color1);
                                linearLayout = view.findViewById(R.id.left_dialog);
                                textView = view.findViewById(R.id.left_msg);
                            }
                            linearLayout.setVisibility(View.VISIBLE);
                            msgRecyclerView.setDefaultFocusHighlightEnabled(false);
                            final LinearLayout finalLinearLayout = linearLayout;
                            btn_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    msgList.remove(position);
                                    adapter = new MsgAdapter(msgList, ChatRoom.this);
                                    msgRecyclerView.setAdapter(adapter);
                                    finalLinearLayout.setVisibility(View.INVISIBLE);
                                }
                            });
                            btn_delete.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    msgList.remove(position);
                                    adapter = new MsgAdapter(msgList, ChatRoom.this);
                                    msgRecyclerView.setAdapter(adapter);
                                    finalLinearLayout.setVisibility(View.INVISIBLE);
                                    return true;
                                }
                            });
                            final TextView finalTextView = textView;
                            btn_high.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (finalTextView.getCurrentTextColor() == Color.RED) {
                                        finalTextView.setTextColor(Color.DKGRAY);
                                        finalLinearLayout.setVisibility(View.INVISIBLE);
                                    } else {
                                        finalTextView.setTextColor(Color.RED);
                                        finalLinearLayout.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            btn_high.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    if (finalTextView.getCurrentTextColor() == Color.RED) {
                                        finalTextView.setTextColor(Color.DKGRAY);
                                        finalLinearLayout.setVisibility(View.INVISIBLE);
                                    } else {
                                        finalTextView.setTextColor(Color.RED);
                                        finalLinearLayout.setVisibility(View.INVISIBLE);
                                    }
                                    return true;
                                }
                            });
                        }
                    }
                })
        );
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        back = findViewById(R.id.back);
        btn_caidan = findViewById(R.id.the_menu);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                moveTaskToBack(true);
//                overridePendingTransition(R.anim.left_in, 0);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.SENT, Now_User.User_Name);
                    msg.qq = Now_User.user_qq;
                    msgList.add(msg);
                    //插入，并滑到总个数-1的位置
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText(" ");
                    /**
                     * 下面该做的应该是把发送的数据存储到数据库----------------------
                     */
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
                    final Chat chat = new Chat();
                    chat.content = content;
                    chat.time = format.format(date);
                    chat.username = Now_User.User_Name;
                    chat.qq = Now_User.user_qq;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DbHelper.Insert_Chat(chat);
                        }
                    }).start();

                    /**
                     * 存储完毕---------------------------------------------------
                     */
                }
            }
        });

        /**
         * 菜单按钮--开始
         */
        btn_caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ChatRoom.this, btn_caidan);
                popup.getMenuInflater().inflate(R.menu.menu_pop, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                        builder.setTitle("删除记录");
                        builder.setMessage("确认删除所有记录");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                msgList = new ArrayList<>();
                                adapter = new MsgAdapter(msgList, ChatRoom.this);
                                msgRecyclerView.setAdapter(adapter);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                });
                popup.show();
            }
        });
        /**
         * 菜单按钮--结束
         */

        Button btn_voice = findViewById(R.id.btn_voice);
        Button btn_emoji = findViewById(R.id.btn_emoji);
        final ImageView imageView_back1 = findViewById(R.id.chat_show_voice);
        final ImageView imageView_back2 = findViewById(R.id.chat_show_emoji);
        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView_back1.getVisibility() == View.GONE) {
                    if (imageView_back1.getVisibility() == View.GONE && imageView_back2.getVisibility() == View.VISIBLE) {
                        imageView_back2.setVisibility(View.GONE);
                        imageView_back1.setImageResource(R.drawable.voice_btn);
                        imageView_back1.setVisibility(View.VISIBLE);
                    } else {
                        imageView_back1.setImageResource(R.drawable.voice_btn);
                        imageView_back2.setVisibility(View.GONE);
                        HiddenAnimUtils.newInstance(ChatRoom.this, imageView_back1, new View(ChatRoom.this), 240).toggle();
                    }
                } else if (imageView_back1.getVisibility() == View.VISIBLE) {
                    imageView_back2.setVisibility(View.GONE);
                    HiddenAnimUtils.newInstance(ChatRoom.this, imageView_back1, new View(ChatRoom.this), 240).toggle();
                }
            }
        });
        btn_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView_back2.getVisibility() == View.GONE) {
                    if (imageView_back2.getVisibility() == View.GONE && imageView_back1.getVisibility() == View.VISIBLE) {
                        imageView_back1.setVisibility(View.GONE);
                        imageView_back2.setImageResource(R.drawable.emoji_btn);
                        imageView_back2.setVisibility(View.VISIBLE);
                    } else {
                        imageView_back2.setImageResource(R.drawable.emoji_btn);
                        imageView_back1.setVisibility(View.GONE);
                        HiddenAnimUtils.newInstance(ChatRoom.this, imageView_back2, new View(ChatRoom.this), 240).toggle();
                    }
                } else if (imageView_back2.getVisibility() == View.VISIBLE) {
                    imageView_back1.setVisibility(View.GONE);
                    HiddenAnimUtils.newInstance(ChatRoom.this, imageView_back2, new View(ChatRoom.this), 240).toggle();
                }

            }
        });


        for (int i = 0; i < Chat_List.chat_list.size(); i++) {
            final Msg message = new Msg();
            Chat chat = Chat_List.chat_list.get(i);
            System.out.println("信息信息" + chat);
            message.user_name = chat.username;
            message.content = chat.content;
            if (Now_User.User_Name.equals(chat.username)) {
                message.type = Msg.SENT;
            } else
                message.type = Msg.RECEIVE;
            message.time = chat.time;
            message.qq = chat.qq;
            //给message的数据
            msgList.add(message);
            adapter.notifyItemInserted(msgList.size() - 1);
            msgRecyclerView.scrollToPosition(msgList.size() - 1);
        }
    }
}