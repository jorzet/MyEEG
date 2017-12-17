package com.pt.myeeg.ui.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.Pager;
import com.pt.myeeg.adapters.RoundedImageView;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.fragments.recording.RecordingFragment;
import com.pt.myeeg.services.android.CountDown;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.dialogs.ErrorDialog;

/**
 * Created by Jorge Zepeda Tinoco on 7/1/2017.
 */

public class ContentActivity extends BaseActivityLifecycle implements TabLayout.OnTabSelectedListener, View.OnClickListener{

    /* For the View */
    private ImageView mProfilePhoto;
    private ImageView mLogout;
    private ImageView mSettings;
    private TextView mName;
    private TextView mAge;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    ImageView mBluetoothIcon;
    private Pager mAdapter;

    private String mTime;

    private int[] mTabIcons = {
            R.drawable.ic_profile,
            R.drawable.ic_home,
            R.drawable.ic_calendar,
    };

    /* For the Fragments tag */
    private static final String HOME_TAG = "home_tag";
    private static final String RECORD_TAG = "record_tag";
    private static final String SCHEDULE_TAG = "schedule_tag";
    private static final String PROFILE_TAG = "profile_tag";

    /* Variable for SharedPreferences */
    public boolean isMedic = true;

    /* Variable for intent */
    private boolean isFromContentScheduleActivity = false;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplication();
        isMedic = getCurrentUser();

        mProfilePhoto = (RoundedImageView) findViewById(R.id.user_profile_photo);
        mLogout = (ImageView) findViewById(R.id.user_logout);
        mSettings = (ImageView) findViewById(R.id.user_settings);
        mName = (TextView) findViewById(R.id.user_profile_name);
        mAge = (TextView) findViewById(R.id.user_profile_age_);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mBluetoothIcon = (ImageView) findViewById(R.id.small_bluetooth_icon);
        if(isMedic) {// if is medic or specialist
            mTabLayout.addTab(mTabLayout.newTab().setTag(HOME_TAG));
            mTabLayout.addTab(mTabLayout.newTab().setTag(SCHEDULE_TAG));
            mTabLayout.addTab(mTabLayout.newTab().setTag(PROFILE_TAG));
        }
        else{ // if is a patient
            mTabLayout.addTab(mTabLayout.newTab().setTag(HOME_TAG));
            mTabLayout.addTab(mTabLayout.newTab().setTag(SCHEDULE_TAG));
            //mTabLayout.addTab(mTabLayout.newTab().setTag(SCHEDULE_TAG));
        }
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new Pager(getApplicationContext(), getSupportFragmentManager(), mTabLayout.getTabCount());

        mLogout.setOnClickListener(this);
        mSettings.setOnClickListener(this);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        setIcons();
        mTabLayout.setOnTabSelectedListener(this);

        getInfoUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfoUser();
        mContext = getApplication();

        isFromContentScheduleActivity = Boolean.parseBoolean(new InfoHandler(getApplication()).getExtraStored(RecordingFragment.FROM_RECORDING));
        if(isFromContentScheduleActivity) { // just execute when comes from ContentScheduleActivity
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    BroadcastReceiver br = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getExtras() != null) {
                                if (!intent.getBooleanExtra(CountDown.COUNT_DOWN_FINISHED, false)) {
                                    mTime = intent.getStringExtra(CountDown.CURRENT_STRING_TIME);
                                    addNotification(false);
                                } else if (intent.getBooleanExtra(CountDown.COUNT_DOWN_FINISHED, false)) {
                                    addNotification(true);
                                }
                            }
                        }
                    };
                    registerReceiver(br, new IntentFilter(CountDown.COUNTDOWN_BR));
                }
            }, 100);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    private void setIcons() {
        if(isMedic) {
            mTabLayout.getTabAt(0).setIcon(mTabIcons[0]);
            mTabLayout.getTabAt(1).setIcon(mTabIcons[1]);
            mTabLayout.getTabAt(2).setIcon(mTabIcons[2]);
        } else{
            mTabLayout.getTabAt(0).setIcon(mTabIcons[0]);
            mTabLayout.getTabAt(1).setIcon(mTabIcons[2]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_logout:
                doLogout();
                break;
            case R.id.user_settings:
                goSettings();
                break;
        }
    }

    private void addNotification(boolean isFinished) {
        NotificationCompat.Builder builder;
        if(isFinished)
            builder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_chronometer_notification)
                    .setContentTitle("Grabación Finalizada")
                    .setContentText("Total de tiempo: ");
        else

            builder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_chronometer_notification)
                    .setContentTitle("Tiempo restante: "+mTime)
                    .setContentText("Total de tiempo: ");

        Intent notificationIntent;

        notificationIntent = new Intent(getApplicationContext(), ContentScheduleActivity.class);

        notificationIntent.putExtra(RecordingFragment.RECORDING, 1);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

        //new InfoHandler(getContext()).saveExtraFromActivity(RecordingFragment.CURRENT_TIME,mTime); // this just save the time


    }

    public boolean getCurrentUser(){
        return new InfoHandler(mContext).getIsMedic();
    }

    private void getInfoUser(){
        if(isMedic) {
            Especialista spetialist = new InfoHandler(getApplicationContext()).getSpetialistInfo();
            if(spetialist != null){
                mName.setText(spetialist.getName() + " " + spetialist.getFirstLastName() + " " + spetialist.getSecondLastName());
            }
        } else {
            Paciente patient = new InfoHandler(getApplicationContext()).getPatientInfo();
            if (patient != null) {
                mName.setText(patient.getName() + " " + patient.getFirstLastName() + " " + patient.getSecondLastName());
                mAge.setText(patient.getAge() + " años");
            }
        }
    }

    private void doLogout() {
        if(!Boolean.parseBoolean(new InfoHandler(getApplication()).getExtraStored(RecordingFragment.RECORDING))) {
            removeSessionData();
            goLogInActivity();
        }
        else{
            new ErrorDialog(this).showErrorLogOut();
        }
    }

    private void goSettings() {
        Intent intent = new Intent(ContentActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void goLogInActivity(){
        Intent intent = new Intent(ContentActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void removeSessionData(){
        new InfoHandler(getApplicationContext()).removePatientAndToken();
    }

}
