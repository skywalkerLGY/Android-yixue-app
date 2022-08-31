package com.example.curriculum_design.Lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockScreenBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = "LockScreenBR";
    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent != null) {
            String action = intent.getAction();
            Log.i(TAG, "LockScreenBroadcastReceiver => receiver => [action : " + action + "]");

            if (action != null && action.equals(Intent.ACTION_SCREEN_ON)) {
                //开启
                try {
                    Intent startIntent = new Intent(context, LockScreenActivity.class);
                    startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                    );
                    context.startActivity(startIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
