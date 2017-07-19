package com.example.user.myalarm;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by USER on 7/11/2017.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    final public static String REPEATING = "repeating";
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        //You can do the processing here update the widget/remote views.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        Format formatter = new SimpleDateFormat("hh:mm:ss a");

        Log.d("alarmor", "alarm received: " + (!intent.hasExtra(REPEATING) ? "ONE TIME" : "REPEATING") + " " +msgStr.append(formatter.format(new Date())));

//        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
//            msgStr.append("One time Timer : ");
//        }
//        Log.d("alarmor", "has intent: " + intent.hasExtra(ONE_TIME));
//        if (intent.hasExtra(ONE_TIME) && intent.getBooleanExtra(ONE_TIME, false)) {
//            Calendar now = Calendar.getInstance();
//            Log.d("alarmor", "Calendar: " + now.getTime());
//            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 1);
//            Log.d("alarmor", "Calendar: " + now.getTime());
//            setOnetimeTimer(context, now, false);
//        }
//
//        msgStr.append(formatter.format(new Date()));
//        Log.d("alarmor", "BR acquired alarm: " + msgStr);
//        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
//
//        context.startActivity(new Intent(context, AlarmManagerActivity.class)
//                .putExtra("CURFEW", true)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        //Release the lock
        wl.release();

    }
    public void SetAlarm(Context context)
    {
        Log.d("alarmor", "BR set repeating alarm");
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra(REPEATING, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //After after 30 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 , pi);
    }

    public void CancelAlarm(Context context)
    {
        Log.d("alarmor", "BR cancel alarm");
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeTimer(Context context, Calendar calendar, boolean start){
        Log.d("alarmor", "BR start one time alarm");
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, start);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }
}
