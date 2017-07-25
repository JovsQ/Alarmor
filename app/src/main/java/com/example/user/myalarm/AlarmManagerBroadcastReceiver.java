package com.example.user.myalarm;

import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by USER on 7/11/2017.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    final public static String REPEATING = "repeating";
    final public static String RELAUNCH = "relaunch";
    final public static String CHECK_TASK = "check_task";
    final public static String CALL_ACTIVE = "call_active";

    final private static int CHECK_ACTIVITIES_REQUEST = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();
        Log.d("telepun", "receive");
        //You can do the processing here update the widget/remote views.

        if (intent.hasExtra(RELAUNCH)) {
//            Log.d("dvm", "BR relaunch received");
//            context.startActivity(new Intent(context, AlarmManagerActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (intent.hasExtra(CHECK_TASK)) {
            checkActivities(context);
//            Log.d("foreground", "action: " + intent.getAction());
//
//            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> taskList = activityManager.getRunningTasks(5);
//            Log.d("foreground", "task list size: " + taskList.size());
//            for (ActivityManager.RunningTaskInfo runningTaskInfo: taskList) {
//                Log.d("foreground", "running task base: " + runningTaskInfo.baseActivity);
//            }


            String currentApp = "NULL";
            Log.d("foreground", "os version: " + Build.VERSION.SDK_INT);
            Log.d("foreground", "lollipop mr1: " + Build.VERSION_CODES.LOLLIPOP_MR1);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                Log.d("foreground", "version qualified");
                UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//                long time = System.currentTimeMillis();
//
//                Log.d("foreground", "app list size: " + appList.size());


                Calendar calendar = Calendar.getInstance();
                long endTime = calendar.getTimeInMillis();
                calendar.add(Calendar.DATE, -1);
                long startTime = calendar.getTimeInMillis();
                List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
                Log.d("foreground", "app list size: " + appList.size());
//                for (UsageStats u : appList) {
//                    Log.d("foreground", "current app: " + u.getPackageName());
//                }

                if (appList != null && appList.size() > 0) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : appList) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {

                        currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                        Log.d("foreground", "current app: " + currentApp);
                    }
                }
            } else {
                ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
                currentApp = tasks.get(0).processName;
            }
            Log.d("foreground", "current app: " + getAppNameFromPackage(currentApp, context));
        }
//        Log.d("alarmor", "alarm received: " + (!intent.hasExtra(REPEATING) ? "ONE TIME" : "REPEATING") + " " +msgStr.append(formatter.format(new Date())));

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

    private String getAppNameFromPackage(String packageName, Context context) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> pkgAppsList = context.getPackageManager()
                .queryIntentActivities(mainIntent, 0);

        for (ResolveInfo app : pkgAppsList) {
            if (app.activityInfo.packageName.equals(packageName)) {
                return app.activityInfo.loadLabel(context.getPackageManager()).toString();
            }
        }
        return packageName;
    }


    PhoneStateListener listener = new PhoneStateListener(){
        String savedNumber;
        public void setOutgoingNumber(String number){
            savedNumber = number;
        }
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub

            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if(incomingNumber==null)
                    {
                        //outgoing call
                    }
                    else
                    {
                        //incoming call
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:

                    if(incomingNumber==null)
                    {
                        //outgoing call
                    }
                    else
                    {
                        //incoming call
                    }
                    break;
            }
        }

    };















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

    public void cancelAlarm(Context context)
    {
        Log.d("alarmor", "BR cancel alarm");
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, CHECK_ACTIVITIES_REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
    }

    public void setOnetimeTimer(Context context, Calendar calendar, boolean start){
        Log.d("alarmor", "BR start one time alarm");
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, start);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    public void relaunch(Context context){
        Log.d("relaunch", "BR relaunch");
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(RELAUNCH, true);
        Calendar relaunch = Calendar.getInstance();
        relaunch.add(Calendar.SECOND, 2);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, relaunch.getTimeInMillis(), pi);
    }

//    public void checkActivities(Context context)  {
//        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
//        intent.putExtra(CHECK_TASK, true);
//        PendingIntent pi = PendingIntent.getBroadcast(context, CHECK_ACTIVITIES_REQUEST, intent, 0);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 , pi);
//    }

    public void checkActivities(Context context)  {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(CHECK_TASK, true);
        Calendar relaunch = Calendar.getInstance();
        relaunch.add(Calendar.SECOND, 3);
        PendingIntent pi = PendingIntent.getBroadcast(context, CHECK_ACTIVITIES_REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 , pi);
        am.set(AlarmManager.RTC_WAKEUP, relaunch.getTimeInMillis(), pi);
    }
}
