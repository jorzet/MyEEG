package com.pt.myeeg.fragments.Patients;

import android.os.Bundle;
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
import com.pt.myeeg.services.database.InfoHandler;

import java.util.ArrayList;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 */

public class PatientsFragment extends BaseFragment implements AdapterView.OnItemClickListener{

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
            mErrorPatients.setText("Aun  no tienes pacientes");
            listView.setVisibility(View.GONE);
            mErrorPatients.setVisibility(View.VISIBLE);
        } else {
            mErrorPatients.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            PatientsAdapter adapter = new PatientsAdapter(getContext(), mPatients);
            listView.setAdapter(adapter);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
