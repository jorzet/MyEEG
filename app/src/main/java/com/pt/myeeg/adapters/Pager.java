package com.pt.myeeg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pt.myeeg.fragments.profile.ProfileFragment;
import com.pt.myeeg.fragments.results.PatientResults;
import com.pt.myeeg.fragments.schedule.SchedulesFragment;

/**
 * Created by Jorge Zepeda Tinoco on 09/07/17.
 */

public class Pager extends FragmentStatePagerAdapter{

    private int mTabCount;
    public boolean isMEdic = false;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.mTabCount= tabCount;
    }

    public void getCurrentUser(){
        //Using shared preferences

    }

    @Override
    public Fragment getItem(int position) {
        if(isMEdic) {
            switch (position) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new SchedulesFragment();
                case 2:
                    return new SchedulesFragment();
                default:
                    return null;
            }
        }
        else{
            switch (position) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new PatientResults();
                case 2:
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
