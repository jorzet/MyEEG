package com.pt.myeeg.fragments.recording;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.android.CountDown;
import com.pt.myeeg.services.bluetoothservice.BluetoothService;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.activities.ContentScheduleActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jorge Zepeda Tinoco on 09/07/17.
 * jorzet.94@gmail.com
 */

public class RecordingFragment extends BaseContentFragment{
    public static final String SCHEDULE_ID = "schedule_id";
    public static final String FROM_RECORDING = "from_recording";
    public static final String TO_RECORDING = "to_recording";
    public static final String RECORDING = "recording_fragment";
    public static final String IN_RECORDING = "in_recording";
    public static final String CURRENT_TIME = "current_time";

    public static final int VIBRATION_TIME = 1000; // one second

    private TextView mPorcentageProgress;
    private ProgressBar mProgressBarCircle;
    private TextView mChronometerRecording;
    private TextView mTotalTimeRecording;
    private ImageView mStartRecording;
    //private ImageView mRestartRecording;

    private static Context context;

    private int[] mStartStopRecordingIcon = {
            R.drawable.ic_start_recording,
            R.drawable.ic_stop_recording
    };

    private String mTime;
    private long totalTimeCountInMilliSeconds; // mili-seconds
    private long everyTime = 1000; //mili-seconds
    private long timeCounter = 0;
    private CountDownTimer mCountDownTimer;
    private boolean isRecording = false;
    private boolean isNotificationAble = false;
    private boolean isCasting = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;
        View rootView = inflater.inflate(R.layout.record_fragment, container, false);

        context = getActivity();

        mPorcentageProgress = (TextView) rootView.findViewById(R.id.porcentage_progress);
        mProgressBarCircle = (ProgressBar) rootView.findViewById(R.id.progressbar_circle);
        mChronometerRecording = (TextView) rootView.findViewById(R.id.chronometer_recording);
        mTotalTimeRecording = (TextView) rootView.findViewById(R.id.total_time);
        mStartRecording = (ImageView) rootView.findViewById(R.id.start_recording);
        //mRestartRecording = (ImageView) rootView.findViewById(R.id.restart_recording);

        mStartRecording.setOnClickListener(mStartStopRecordingListener);
        //mRestartRecording.setOnClickListener(mRestartRecordingListener);

        InfoHandler myHandler = new InfoHandler(getContext());
        String[] values = myHandler.getExtraStored(Palabras.SCHEDULE_POSITION).split("-");
        Cita c = myHandler.getPatientSchedule(Integer.parseInt(values[0]));
        String duracion = c.getDuracion();
        totalTimeCountInMilliSeconds = StringToHMSInt(duracion);
        mTotalTimeRecording.setText(duracion);

        int recording = getActivity().getIntent().getIntExtra(RECORDING,0);

