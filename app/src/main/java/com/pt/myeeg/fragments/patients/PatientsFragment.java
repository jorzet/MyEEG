package com.pt.myeeg.fragments.patients;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.PatientsAdapter;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.activities.ContentResultActivity;
import com.pt.myeeg.ui.activities.LoginActivity;

import java.util.ArrayList;

import static com.pt.myeeg.models.Palabras.ID_PATIENT;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 * jorzet.94@gmail.com
 */

public class PatientsFragment extends BaseContentFragment implements AdapterView.OnItemClickListener{

    public static final String PATIENT_NAME = "patient_name";

    private ArrayList<Paciente> mPatients;

    /* for View */
    private ListView listView;
    private TextView mErrorPatients;
    private ImageButton mRefreshView;
    private ImageButton mAddPatient;
    private ProgressBar mLoadingData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.patients_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_patients);
        mErrorPatients = (TextView) rootView.findViewById(R.id.patients_error);
        mRefreshView = (ImageButton) rootView.findViewById(R.id.refresh_view);
        mAddPatient = (ImageButton) rootView.findViewById(R.id.add_patient);
        mLoadingData = (ProgressBar) rootView.findViewById(R.id.loading_patients);

        listView.setOnItemClickListener(this);

        loadPatients();
        if (mPatients == null) {
            mErrorPatients.setText(Palabras.WITHOUT_PATIENTS);
            listView.setVisibility(View.GONE);
            mErrorPatients.setVisibility(View.VISIBLE);
        } else if(mPatients.isEmpty()) {
            mErrorPatients.setText(Palabras.WITHOUT_PATIENTS);
            listView.setVisibility(View.GONE);
            mErrorPatients.setVisibility(View.VISIBLE);
        } else {
            mErrorPatients.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            PatientsAdapter adapter = new PatientsAdapter(getContext(), mPatients);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

        mAddPatient.setOnClickListener(mAddPatientListener);
        mRefreshView.setOnClickListener(mRefreshViewListener);

        // to check if is coming from singup fragment
        // set patients node selected
        InfoHandler ih = new InfoHandler(getContext());
        if (Boolean.parseBoolean(ih.getExtraStored(Palabras.FROM_SINGUP_FRAGMENT_SUCCESS))){
            String message = ih.getExtraStored(Palabras.SUCESSFULL_SINGUP);
            showSingUpSuccessDialog(message);
        }

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    private void loadPatients() {
        mPatients = new InfoHandler(getContext()).getPatientsSpetialist();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        InfoHandler myHandler = new InfoHandler(getContext());

        Intent intent = new Intent(getActivity(), ContentResultActivity.class);
        Paciente patient = (Paciente) adapterView.getItemAtPosition(position);
        myHandler.saveExtraFromActivity(PatientsFragment.PATIENT_NAME, patient.getName() + " " + patient.getFirstLastName() + " " + patient.getSecondLastName());
        myHandler.saveExtraFromActivity(ID_PATIENT, patient.getId() + "");

        TextView sharedTextView = (TextView) view.findViewById(R.id.name_patient);
        Pair<View, String> p1 = Pair.create((View) sharedTextView, "p");

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1);

        startActivity(intent, transitionActivityOptions.toBundle());
    }


    private View.OnClickListener mAddPatientListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goSingUpPatient();
        }
    };

    private View.OnClickListener mRefreshViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadData();
        }
    };

    private void goSingUpPatient(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        Bundle b = new Bundle();
        b.putBoolean(Palabras.FROM_PATIENTS_LIST_FRAGMENT,true);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void loadData(){
        requestGetPatientsBySpetialist();
    }

    private void showSingUpSuccessDialog(String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Mensaje");
        alert.setMessage(message);
        alert.setPositiveButton(Palabras.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.show();
    }

    @Override
    public void onGetPatientsBySpetialistSuccess(String response) {
        super.onGetPatientsBySpetialistSuccess(response);
        new InfoHandler(getContext()).savePatiensSpetialist(response);
        requestGetSpetialistSchedules();
    }

    @Override
    public void onGetPatientsBySpetialistFail(String response) {
        super.onGetPatientsBySpetialistFail(response);
    }

    @Override
    public void onGetSpetialistSchedulesSuccess(String response) {
        super.onGetSpetialistSchedulesSuccess(response);

        new InfoHandler(getContext()).savePatientSchedules(response);
        loadPatients();
        PatientsAdapter adapter = new PatientsAdapter(getContext(), mPatients);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onGetSpetialistSchedulesFail(String response) {
        super.onGetSpetialistSchedulesFail(response);
    }
}
