package com.pt.myeeg.ui.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.fragments.recording.RecordingFragment;
import com.pt.myeeg.fragments.schedule.ScheduleFragment;
import com.pt.myeeg.fragments.schedule.SchedulesFragment;
import com.pt.myeeg.services.android.CountDown;
import com.pt.myeeg.services.database.InfoHandler;

import java.util.ArrayList;


/**
 * Created by Jorge Zepeda Tinoco on 24/07/17.
 */

public class ContentScheduleActivity extends BaseActivityLifecycle{
    private ImageView mBackButton;
    private ImageView mRoundedDate;
    private TextView mDate;
    private Toolbar mToolbar;
    private Cita schedule;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_schedule);

        mBackButton = (ImageView) findViewById(R.id.arrow_back);
        mRoundedDate = (ImageView) findViewById(R.id.rounded_date_schedule_container);
        mDate = (TextView) findViewById(R.id.date_container_schedule);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_container_schedule);

        mBackButton.setOnClickListener(backAction);


        InfoHandler myHandler = new InfoHandler(getApplicationContext());


        schedule = myHandler.getCurrentScheduele();

        String[] date = myHandler.getExtraStored(SchedulesFragment.DATE_TEXT).split(" ");
        mDate.setText(date[1]);

        mContext = getApplicationContext();
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(myHandler.getExtraStored(SchedulesFragment.DATE_COLOR));
        TextDrawable drawable = TextDrawable.builder().buildRound(date[0], color);

        mRoundedDate.setImageDrawable(drawable);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, color);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (int) animator.getAnimatedValue();
                mToolbar.setBackgroundColor(color);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(color);
                }
            }
        });
        colorAnimation.start();
        mToolbar.setBackgroundColor(color);

        Bundle extras = getIntent().getExtras();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_schedule, new RecordingFragment());
        ft.commit();


        /*if(extras!=null && extras.getInt(RecordingFragment.RECORDING) == 1 || Boolean.parseBoolean(new InfoHandler(getApplication()).getExtraStored(RecordingFragment.RECORDING))){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container_schedule, new RecordingFragment());
            ft.commit();
        }
        else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container_schedule, new BluetoothConnectionFragment());
            ft.commit();
        }*/
    }

    private View.OnClickListener backAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        InfoHandler ih = new InfoHandler(getApplication());
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container_schedule) instanceof RecordingFragment
                && Boolean.parseBoolean(ih.getExtraStored(RecordingFragment.RECORDING))) {
            ih.saveExtraFromActivity(RecordingFragment.FROM_RECORDING, "true");
            ih.saveExtraFromActivity(RecordingFragment.SCHEDULE_ID, schedule.getFolioCita() + "");
        } else {
            ih.saveExtraFromActivity(RecordingFragment.IN_RECORDING, null);
            ih.saveExtraFromActivity(RecordingFragment.SCHEDULE_ID, null);
            // when recording finish stop service
            this.stopService(new Intent(this, CountDown.class));
            Log.i("ContentScheduleAct", "Stopped service");
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InfoHandler ih = new InfoHandler(getApplication());
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container_schedule) instanceof RecordingFragment && Boolean.parseBoolean(ih.getExtraStored(RecordingFragment.RECORDING)))
            ih.saveExtraFromActivity(RecordingFragment.FROM_RECORDING, "true");
        else
            ih.saveExtraFromActivity(RecordingFragment.IN_RECORDING, null);

    }
}
