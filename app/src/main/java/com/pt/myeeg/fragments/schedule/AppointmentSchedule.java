package com.pt.myeeg.fragments.schedule;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.Spinner;
import android.widget.TimePicker;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.CalibrationCanvas;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.database.InfoHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 */

public class AppointmentSchedule extends BaseFragment {

    private List<String> mPatients;

    private Spinner mChoosePatient;
    private TimePicker mChooseTime;
    private DatePicker mChooseDate;
    private Button mAppointSchedule;
    private CalibrationCanvas mCalibrationCanvas;

    private int hour;
    private int minute;

    private List<String> channels = new ArrayList<>();

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
        mChooseTime = (TimePicker) rootView.findViewById(R.id.choose_time_picker);
        mChooseDate = (DatePicker) rootView.findViewById(R.id.choose_date_picker);
        mAppointSchedule = (Button) rootView.findViewById(R.id.appoint_schedule);
        mCalibrationCanvas = (CalibrationCanvas) rootView.findViewById(R.id.choose_channels);

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

        mChooseTime.setOnTimeChangedListener(timePickerListener);


        mCalibrationCanvas.setOnTouchListener(mCalibrationListener);

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

                        mCalibrationCanvas.setElectrodesAsociatedWithPatient(channels);
                        //mCalibrationCanvas.draw(mCalibrationCanvas.getCanvas());
                        channels.add(mCalibrationCanvas.chanels[i]);

                        Log.d("Appointment",channels.size() + " ---- " );

                    }
                }
                return true;
            }
            return false;
        }
    };

    /*private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    // set current time into timepicker
                    mChooseTime.setCurrentHour(hour);
                    mChooseTime.setCurrentMinute(minute);

                }
            };*/


}
