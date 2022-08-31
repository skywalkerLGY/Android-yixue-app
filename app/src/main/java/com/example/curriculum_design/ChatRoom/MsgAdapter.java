package com.example.curriculum_design.ChatRoom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.curriculum_design.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;
    static Context context = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ViewHolder通常出现在适配器里，为的是listview滚动的时候快速设置值，而不必每次都重新创建很多对象，从而提升性能。
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        //LayoutInflat.from()从一个Context中，获得一个布局填充器，这样你就可以使用这个填充器来把xml布局文件转为View对象了。
        //LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);这样的方法来加载布局msg_item.xml
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.RECEIVE) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.content);
            holder.leftname.setText(msg.user_name);
            holder.receive_time.setText(msg.time);
            set_img(holder.receive_img, msg.qq);
        } else if (msg.getType() == Msg.SENT) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.content);
            holder.rightname.setText(msg.user_name);
            holder.send_time.setText(msg.time);
            set_img(holder.send_img, msg.qq);
        }
    }

    void set_img(ImageView imageView, String qq) {
//        if (position % 5 == 0) {
//            imageView.setBackgroundResource(R.drawable.chat1);
//        }
//        if (position % 5 == 1) {
//            imageView.setBackgroundResource(R.drawable.chat2);
//        }
//        if (position % 5 == 2) {
//            imageView.setBackgroundResource(R.drawable.chat3);
//        }
//        if (position % 5 == 3) {
//            imageView.setBackgroundResource(R.drawable.chat4);
//        }
//        if (position % 5 == 4) {
//            imageView.setBackgroundResource(R.drawable.chat5);
//        }
        Glide.with(context).load("https://q1.qlogo.cn/g?b=qq&nk="+qq+"&s=140").into(imageView);
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        ImageView receive_img, send_img;
        TextView leftMsg;
        TextView rightMsg;
        TextView leftname;
        TextView rightname;
        TextView receive_time;
        TextView send_time;

        public ViewHolder(@NonNull View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            leftMsg = view.findViewById(R.id.left_msg);
            rightMsg = view.findViewById(R.id.right_msg);
            leftname = view.findViewById(R.id.receive_name);
            rightname = view.findViewById(R.id.send_name);
            receive_time = view.findViewById(R.id.receive_time);
            send_time = view.findViewById(R.id.send_time);
            receive_img = view.findViewById(R.id.receive_img);
            send_img = view.findViewById(R.id.send_img);
        }
    }

    public MsgAdapter(List<Msg> msgList, Context mycontext) {
        mMsgList = msgList;
        context=mycontext;
    }
}
