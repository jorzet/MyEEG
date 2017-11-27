package com.pt.myeeg.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.services.database.InfoHandler;

/**
 * Created by Jorge Zepeda Tinoco on 09/07/17.
 */

public class ProfileFragment extends BaseFragment {

    private TextView mSpetialist;
    private TextView mIllness;
    private TextView mGender;
    private TextView mLastStudy;
    private TextView mTotalStudies;
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
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        mSpetialist = (TextView) rootView.findViewById(R.id.especialista_paciente);
        mIllness = (TextView) rootView.findViewById(R.id.enfermedad_paciente);
        mGender = (TextView) rootView.findViewById(R.id.genero_paciente);
        mLastStudy = (TextView) rootView.findViewById(R.id.last_study_patient);
        mTotalStudies = (TextView) rootView.findViewById(R.id.total_studies_patient);
        mAboutUser = (TextView) rootView.findViewById(R.id.about_patient);
        //mProgressBar = (ProgressBar) rootView.findViewById(R.id.profile_progress);
        //mData = (View) rootView.findViewById(R.id.scroll_profile_data);


        getInfoUser();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    private void getInfoUser(){
        Paciente patient = new InfoHandler(getContext()).getPatientInfo();
        if(patient.getEspecialista()!=null)
            mSpetialist.setText(patient.getEspecialista().getName());
        else
            mSpetialist.setText("No asignado");
        mIllness.setText(patient.getPadecimiento());
        mGender.setText(patient.getGender());
        mTotalStudies.setText("10");
        mLastStudy.setText("13/12/17");
        mAboutUser.setText("padecimiento epilepsia");
    }

}
