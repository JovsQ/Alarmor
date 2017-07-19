package com.example.user.myalarm.receiver;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.user.myalarm.AlarmDeviceManager;

/**
 * Created by USER on 7/17/2017.
 */

public class AlarmDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        AlarmDeviceManager am = new AlarmDeviceManager(context);

//        Log.d() todo
        Log.d("dvm", "password success");
        if (am.isActiveAdmin()) {
            am.doResetPassword("");
//            am.unlockScreen();
        }

    }



    public boolean accountExists(Context context){
        AccountManager accountManager = AccountManager.get(context);
        Account[] wsAccounts = accountManager.getAccountsByType("com.example.user.myalarm");

        if (wsAccounts.length != 0) { return true; }
        return false;
    }
}
