package com.pt.myeeg.fragments.schedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.CalibrationCanvas;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.database.InfoHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 */

public class AppointmentScheduleFragment extends BaseContentFragment {

    private List<String> mPatients;
    private List<Integer> mPatientIds;

    private View rootView;
    private ScrollView mScrollViewSchedule;
    private Spinner mChoosePatient;
    private DatePicker mChooseDate;
    private TimePicker mChooseTime;
    private TimePicker mChooseDuration;
    private CalibrationCanvas mCalibrationCanvas;
    private EditText mObservations;
    private Button mAppointSchedule;


    private int hour;
    private int minute;

    private List<String> channels = new ArrayList<>();
    private int[] electrodes = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        rootView = inflater.inflate(R.layout.appointment_schedule_fragment, container,false);

        mScrollViewSchedule = (ScrollView) rootView.findViewById(R.id.scroll_view_schedule);
        mChoosePatient = (Spinner) rootView.findViewById(R.id.choose_petient_spinner);
        mChooseDate = (DatePicker) rootView.findViewById(R.id.choose_date_picker);
        mChooseTime = (TimePicker) rootView.findViewById(R.id.choose_time_picker);
        mChooseDuration = (TimePicker) rootView.findViewById(R.id.choose_duration_picker);
        mCalibrationCanvas = (CalibrationCanvas) rootView.findViewById(R.id.choose_channels);
        mObservations = (EditText) rootView.findViewById(R.id.insert_observations);
        mAppointSchedule = (Button) rootView.findViewById(R.id.appoint_schedule);