        /*if(Boolean.parseBoolean(new InfoHandler(getContext()).getExtraStored(RecordingFragment.RECORDING))){
            mStartRecording.setVisibility(View.GONE);
            mRestartRecording.setVisibility(View.GONE);
        }*/
        return rootView;
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (!intent.getBooleanExtra(CountDown.COUNT_DOWN_FINISHED, false)) {
                    mTime = intent.getStringExtra(CountDown.CURRENT_STRING_TIME);
                    mChronometerRecording.setText(mTime);
                    mProgressBarCircle.setProgress((int) ((intent.getLongExtra(CountDown.CURRENT_LONG_TIME, 0) * 100) / totalTimeCountInMilliSeconds));
                    mPorcentageProgress.setText((int) (100 - ((intent.getLongExtra(CountDown.CURRENT_LONG_TIME, 0) * 100) / totalTimeCountInMilliSeconds)) + "%");

                    if (isNotificationAble)
                        addNotification(false);

                    isCasting = true;

                    mStartRecording.setImageResource(mStartStopRecordingIcon[1]);

                } else if (intent.getBooleanExtra(CountDown.COUNT_DOWN_FINISHED, false)) {
                    mProgressBarCircle.setProgress(0);
                    mChronometerRecording.setText("00:00:00");
                    mPorcentageProgress.setText("100%");
                    //setProgressBarValues();
                    mStartRecording.setVisibility(View.GONE);
                    addNotification(true);
                    isCasting = false;
                }
            }
        }
    };

    @Override
    public void onStop() {
        try {
            //getActivity().unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        //getActivity().stopService(new Intent(getActivity(), CountDown.class));
        //if(isCasting)
        //    br.abortBroadcast();
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        final Activity activity = getActivity();

        if(activity!=null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    BroadcastReceiver br = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getExtras() != null) {
                                if (!intent.getBooleanExtra(CountDown.COUNT_DOWN_FINISHED, false)) {
                                    mTime = intent.getStringExtra(CountDown.CURRENT_STRING_TIME);
                                    mChronometerRecording.setText(mTime);
                                    mProgressBarCircle.setProgress((int) ((intent.getLongExtra(CountDown.CURRENT_LONG_TIME, 0) * 100) / totalTimeCountInMilliSeconds));
                                    mPorcentageProgress.setText((int) (100 - ((intent.getLongExtra(CountDown.CURRENT_LONG_TIME, 0) * 100) / totalTimeCountInMilliSeconds)) + "%");

                                    if (isNotificationAble)
                                        addNotification(false);
                                    isCasting = true;

                                    mStartRecording.setImageResource(mStartStopRecordingIcon[1]);

                                } else if (intent.getBooleanExtra(CountDown.COUNT_DOWN_FINISHED, false)) {
                                    mProgressBarCircle.setProgress(0);
                                    mChronometerRecording.setText("00:00:00");
                                    mPorcentageProgress.setText("100%");
                                    //setProgressBarValues();
                                    mStartRecording.setVisibility(View.GONE);
                                    addNotification(true);
                                    isCasting = false;
                                }
                            }
                        }
                    };
                    activity.registerReceiver(br, new IntentFilter(CountDown.COUNTDOWN_BR));
                }
            }, 100);

            isNotificationAble = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(br, new IntentFilter(CountDown.COUNTDOWN_BR));
        isNotificationAble = false;
        NotificationManager notificationManager = (NotificationManager) getContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        new InfoHandler(getContext()).saveExtraFromActivity(RecordingFragment.FROM_RECORDING, "false");
    }


    private void addNotification(boolean isFinished) {
        NotificationCompat.Builder builder;
        if(isFinished) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(RecordingFragment.VIBRATION_TIME);
            builder = new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.drawable.ic_chronometer_notification)
                    .setContentTitle("Grabación Finalizada")
                    .setContentText("Total de tiempo: ");
        } else

            builder = new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.ic_chronometer_notification)
                        .setContentTitle("Tiempo restante: "+mTime)
                        .setContentText("Total de tiempo: ");

        Intent notificationIntent;

        notificationIntent = new Intent(getContext(), ContentScheduleActivity.class);

        notificationIntent.putExtra(RecordingFragment.RECORDING, 1);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

        //new InfoHandler(getContext()).saveExtraFromActivity(RecordingFragment.CURRENT_TIME,mTime); // this just save the time


    }

    private ImageView.OnClickListener mStartStopRecordingListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isRecording){
                mStartRecording.setImageResource(mStartStopRecordingIcon[0]);
                stopCountDownTimer();
            }
            else {
                mStartRecording.setImageResource(mStartStopRecordingIcon[1]);

                InfoHandler ih = new InfoHandler(getContext());
                String jsonDevices = ih.getPatientDevicesJson();
                ArrayList<Dispositivo> dispositivos = ih.getPatientDevices(jsonDevices, Dispositivo.class);
                String raspberryMacAddress="";

                for (int i=0;i<dispositivos.size();i++) {
                    if (dispositivos.get(i).getDeviceName().toLowerCase().equals("raspberry")) {
                        raspberryMacAddress = dispositivos.get(i).getDeviceMacAddress();
                    }
                }

                if (!raspberryMacAddress.equals("")) {


                    //BluetoothConnectionTask mConnection =
                    //        BluetoothConnectionTask.getInstance(getActivity(), raspberryMacAddress);


                    BluetoothService bluetoothService = new BluetoothService(getActivity(), raspberryMacAddress);
                    bluetoothService.connect();

                    if (bluetoothService.sendData("1") == BluetoothService.DATA_SUCESSFULLY_SENDED) {
                        System.out.println("iniciar grabacion");
                    } else {
                        System.out.println("error al iniciar grabacion");
                    }

                    startCountDownTimer();
                } else {
                    System.out.println("No tiene dispositivo de adquisicion asociado");
                }
            }
            isRecording = !isRecording;
        }
    };

    private void mRestartRecordingListener() {
            stopCountDownTimer();
            startCountDownTimer();
    };

    private void startCountDownTimer() {
        getActivity().startService(new Intent(getActivity(), CountDown.class));
        Log.i("MyTAG", "Started countdown");
        getActivity().registerReceiver(br, new IntentFilter(CountDown.COUNTDOWN_BR));

        //mCountDownTimer.start();
        //Log.i("MyTAG: ","Current thread: " + mCountDownTimer);
        //new InfoHandler(getContext()).saveReferceObject(mCountDownTimer);
    }

    private void stopCountDownTimer() {
        getActivity().stopService(new Intent(getActivity(), CountDown.class));
        Log.i(TAG, "Stopped service");
        //mCountDownTimer.cancel();
    }

    private void setProgressBarValues() {
        mProgressBarCircle.setMax((int) totalTimeCountInMilliSeconds / 1000);
        mProgressBarCircle.setProgress((int) totalTimeCountInMilliSeconds / 1000);
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
