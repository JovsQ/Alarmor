package com.example.user.myalarm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myalarm.services.OnScreenOffReceiver;

import java.util.Calendar;

import static com.example.user.myalarm.DeviceManagerSingleton.LOCK_DEVICE;

/**
 * Created by USER on 7/11/2017.
 */

public class AlarmManagerActivity extends AppCompatActivity implements LoginTaskListener{
    private AlarmManagerBroadcastReceiver alarm;

    private PowerManager.WakeLock wakeLock;
    private OnScreenOffReceiver onScreenOffReceiver;
    private LoginTaskListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFlags();
        setContentView(R.layout.activity_alarm_manager);
        alarm = new AlarmManagerBroadcastReceiver();
        setDeviceManager();
//        checkCurfew();
    }


    private void setDeviceManager() {
        DeviceManagerSingleton.getInstance().getAlarmDeviceManager(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("relaunch", "has focus: " + hasFocus);
        if (!hasFocus) {
//            Log.d("relaunch", "is device locked: " + hasFocus);
//            if (PrefsHelper.getBoolean(this, LOCK_DEVICE)) {
//                alarm.relaunch(this);
//            }
//            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

//            sendBroadcast(new Intent(Intent.ACTION_LOCKED_BOOT_COMPLETED));

//            startActivity(new Intent(this, AlarmManagerActivity.class)
//                    .putExtra("CURFEW", true)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

//    private void checkCurfew() {
//        if (getIntent().hasExtra("CURFEW")) {
//            final Window win = getWindow();
//            win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
//                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
//                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
//                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
//                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private ComponentName deviceAdminComponentName;
    static final int ACTIVATION_REQUEST = 1;

    public boolean accountExists(){
        AccountManager accountManager = AccountManager.get(this);
        Account[] wsAccounts = accountManager.getAccountsByType("com.example.user.myalarm");

        if (wsAccounts.length != 0) { return true; }
        return false;
    }

    public void startRepeatingTimer(View view) {
//        Log.d("alarmor", "started alarm");
//        Context context = this.getApplicationContext();
//        if(alarm != null){
//            Log.d("alarmor", "start alarm not null");
//            alarm.SetAlarm(context);
//        }else{
//            Log.d("alarmor", "start alarm null");
//            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
//        }
//        startLockTask();

//        if (!DeviceManagerSingleton.getInstance().getAlarmDeviceManager(this).isActiveAdmin()) {
//            startDeviceManager();
//        } else {
//            lockDevice();
//        }

//        lockDevice();

//        startDeviceManager();

        finish();
    }

    private void relaunch() {
        startActivity(new Intent(this, AlarmManagerActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void getFlags() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("dvm", "new intent");
        super.onNewIntent(intent);
        getFlags();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("dvm", "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("dvm", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("dvm", "destroy");
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("alarmor", "on activity result");
//        switch (requestCode) {
//            case ACTIVATION_REQUEST:
//                if (resultCode == Activity.RESULT_OK) {
//                    Log.d("alarmor", "administration enabled!");
//                    Log.i("MainActivity", "Administration enabled!");
//
//                    DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//                    String mPackageName = this.getPackageName();
//                    if (myDevicePolicyManager.isDeviceOwnerApp(mPackageName)) {
//                    Log.d("alarmor", "is device owner app");
//                    myDevicePolicyManager.setLockTaskPackages(deviceAdminComponentName, new String[]{mPackageName});
//                } else {
//                    Log.d("alarmor", "not device owner");
//
//                }
//
//                    if (myDevicePolicyManager.isLockTaskPermitted(mPackageName)) {
//                        Log.d("alarmor", "lock permitted");
//                        startLockTask();
//                    } else {
//                        Log.d("alarmor", "lock not permitted");
//                    }
//
//                } else {
//                    Log.d("alarmor", "administration enable failed");
//                    Log.i("MainActivity", "Administration enable FAILED!");
//                }
//
//                return;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    public void cancelRepeatingTimer(View view){
//        Log.d("alarmor", "cancel alarm");
//        Context context = this.getApplicationContext();
//        if(alarm != null){
//            Log.d("alarmor", "cancel alarm not null");
//            alarm.CancelAlarm(context);
//        }else{
//            Log.d("alarmor", "cancel alarm null");
//            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
//        }

//        if (isAppInLockTaskMode()) {
//            stopLockTask();
//        }
//        unlockDevice();

        alarm.cancelAlarm(this);
    }

    private AlarmDeviceManager deviceManager;

    private void startDeviceManager() {
        deviceManager = new AlarmDeviceManager(this);
        deviceManager.showDeviceAdminDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AlarmDeviceManager.REQUEST_CODE_ENABLE_ADMIN:
                if(resultCode == RESULT_OK){

                    Log.d("dvm", "result ok");
                } else {
                }
                break;
            case 101:
                Log.d("dvm", "result: " + (resultCode == RESULT_OK));
                break;
        }
    }

    private void lockDevice() {
        Log.d("dvm", "lock");
        PrefsHelper.setBoolean(this, LOCK_DEVICE, true);
        DeviceManagerSingleton.getInstance().lockScreen(this, "1234");
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 2);
        relaunch();
    }



    private void unlockDevice() {
        Log.d("dvm", "unlock");
        PrefsHelper.setBoolean(this, LOCK_DEVICE, false);
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 15000);
        DeviceManagerSingleton.getInstance().unLockScreen(this, "1234");
//        if (deviceManager != null && deviceManager.isActiveAdmin()) {
//            Log.d("dvm", "active admin unlock");
//            deviceManager.unlockScreen("1234");
//        }
    }

    private void checkOwnership() {
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        String mPackageName = this.getPackageName();
        Log.d("dvm", "is device owner app: " + myDevicePolicyManager.isDeviceOwnerApp(mPackageName));
    }

    public boolean isAppInLockTaskMode() {
        ActivityManager activityManager;

        activityManager = (ActivityManager)
                this.getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For SDK version 23 and above.
            return activityManager.getLockTaskModeState()
                    != ActivityManager.LOCK_TASK_MODE_NONE;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // When SDK version >= 21. This API is deprecated in 23.
            return activityManager.isInLockTaskMode();
        }

        return false;
    }

    public void onetimeTimer(View view){
//        Context context = this.getApplicationContext();
//        if(alarm != null){
//            Calendar now = Calendar.getInstance();
//            Log.d("alarmor", "Calendar: " + now.getTime());
//            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 5);
//            Log.d("alarmor", "Calendar: " + now.getTime());
//            alarm.setOnetimeTimer(context, now, true);
//        }else{
//            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
//        }

//        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);

        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 3000000);
        alarm.checkActivities(this);
        Intent intent = new Intent(Intent.ACTION_DIAL, null);
        Log.d("foreground", "start call for result");
        startActivityForResult(intent, 101);
//        startActivityForResult(intent, 101);

    }

    private UrlObserver observer = new UrlObserver(new Handler());

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_widget_alarm_manager, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onSuccess(Object result) {

    }

    @Override
    public void onFailure(Exception result) {

    }
}
