package com.pt.myeeg.services.android;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.fragments.recording.RecordingFragment;
import com.pt.myeeg.services.database.InfoHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jorge Zepeda Tinoco on 04/09/17.
 * jorzet.94@gmail.com
 */

public class CountDown extends Service {
    public static final String CURRENT_STRING_TIME = "current_string_time";
    public static final String CURRENT_LONG_TIME = "current_long_time";
    public static final String ZERO_TIME_VALUE = "00:00:00";
    public static final String COUNT_DOWN_FINISHED = "count_down_finished";
    private final static String TAG = "BroadcastService";
    public static final String COUNTDOWN_BR = "com.eeg.pt1_v1.services.android";
    Intent bi = new Intent(COUNTDOWN_BR);
    private long totalTimeCountInMilliSeconds = 60000; // mili-seconds
    CountDownTimer mCountDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyTAG", "Starting timer...");
        InfoHandler ih = new InfoHandler(getApplication());
        ih.saveExtraFromActivity(RecordingFragment.RECORDING, "true");

        String[] schedulePositionValues = ih.getExtraStored(Palabras.SCHEDULE_POSITION).split("-");
        ih.saveExtraFromActivity(Palabras.SCHEDULE_POSITION, schedulePositionValues[0]+"-"+"true");

        Cita c = ih.getPatientSchedule(Integer.parseInt(schedulePositionValues[0]));
        String duracion = c.getDuracion();
        totalTimeCountInMilliSeconds = StringToHMSInt(duracion);

        mCountDownTimer = new CountDownTimer(totalTimeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("MyTAG","time: "+millisUntilFinished+" time2: "+millisUntilFinished);

                bi.putExtra(CURRENT_STRING_TIME, hmsTimeFormatter(millisUntilFinished));
                bi.putExtra(CURRENT_LONG_TIME, millisUntilFinished);
                bi.putExtra(COUNT_DOWN_FINISHED, false);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                bi.putExtra(COUNT_DOWN_FINISHED, true);
                bi.putExtra(CURRENT_STRING_TIME,ZERO_TIME_VALUE);

                InfoHandler ih = new InfoHandler(getApplication());

                ih.saveExtraFromActivity(RecordingFragment.RECORDING, "false");
                ih.saveExtraFromActivity(RecordingFragment.IN_RECORDING, null);

                sendBroadcast(bi);
            }
        }.start();

    }

    @Override
    public void onDestroy() {

        mCountDownTimer.cancel();
        Log.i("MyTAG", "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String hmsTimeFormatter(long milliSeconds) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
    }

    private long StringToHMSInt(String time){
        String[] units = time.split(":"); //will break the string up into an array
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]); //first element
        int seconds = Integer.parseInt(units[2]); //second element
        return (3600*hours + 60*minutes + seconds)*1000; //add up our values
    }
}
