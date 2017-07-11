package com.example.user.myalarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by USER on 7/11/2017.
 */

public class AlarmManagerActivity extends AppCompatActivity {
    private AlarmManagerBroadcastReceiver alarm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);
        alarm = new AlarmManagerBroadcastReceiver();

        checkCurfew();

    }

    private void checkCurfew() {
        if (getIntent().hasExtra("CURFEW")) {
            final Window win = getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void startRepeatingTimer(View view) {
        Log.d("alarmor", "started alarm");
        Context context = this.getApplicationContext();
        if(alarm != null){
            Log.d("alarmor", "start alarm not null");
            alarm.SetAlarm(context);
        }else{
            Log.d("alarmor", "start alarm null");
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelRepeatingTimer(View view){
        Log.d("alarmor", "cancel alarm");
        Context context = this.getApplicationContext();
        if(alarm != null){
            Log.d("alarmor", "cancel alarm not null");
            alarm.CancelAlarm(context);
        }else{
            Log.d("alarmor", "cancel alarm null");
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onetimeTimer(View view){
        Context context = this.getApplicationContext();
        if(alarm != null){
            Calendar now = Calendar.getInstance();
            Log.d("alarmor", "Calendar: " + now.getTime());
            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 1);
            Log.d("alarmor", "Calendar: " + now.getTime());
            alarm.setOnetimeTimer(context, now, true);
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_widget_alarm_manager, menu);
        return true;
    }
}
