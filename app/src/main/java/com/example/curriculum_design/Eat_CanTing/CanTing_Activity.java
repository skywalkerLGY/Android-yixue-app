package com.example.curriculum_design.Eat_CanTing;

import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.R;
import com.example.curriculum_design.Word_recite.Word_Recite;
import com.githang.statusbar.StatusBarCompat;

public class CanTing_Activity extends AppCompatActivity{


    DianCanFragment dianCanFragment;
    DingDanFragment dingDanFragment;
    static RadioButton diancanbtn, dingdanbtn;
    RadioGroup rd_group_bottom;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_can_ting);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        Save_DingDan save_dingDan=null;
        save_dingDan= (Save_DingDan) ObjectSaveUtils.getObject(CanTing_Activity.this, "save_dingDan");
        if(save_dingDan!=null){
            Static_DingDan.time=save_dingDan.time;
            Static_DingDan.status=save_dingDan.status;
            Static_DingDan.eat_sum=save_dingDan.eat_sum;
            Static_DingDan.eat_money=save_dingDan.eat_money;
            Static_DingDan.eat_name=save_dingDan.eat_name;
            Static_DingDan.eat_image=save_dingDan.eat_image;
            Static_DingDan.hp=save_dingDan.hp;
        }
        fragmentManager = getSupportFragmentManager();
        InitBTN();
        Set_Check_Click();
        diancanbtn.setChecked(true);
    }
    void InitBTN() {
        diancanbtn = findViewById(R.id.diancan_btn);
        dingdanbtn = findViewById(R.id.dingdan_btn);
        rd_group_bottom = findViewById(R.id.rd_group_bottom);
    }
    void Hide_All(FragmentTransaction fragmentTransaction) {
        if (dianCanFragment != null)
            fragmentTransaction.hide(dianCanFragment);
        if (dingDanFragment != null)
            fragmentTransaction.hide(dingDanFragment);
    }
    void Set_Check_Click() {
        rd_group_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Hide_All(fragmentTransaction);
                switch (i) {
                    case R.id.diancan_btn:
                        dianCanFragment = new DianCanFragment();
                        fragmentTransaction.add(R.id.framelayout_ct, dianCanFragment);
                        break;
                    case R.id.dingdan_btn:
                        dingDanFragment = new DingDanFragment();
                        fragmentTransaction.add(R.id.framelayout_ct, dingDanFragment);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Save_DingDan save_dingDan=new Save_DingDan();
        for(int i=0;i<Static_DingDan.time.size();i++){
            save_dingDan.time.add(Static_DingDan.time.get(i));
            save_dingDan.status.add(Static_DingDan.status.get(i));
            save_dingDan.eat_sum.add(Static_DingDan.eat_sum.get(i));
            save_dingDan.eat_money.add(Static_DingDan.eat_money.get(i));
            save_dingDan.eat_name.add(Static_DingDan.eat_name.get(i));
            save_dingDan.eat_image.add(Static_DingDan.eat_image.get(i));
            save_dingDan.hp.add(Static_DingDan.hp.get(i));
        }
        ObjectSaveUtils.saveObject(CanTing_Activity.this, "save_dingDan", save_dingDan);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}