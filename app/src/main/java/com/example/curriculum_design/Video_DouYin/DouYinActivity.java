package com.example.curriculum_design.Video_DouYin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.Pyq_Info;
import com.example.curriculum_design.LXR_More;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DouYinActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private DouYinLayoutManager douYinLayoutManager;

    ArrayList<String> video_urls=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_douyin);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/12/21/15/BMjAyMDEyMjExNTUwMzNfMjA3NjY4Nzg0M180MTA3MjAzOTMyMl8yXzM=_b_Bf7e4f7b1090beb5e7d0cec2e67e9e3eb.mp4?tag=1-1609242506-xpcwebfeatured-0-kxx5tliuek-6d4aa3ef897f125c&clientCacheKey=3xq8b7fcu35scpu_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/09/18/BMjAyMDEwMDkxODE3MjhfMTg2Njc0NDczMF8zNzQ0MzI3Nzk2OF8xXzM=_b_B15207891ef8b734d59fd90deba7b6afb.mp4?tag=1-1609252709-xpcwebfeatured-0-xss6iaat72-d52a08a93c53c087&clientCacheKey=3xsqec6g2ti588u_b.mp4&tt=b&di=315cb645&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/23/16/BMjAyMDEwMjMxNjM5MzBfMjEwOTg2NTIxM18zODExNTA4NDE3Nl8xXzM=_b_B4a3e34ac3c5d55b807881114a812cb9a.mp4?tag=1-1609243260-xpcwebfeatured-0-xaey1mo4yg-9b4f4e614b5b3a04&clientCacheKey=3xss35wzv9zad4a_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/11/14/14/BMjAyMDExMTQxNDMyMDlfNDYyNzY0NjI0XzM5MjA2NjY5NjExXzFfMw==_b_Bb4c522b86c2b1237662fe93f44b97345.mp4?tag=1-1609243417-xpcwebfeatured-0-iiym0hifkh-c3a38a5ebbcfee47&clientCacheKey=3xvy4qsm4fwppiq_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/14/17/BMjAyMDEwMTQxNzIxMjdfMTk5MjkwNzIyMV8zNzY3ODI1OTM0N18yXzM=_b_B1099e920adfa36d133434e602b42d760.mp4?tag=1-1609243442-xpcwebfeatured-0-myecf1xyb6-24a429d5d4878b6d&clientCacheKey=3x83aipu8r8hi84_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/08/12/18/BMjAyMDA4MTIxODQ5MTBfOTE3NzkyMjA2XzM0MjAyMzE1MTI5XzBfMw==_b_B745e0f9a3159e35496524678d1509e18.mp4?tag=1-1609243596-xpcwebfeatured-0-kna16otccu-d60582773a9c8175&clientCacheKey=3xfvgsech2ukcw9_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/12/20/14/BMjAyMDEyMjAxNDE4MDVfMjEyNDc4MTU3NV80MTAxNTIwNTEyMl8wXzM=_b_B931c3eab5c716d7824cac474c0747116.mp4?tag=1-1609243623-xpcwebfeatured-0-qv8aipwmv2-f3c41d04f6a11b70&clientCacheKey=3xqt9asdkbn2kiq_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/06/05/20/BMjAyMDA2MDUyMDEzMzdfMTE2NTcyMjM0N18yOTkzNDM5Mzc0MV8yXzM=_b_B06230c7ed081a5010d8213c85a1458cb.mp4?tag=1-1609243695-xpcwebfeatured-0-yo1hk2vlwy-d0cf07a783122fae&clientCacheKey=3xxyyxq3s38iwj4_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/09/28/17/BMjAyMDA5MjgxNzQ0MTNfMTg1MDk2MzA2NV8zNjc0OTMxMTA1Nl8yXzM=_b_B4b8f1c02478aefb410f6253c99336adf.mp4?tag=1-1609243729-xpcwebfeatured-0-lrn5xzvqpw-5cf7cd7387450914&clientCacheKey=3xhdfe6hpcbxepa_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/11/11/17/BMjAyMDExMTExNzA1MDNfMTE3MjM2MTM4N18zOTA2NjkzNTc4Nl8yXzM=_b_B98597c6517af21cf63bc70cdc1e81ab9.mp4?tag=1-1609243780-xpcwebfeatured-0-4naqcfbbhr-e47a35a6a4e66e8a&clientCacheKey=3xiichszajvxf46_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/16/17/BMjAyMDEwMTYxNzQwNTZfMTg1MDk2MzA2NV8zNzc2ODk4NTU3N18yXzM=_b_B3b014f25e3d4b5924f0881c535f590f9.mp4?tag=1-1609243848-xpcwebfeatured-0-3w0jdazgye-1af09424d76cb522&clientCacheKey=3xfiyuewpk2pa9y_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/11/05/19/BMjAyMDExMDUxOTE4NDFfMTM4MDIwMjAwNl8zODc3MzYxNzgzMF8wXzM=_b_B9b18621571f27a29707615154675ca96.mp4?tag=1-1609243880-xpcwebfeatured-0-xgwfgd3j6z-186423e3b94d7e96&clientCacheKey=3xcqbww63njvcxc_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/12/22/17/BMjAyMDEyMjIxNzMxMjRfMjE4NzY1Nzk5XzQxMTIzMDU5MjAxXzFfMw==_b_Bd7f4f34e9fa0449179b2f4e4fe240e52.mp4?tag=1-1609243947-xpcwebfeatured-0-nj1zd90jb7-7212ee00cde129a8&clientCacheKey=3x39b2aqwz9cexk_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/09/11/20/BMjAyMDA5MTEyMDI3MzhfMTY4ODUwNzYzNV8zNTg2NDM1MzQ1OV8xXzM=_b_B13daa36ae636f29e0d62497ee2c7c1be.mp4?tag=1-1609243984-xpcwebfeatured-0-iydudfaddi-a1bf66c930a88bb5&clientCacheKey=3x9a6t7ri6phdds_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/09/18/BMjAyMDEwMDkxODM1MjdfMjA5NzM5Nzk1M18zNzQ0NDE2Mzc0MF8yXzM=_b_B04dd7adc95a8592a41b0953905c39bb2.mp4?tag=1-1609244051-xpcwebfeatured-0-dekrvo9fpo-baa7b9ec0464bde1&clientCacheKey=3x25ri3ax5d9a6e_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/11/02/17/BMjAyMDExMDIxNzMwNTVfMTY3MDMyNzk3MF8zODY0MDA0MTQwNF8yXzM=_b_B6b0d6cc8e3b026081f08f4aadda1f158.mp4?tag=1-1609244091-xpcwebfeatured-0-gesep13tot-832b27a3341f5f9b&clientCacheKey=3xmzntykjjktau9_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/12/18/15/BMjAyMDEyMTgxNTU5NDNfNjAxMzY3NDg2XzQwODk3MjkxNjQ0XzFfMw==_b_Bb495bb0278843ebe6971c0262d0b060a.mp4?tag=1-1609244142-xpcwebfeatured-0-xqjljrsvp8-f8cd3808f262e8ed&clientCacheKey=3x44kfyjunsb47q_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/12/20/15/BMjAyMDEyMjAxNTExNDFfMTI3MDI1NDY4MF80MTAxODQxMTkwOV8xXzM=_b_B4c9359ef16e9c333c036911467448b29.mp4?tag=1-1609244182-xpcwebfeatured-0-wjhbttjzrv-8fcb7250f968b4f4&clientCacheKey=3x2c8wrvdpxqkzy_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/08/24/21/BMjAyMDA4MjQyMTAxNDBfMTU4NTQwMTI4Nl8zNDkzNzY1MDI3OV8yXzM=_b_B31cc7988c497a511c1a798343c05936a.mp4?tag=1-1609244253-xpcwebfeatured-0-k2nnylqtb3-06b0d573b61a3189&clientCacheKey=3xjq9cyxt2b7itc_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/09/30/21/BMjAyMDA5MzAyMTI4MzNfMTQ4NjMwNzM4N18zNjg2MzIwNTQzN18wXzM=_b_B89b48ba316b5d9c6724640a77ddaf5ea.mp4?tag=1-1609244294-xpcwebfeatured-0-siyeeb6fvr-97d06e38c37e503f&clientCacheKey=3xtfvvv4wbbcdue_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/01/12/BMjAyMDEwMDExMjQ5MTFfMzExNzE4NDU2XzM2OTA0MjcyNTI1XzFfMw==_b_Ba1704a2327a4501c407e0ee217825810.mp4?tag=1-1609244346-xpcwebfeatured-0-nu4sirdcg3-7de397e57d064374&clientCacheKey=3xrgpiby326be3a_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/11/11/13/BMjAyMDExMTExMzIwMTFfMTMxMTgxNTM3XzM5MDU4NTI5MTg0XzBfMw==_b_B026689fdc80d17d635f48f1f6e8de0a3.mp4?tag=1-1609244416-xpcwebfeatured-0-emxyfnigi4-7314d46de2a4f422&clientCacheKey=3xtchjjhbmwhmwq_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/11/08/11/BMjAyMDExMDgxMTQ0MDdfMTY4Mzg1NTgxNF8zODkxNTg1MjUxNV8xXzM=_b_Bdfc796fa71d12b643165972f2cd255aa.mp4?tag=1-1609244504-xpcwebfeatured-0-lmyr5oausa-9857c571cb5dc39b&clientCacheKey=3xfvhhq7pys8vhu_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/09/28/18/BMjAyMDA5MjgxODIwMTFfNzk4NzU3ODcxXzM2NzUxMzQ4NzczXzBfMw==_b_B2ebc099723ab00b0cca31c25f8b85fa3.mp4?tag=1-1609244542-xpcwebfeatured-0-17bhz2ida4-156c7e11f1d6af4b&clientCacheKey=3xug29y5f7umzn9_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/09/11/16/BMjAyMDA5MTExNjAyNDNfMTI2ODc3NTU5NF8zNTg0NzI2NDAzOF8xXzM=_b_B854f86dbeb9fe1343e4603d7fe0cefb0.mp4?tag=1-1609244631-xpcwebfeatured-0-jlyd8aq8rx-3e748114b48599e6&clientCacheKey=3xih4x5zbeszu6k_b.mp4&tt=b&di=79ee3be1&bp=10004");
        video_urls.add("https://txmov2.a.yximgs.com/upic/2020/10/27/18/BMjAyMDEwMjcxODI4NTBfMTQ3MDUxMDQ5MV8zODMzODA3OTg5OV8yXzM=_b_B391f45c643404030f97888a5132ddec8.mp4?tag=1-1609244685-xpcwebfeatured-0-ujlaybml39-a3990bde3710c2ad&clientCacheKey=3xt3u6h99bm97k4_b.mp4&tt=b&di=79ee3be1&bp=10004");
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView_dy);
        douYinLayoutManager = new DouYinLayoutManager(this, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(douYinLayoutManager);
        recyclerView.setAdapter(new MyAdapter());

        douYinLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onPageRelease(boolean isNest, View position) {
                releaseVideo(position);
            }
            @Override
            public void onPageSelected(boolean isButten, View position) {
                int index = 0;
                if (isButten){
                    index = 0;
                }else {
                    index = 1;
                }
                playVideo(position);
            }
        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public MyAdapter(){
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_douyin_view_pager,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Uri uri = Uri.parse(video_urls.get(position));
//            holder.videoView.c
            int i=position%4+1;
            if(i==1){
                holder.photo.setImageResource(R.drawable.tv1);
            }
            if(i==2){
                holder.photo.setImageResource(R.drawable.tv2);
            }
            if(i==3){
                holder.photo.setImageResource(R.drawable.tv3);
            }
            if(i==4){
                holder.photo.setImageResource(R.drawable.tv4);
            }
            holder.videoView.setVideoURI(uri);
            holder.zhuanfa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog_dy(position);
                }
            });
            holder.pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showpl_dy();
                }
            });
            holder.videoView.requestFocus();
        }

        @Override
        public int getItemCount() {
            return video_urls.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView img_thumb;
            VideoView videoView;
            ImageView img_play;
            RelativeLayout rootView;
            ImageView double_heart;
            ImageView heart_donhua;
            ImageView zhuanfa;
            ImageView photo;
            ImageView pl;
            public ViewHolder(View itemView) {
                super(itemView);
                img_thumb = itemView.findViewById(R.id.img_thumb);
                videoView = itemView.findViewById(R.id.video_view);
                img_play = itemView.findViewById(R.id.img_play);
                rootView = itemView.findViewById(R.id.root_view);
                double_heart = itemView.findViewById(R.id.double_heart);
                heart_donhua = itemView.findViewById(R.id.heart_donhua);
                zhuanfa=itemView.findViewById(R.id.zhuanfa);
                photo=itemView.findViewById(R.id.photo);
                pl=itemView.findViewById(R.id.pinglun);
                rootView.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                    @Override
                    public void onDoubleClick() {
                        //处理双击事件
                        double_heart.setBackgroundResource(R.mipmap.heart_icon_red);
                        heart_donhua.setVisibility(View.VISIBLE);
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                for(int k=255;k>=0;k--){
                                    final int finalK = k;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            heart_donhua.getBackground().setAlpha(finalK);
                                        }
                                    });
                                    try {
                                        Thread.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        heart_donhua.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }).start();
                    }
                }));
                double_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double_heart.setBackgroundResource(R.mipmap.heart_icon);
                    }
                });
            }
        }
    }


    /**
     * 停止播放
     * @param itemView
     */
    private void releaseVideo(View  itemView){
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }

    /**
     * 开始播放
     * @param itemView
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo(View itemView) {
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final RelativeLayout rootView = itemView.findViewById(R.id.root_view);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });


        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                }else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
    }
    void showDialog_dy(final int i) {
        final Dialog dialog = new Dialog(DouYinActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_douying);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        RelativeLayout fason_btn =  window.findViewById(R.id.fason_douying);
        RelativeLayout cancel_btn =  window.findViewById(R.id.cancel_douying);
        fason_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Pyq_Info pyq_info=new Pyq_Info();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd日 HH:mm");
                Date date = new Date(System.currentTimeMillis());
                pyq_info.pyq_time=simpleDateFormat.format(date);
                pyq_info.pyq_photo="0";
                pyq_info.pyq_name= Now_User.User_Name;
                pyq_info.pyq_text="我看到了一条很好看的YAO视频,快戳下方链接去看一看吧\n"+video_urls.get(i);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        DbHelper.Insert_Pyq(pyq_info);
                    }
                }).start();
                Toast.makeText(DouYinActivity.this, "已发送", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    void showpl_dy() {
        final Dialog dialog = new Dialog(DouYinActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_douying_pl);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button cancel_btn =  window.findViewById(R.id.cancel_btn_pl);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}