        loadPatients();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mPatients);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChoosePatient.setAdapter(dataAdapter);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        // set current time into timepicker
        mChooseTime.setCurrentHour(hour);
        mChooseTime.setCurrentMinute(minute);

        mChooseDuration.setCurrentHour(0);
        mChooseDuration.setCurrentMinute(0);

        mChooseTime.setOnTimeChangedListener(timePickerListener);

        mChooseTime.setIs24HourView(true);
        mChooseDuration.setIs24HourView(true);


        mCalibrationCanvas.setOnTouchListener(mCalibrationListener);
        mAppointSchedule.setOnClickListener(mAppointScheduleListener);

        return rootView;
    }




    private void loadPatients() {
        mPatients = new ArrayList<>();
        mPatientIds = new ArrayList<>();
        List<Paciente> patients = new InfoHandler(getContext()).getPatientsSpetialist();
        if(patients != null) {
            mPatients.add(Palabras.SELECT_A_PATIENT);

            for (int i = 0; i < patients.size(); i++){
                Paciente patient = patients.get(i);
                mPatients.add(patient.getName() + " " + patient.getFirstLastName() + " " + patient.getSecondLastName() + "");
                mPatientIds.add(patient.getId());
            }
        }
        else
            mPatients.add(Palabras.WITHOUT_PATIENTS);
    }


    private TimePicker.OnTimeChangedListener timePickerListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker timePicker, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into timepicker
            mChooseTime.setCurrentHour(hour);
            mChooseTime.setCurrentMinute(minute);
        }
    };

    private View.OnClickListener mAppointScheduleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doAppointmentSchedule();
        }
    };

    private View.OnTouchListener mCalibrationListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i("MyTAG: ","id: "+v.getId());
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                for(int i=0;i<mCalibrationCanvas.electrodes.length;i++) {
                    if (event.getX() > (float) (mCalibrationCanvas.percentageElectrode[i][0] * mCalibrationCanvas.width) &&
                            event.getX() < (float) (mCalibrationCanvas.percentageElectrode[i][0] * mCalibrationCanvas.width + 100) &&
                            event.getY() > (float) (mCalibrationCanvas.percentageElectrode[i][1] * mCalibrationCanvas.width) &&
                            event.getY() < (float) (mCalibrationCanvas.percentageElectrode[i][1] * mCalibrationCanvas.width + 100)){


                        if(electrodes[i] == CalibrationCanvas.ELECTRODE_RED) {
                            electrodes[i] = CalibrationCanvas.ELECTRODE_GREEN;
                            channels.add(mCalibrationCanvas.chanels[i]);
                        } else {
                            electrodes[i] = CalibrationCanvas.ELECTRODE_RED;
                            for (int j = 0; j < channels.size(); j++) {
                                if (channels.get(j).equals(mCalibrationCanvas.chanels[i])) {
                                    channels.remove(j);
                                }
                            }
                        }

                        mCalibrationCanvas = (CalibrationCanvas) rootView.findViewById(R.id.choose_channels);
                        mCalibrationCanvas.setElectrodesAsociatedWithPatient(electrodes);
                        mCalibrationCanvas.invalidate();
                        Log.d("Appointment",channels.size() + " ---- " );

                    }
                }
                return true;
            }
            return false;
        }
    };

    private void doAppointmentSchedule(){
        String patient = mChoosePatient.getSelectedItem().toString();
        int patientId = mPatientIds.get(mChoosePatient.getSelectedItemPosition()-1);

        int day = mChooseDate.getDayOfMonth();
        int month = mChooseDate.getMonth() + 1;
        int year = mChooseDate.getYear();
        String selectedDate = year + "-" + month + "-" + day;

        int hour = mChooseTime.getCurrentHour();
        int minute = mChooseTime.getCurrentMinute();
        String selectedHour = hour + ":" + minute;

        int hourDuration = mChooseDuration.getCurrentHour();
        int minuteDuration = mChooseDuration.getCurrentMinute();
        String selectedDuration = hourDuration + ":" + minuteDuration;

        int count = 0;
        for(int i = 0; i < electrodes.length; i++){
            if(i != 8 && i != 14 && electrodes[i] != 1)
                count++;
        }

        String observations = mObservations.getText().toString();

        if (!patient.equals(Palabras.WITHOUT_PATIENTS)) {
            if (!patient.equals(Palabras.SELECT_A_PATIENT)) {
                if(count > 0){
                    if (observations.equals("")) {
                        observations = Palabras.WITHOUT_OBSERVATIONS;
                    }

                    Log.d("Appointment", "entra");
                    Log.d("Appointment", "paciente: "+patientId+" -- "+patient);
                    Log.d("Appointment", "fecha: " + selectedDate);
                    Log.d("Appointment", "hora: " + selectedHour);
                    Log.d("Appointment", "duracion: " + selectedDuration);
                    Log.d("Appointment", "electrodos: ");
                    String[] electrodes = new String[channels.size()-2];
                    for (int j = 0; j < channels.size(); j++) {
                        if(!channels.get(j).equals("A1") && !channels.get(j).equals("A2")) {
                            Log.d("Appointment", channels.get(j));
                            electrodes[j] = channels.get(j);
                        }
                    }
                    Log.d("Appointment", "observaciones: "+observations);


                    Cita schedule = new Cita();
                    Paciente patientO = new Paciente();
                    patientO.setId(patientId);
                    schedule.setPaciente(patientO);

                    schedule.setDuracion(selectedDuration);
                    schedule.setFecha(selectedDate);
                    schedule.setHora(selectedHour);
                    schedule.setElectrodos(electrodes);
                    schedule.setObservaciones(observations);

                    requestScheduleAppointment(schedule);

                } else {
                    Log.d("Appointment","seleccione los electrodos a usar");
                }
            } else {
                Log.d("Appointment","seleccione un paciente");
            }
        } else {
            Log.d("Appointment","no puede agendar una cita, no tiene pacientes registrados");
        }


    }

    private void resetView(){
        // put selected patient in first position
        mChoosePatient.setSelection(0);

        // set time in 8:00 AM
        mChooseTime.setCurrentHour(8);
        mChooseTime.setCurrentMinute(0);

        // set duration in zero
        mChooseDuration.setCurrentHour(0);
        mChooseDuration.setCurrentMinute(0);

        // set selected electrodes in none
        for (int i = 0; i < electrodes.length; i++){
            electrodes[i] = 1;
        }
        mCalibrationCanvas = (CalibrationCanvas) rootView.findViewById(R.id.choose_channels);
        mCalibrationCanvas.setElectrodesAsociatedWithPatient(electrodes);
        mCalibrationCanvas.invalidate();

        // set observation in null
        mObservations.setText("");

        // scroll view to the top
        mScrollViewSchedule.fullScroll(ScrollView.FOCUS_UP);
    }

    private void showDialog(String message){
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
    public void onScheduleAppointmentSuccess(String response) {
        super.onScheduleAppointmentSuccess(response);
        Log.d("Appointment",response);
        resetView();
        showDialog(response);
    }

    @Override
    public void onScheduleAppointmentFail(String response) {
        super.onScheduleAppointmentFail(response);
        showDialog(response);
    }
}
