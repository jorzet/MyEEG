package com.pt.myeeg.fragments.Patients;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.PatientsAdapter;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.activities.ContentResultActivity;
import com.pt.myeeg.ui.activities.ContentScheduleActivity;

import java.util.ArrayList;

import static com.pt.myeeg.models.Palabras.ID_PATIENT;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 */

public class PatientsFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public static final String PATIENT_NAME = "patient_name";

    private ArrayList<Paciente> mPatients;

    /* for View */
    private ListView listView;
    private TextView mErrorPatients;

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
        listView.setOnItemClickListener(this);

        loadPatients();
        if(mPatients.isEmpty()) {
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


}
