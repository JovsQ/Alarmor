package com.example.user.myalarm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by USER on 7/19/2017.
 */

public class DeviceManagerSingleton {

    private AlarmDeviceManager alarmDeviceManager;
    private Context context;

    private static DeviceManagerSingleton DEVICE_MANAGER = new DeviceManagerSingleton();

    private DeviceManagerSingleton() {}

    public static DeviceManagerSingleton getInstance() {
        return DEVICE_MANAGER;
    }

    public AlarmDeviceManager getAlarmDeviceManager(final Context context) {
        if (alarmDeviceManager == null) {
            alarmDeviceManager = new AlarmDeviceManager(context);
            alarmDeviceManager.showDeviceAdminDialog((AppCompatActivity)context);
        }
        return alarmDeviceManager;
    }

    public void lockScreen(final String password) {
        if (alarmDeviceManager != null) {
            alarmDeviceManager.lockScreen(password);
        }
    }

    public void unLockScreen(final String password) {
        if (alarmDeviceManager !=null) {
            alarmDeviceManager.unlockScreen(password);
        }
    }
}
