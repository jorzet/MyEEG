package com.pt.myeeg.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.database.InfoHandler;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 */

public class ProfileFragmentSpetialist extends BaseFragment{
    private TextView mEmail;
    private TextView mGender;
    private TextView mPatients;
    private TextView mSchedules;
    private TextView mAboutUser;
    private ProgressBar mProgressBar;
    private View mData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;
        View rootView = inflater.inflate(R.layout.profile_fragment_spetialist, container, false);
        mEmail = (TextView) rootView.findViewById(R.id.email_spetialist);
        mGender = (TextView) rootView.findViewById(R.id.gender_spetialist);
        mPatients = (TextView) rootView.findViewById(R.id.patients_spetialist);
        mSchedules = (TextView) rootView.findViewById(R.id.schedules_spetialist);
        mAboutUser = (TextView) rootView.findViewById(R.id.about_spetialist);
        //mProgressBar = (ProgressBar) rootView.findViewById(R.id.profile_progress);
        //mData = (View) rootView.findViewById(R.id.scroll_profile_data);


        getInfoUser();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    private void getInfoUser(){
        InfoHandler ih = new InfoHandler(getContext());
        Especialista spetialist = ih.getSpetialistInfo();
        mEmail.setText(spetialist.getEmail());
        mGender.setText(spetialist.getGender());
        mPatients.setText(ih.getPatientsCount() + "");
        mSchedules.setText(ih.getSpetialistSchedulesCount() + "");
        mAboutUser.setText("padecimiento epilepsia");
    }

}

