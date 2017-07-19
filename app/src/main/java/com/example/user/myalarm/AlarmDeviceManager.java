package com.example.user.myalarm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.user.myalarm.receiver.AlarmDeviceAdminReceiver;

/**
 * Created by USER on 7/18/2017.
 */

public class AlarmDeviceManager {

    public static final int REQUEST_CODE_ENABLE_ADMIN = 1;

    private DevicePolicyManager aDevicePolicyManager;
    private Context context;
    private ComponentName aDeviceAdmin;

    public AlarmDeviceManager(Context context) {
        this.context = context;
        setupDevicePolicyManager();
    }

    public boolean isActiveAdmin() {
        return aDevicePolicyManager.isAdminActive(aDeviceAdmin);
    }

    private void setupDevicePolicyManager() {
        aDevicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        aDeviceAdmin = new ComponentName(context, AlarmDeviceAdminReceiver.class);
    }

    public void showDeviceAdminDialog(final Activity activity) {
        if (isActiveAdmin()) {
            removeActiveAdmin();
        }

        String description = "You need to Activate Device Admin to Access Full Curfew Features.";

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, aDeviceAdmin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
        activity.startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
    }

    public void lockScreen(String pw){
        doResetPassword(pw);
        aDevicePolicyManager.lockNow();
    }



//    public void lockScreen(String password){
//        aDevicePolicyManager.setPasswordMinimumLength(aDeviceAdmin, 0);
//        aDevicePolicyManager.lockNow();
//        KeyguardManager keyguardManager = (KeyguardManager) context.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock keyguardLock = null;
//        keyguardLock = keyguardManager.newKeyguardLock(password);
//        doResetPassword(password);
//        keyguardLock.reenableKeyguard();
//
//    }

    public void unlockScreen(String password) {
        if (isScreenLocked()) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = null;
            keyguardLock = keyguardManager.newKeyguardLock(password);
            keyguardLock.disableKeyguard();
            doResetPassword("");
            keyguardLock.reenableKeyguard();
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) wsDevicePolicyManager.uninstallAllUserCaCerts(wsDeviceAdmin);
        }
    }

    public void removeActiveAdmin() {
        aDevicePolicyManager.removeActiveAdmin(aDeviceAdmin);
    }

    public boolean isScreenLocked() {
        KeyguardManager keyguard = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguard.inKeyguardRestrictedInputMode();
    }

    public void doResetPassword(String newPassword) {
        aDevicePolicyManager.resetPassword(newPassword, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
    }
}
