package com.pt.myeeg.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.logintemsandconditions.SinginFragment;
import com.pt.myeeg.fragments.schedule.CalibrationFragment;

/**
 * Created by Jorge Zepeda Tinoco on 7/1/2017.
 */

public class LoginActivity extends BaseActivityLifecycle{

    public static final String TAG = "login_activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        if (savedInstanceState == null) {
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.add(R.id.fragment_container, new CalibrationFragment());
            //ft.commit();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, new SinginFragment());
            ft.commit();
        }
    }

}
