package com.pt.myeeg.fragments.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.database.InfoHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 */

public class AppointmentSchedule extends BaseFragment {

    private List<String> mPatients;

    private Spinner mChoosePatient;
    private DatePicker mChooseDate;
    private Button mAppointSchedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        View rootView = inflater.inflate(R.layout.appointment_schedule_fragment, container,false);

        mChoosePatient = (Spinner) rootView.findViewById(R.id.choose_petient_spinner);
        mChooseDate = (DatePicker) rootView.findViewById(R.id.choose_date_picker);
        mAppointSchedule = (Button) rootView.findViewById(R.id.appoint_schedule);

        loadPatients();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mPatients);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChoosePatient.setAdapter(dataAdapter);
        return rootView;
    }

    private void loadPatients() {
        mPatients = new ArrayList<>();
        List<Paciente> patients = new InfoHandler(getContext()).getPatientsSpetialist();
        if(patients != null)
            mPatients.add("Seleccione un paciente");
        else
            mPatients.add("Sin pacientes");
        for (int i = 0; i < patients.size(); i++){
            Paciente patient = patients.get(i);
            mPatients.add(patient.getName() + " " + patient.getFirstLastName() + " " + patient.getSecondLastName() + "");
        }
    }


}
