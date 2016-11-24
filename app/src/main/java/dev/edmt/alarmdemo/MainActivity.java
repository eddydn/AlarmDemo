package dev.edmt.alarmdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    RadioButton rdiNotification,rdiToast;
    Button btnOneTime,btnRepeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdiNotification=(RadioButton)findViewById(R.id.rdiNotification);
        rdiToast = (RadioButton)findViewById(R.id.rdiToast);
        btnOneTime = (Button)findViewById(R.id.btnOneTime);
        btnRepeating = (Button)findViewById(R.id.btnRepeating);

        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdiNotification.isChecked())
                    startAlarm(true, false);
                else
                    startAlarm(false, false);
            }
        });

        btnRepeating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdiNotification.isChecked())
                    startAlarm(true,true);
                else
                    startAlarm(false,true);
            }
        });


    }

    private void startAlarm(boolean isNotification, boolean isRepeat) {
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        if(!isNotification)
        {
            myIntent = new Intent(MainActivity.this,AlarmToastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
        }
        else{
            myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
        }

        if(!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+3000,60*1000,pendingIntent);
    }
}
