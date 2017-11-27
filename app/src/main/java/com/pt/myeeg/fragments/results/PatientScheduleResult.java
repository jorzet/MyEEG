package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.myeeg.fragments.content.BaseFragment;

/**
 * Created by Jorge on 26/11/17.
 */

public class PatientScheduleResult extends BaseFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(container == null)
            return null;

        View rootView = null;

        return rootView;
    }
}
