package com.example.user.myalarm.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.example.user.myalarm.DeviceManagerSingleton;
import com.example.user.myalarm.PrefsHelper;

/**
 * Created by USER on 7/17/2017.
 */

public class AlarmDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        Log.d("JOVS", "password removed");
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 15000);
        PrefsHelper.setBoolean(context, DeviceManagerSingleton.LOCK_DEVICE, false);
        DeviceManagerSingleton.getInstance().getAlarmDeviceManager(context).doResetPassword("");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        Log.d("JOVS", "password failed");
    }
}
