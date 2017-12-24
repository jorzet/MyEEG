package com.pt.myeeg.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.patients.PatientsFragment;
import com.pt.myeeg.fragments.schedule.SchedulesPatientFragment;
import com.pt.myeeg.services.database.InfoHandler;

/**
 * Created by Jorge Zepeda Tinoco on 19/12/17.
 */

public class ContentResultActivity extends BaseActivityLifecycle {

    private ImageView mBackButton;
    private TextView mNamePatient;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_results);

        mBackButton = (ImageView) findViewById(R.id.arrow_back);
        mNamePatient = (TextView) findViewById(R.id.name_patient);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_container_results);

        mBackButton.setOnClickListener(backAction);

        InfoHandler myHandler = new InfoHandler(getApplicationContext());

        String namePatient = myHandler.getExtraStored(PatientsFragment.PATIENT_NAME);

        mNamePatient.setText(namePatient);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_results, new SchedulesPatientFragment());
        ft.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private View.OnClickListener backAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
