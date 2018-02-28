package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.pt.myeeg.fragments.content.BaseFragment;

/**
 * Created by Jorge on 26/11/17.
 * jorzet.94@gmail.com
 */

public class PatientScheduleResult extends BaseFragment{

    private LineChart mSecondPlot;
    //private TextView


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
