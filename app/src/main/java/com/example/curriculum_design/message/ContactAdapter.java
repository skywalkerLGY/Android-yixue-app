package com.example.curriculum_design.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.MessageFragment;
import com.example.curriculum_design.R;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    int flag = 0;
    static Context context;
    ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<Contact> mContacts;
    public static boolean flag_vis=false;
    String Select_UserPhoto_By_QQ(String qq){
        for(int i = 0; i< UserInfo_List.userInfo_List.size(); i++){
            if(qq.equals(UserInfo_List.userInfo_List.get(i).user_qq)){
                return UserInfo_List.userInfo_List.get(i).photo;
            }
        }
        return "123";
    }
    public ContactAdapter(ArrayList<Contact> contacts,Context mycontext) {
        this.mContacts = contacts;
        images.add(R.drawable.tv1);
        images.add(R.drawable.tv2);
        images.add(R.drawable.tv3);
        images.add(R.drawable.tv4);
        context=mycontext;
    }

    @Override
    public int getCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contact, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String name = mContacts.get(position).getName();
        final String pinyin = mContacts.get(position).getPinyin().substring(0, 1);
        if (position == 0) {
            holder.tvPinYin.setVisibility(View.VISIBLE);
        } else {
            //首字母是否与前面一个一致
            final String prePinyin = mContacts.get(position - 1).getPinyin().substring(0, 1);
            if (pinyin.equals(prePinyin)) {
                holder.tvPinYin.setVisibility(View.GONE);
            } else {
                holder.tvPinYin.setVisibility(View.VISIBLE);
                flag++;
                if (flag >= 4) {
                    flag = 0;
                }
            }
        }
        String s = name.substring(0, 1);
//        holder.tv_image.setBackgroundResource(images.get(position%4));
//        holder.tv_image.setText(s);
        Glide.with(context).load(Select_UserPhoto_By_QQ(mContacts.get(position).qq)).into(holder.tv_image);
        holder.tvName.setText(name);
        holder.tvPinYin.setText(pinyin);
        if (flag_vis == true) {
            boolean flag_show=false;
            for(int i=0;i<MessageFragment.PB_list.size();i++){
                if(MessageFragment.PB_list.get(i).equals(name)){
                    flag_show=true;
                }
            }
            if(flag_show==true)
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setChecked(false);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    private static class ViewHolder {
        private ImageView tv_image;
        private TextView tvName;
        private TextView tvPinYin;
        private CheckBox checkBox;
        ViewHolder(View itemView) {
            tv_image = itemView.findViewById(R.id.tv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPinYin = itemView.findViewById(R.id.tv_pinyin);
            checkBox=itemView.findViewById(R.id.check_to_delete);
        }
    }
}
