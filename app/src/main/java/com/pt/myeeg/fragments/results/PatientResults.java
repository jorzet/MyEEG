package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;

/**
 * Created by Jorge Zepeda Tinoco on 13/07/17.
 */

public class PatientResults extends BaseFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.patient_results_fragment, container, false);




        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }
}
