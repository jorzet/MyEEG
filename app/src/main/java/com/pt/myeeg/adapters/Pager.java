package com.pt.myeeg.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pt.myeeg.fragments.Patients.PatientsFragment;
import com.pt.myeeg.fragments.profile.ProfileFragmentPatient;
import com.pt.myeeg.fragments.profile.ProfileFragmentSpetialist;
import com.pt.myeeg.fragments.schedule.AppointmentScheduleFragment;
import com.pt.myeeg.fragments.schedule.SchedulesFragment;
import com.pt.myeeg.services.database.InfoHandler;

/**
 * Created by Jorge Zepeda Tinoco on 09/07/17.
 */

public class Pager extends FragmentStatePagerAdapter{

    private int mTabCount;
    public boolean isMedic = false;
    public Context mContext;

    public Pager(Context context, FragmentManager fm, int tabCount) {
        super(fm);
        this.mContext = context;
        this.mTabCount= tabCount;
        isMedic = getCurrentUser();
    }

    public boolean getCurrentUser(){
        return new InfoHandler(mContext).getIsMedic();
    }

    @Override
    public Fragment getItem(int position) {
        if(isMedic) {
            switch (position) {
                case 0:
                    return new ProfileFragmentSpetialist();
                case 1:
                    return new PatientsFragment();
                case 2:
                    return new AppointmentScheduleFragment();
                default:
                    return null;
            }
        }
        else{
            switch (position) {
                case 0:
                    return new ProfileFragmentPatient();
                case 1:
                    return new SchedulesFragment();
                default:
                    return null;
            }
        }

    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
