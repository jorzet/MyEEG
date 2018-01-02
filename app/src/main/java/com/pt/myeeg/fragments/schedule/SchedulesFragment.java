package com.pt.myeeg.fragments.schedule;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.SchedulesAdapter;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.fragments.recording.RecordingFragment;
import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.activities.ContentScheduleActivity;
import com.pt.myeeg.ui.dialogs.ErrorDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jorge Zepeda Tinoco on 09/07/17.
 */

public class SchedulesFragment extends BaseContentFragment implements AdapterView.OnItemClickListener{

    /* for Bundle */
    public static final String DATE_COLOR = "date_color";
    public static final String DATE_TEXT = "date_text";

    /* for View */
    private ImageButton mRefreshView;
    private ListView listView;
    private TextView mErrorSchedule;
    private ArrayList<String> stringScheduleList;
    private ArrayAdapter<String> adapter;

    private ArrayList<Cita> citas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.schedules_fragment, container, false);

        mRefreshView = (ImageButton) rootView.findViewById(R.id.refresh_view);
        listView = (ListView) rootView.findViewById(R.id.list_schedule);
        mErrorSchedule = (TextView) rootView.findViewById(R.id.schedule_error);
        listView.setOnItemClickListener(this);

        setData();
        if(stringScheduleList.isEmpty()) {
            mErrorSchedule.setText("Aun  no tienes citas");
            listView.setVisibility(View.GONE);
            mErrorSchedule.setVisibility(View.VISIBLE);
        } else {
            mErrorSchedule.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new SchedulesAdapter(getContext(), R.layout.item_schedule_listview, stringScheduleList);
            listView.setAdapter(adapter);
        }

        mRefreshView.setOnClickListener(mRefreshViewListener);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    private void setData() {
        stringScheduleList = new ArrayList<>();
        String savedSchedules = new InfoHandler(getContext()).getPatientSchedulesJson();
        if (savedSchedules != null) {
            if (!savedSchedules.contains("Error")) {
                citas = new InfoHandler(getContext()).getPatientSchedules(savedSchedules, Cita.class);
                if (citas != null) {
                    for (int i = 0; i < citas.size(); i++) {
                        stringScheduleList.add(citas.get(i).getDayAndMonthFormath());
                    }
                }
            } else {
                mErrorSchedule.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                mErrorSchedule.setText(savedSchedules);
            }
        } else {
            mErrorSchedule.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            mErrorSchedule.setText(Palabras.ERROR_EMTY_SCHEDULES);
        }
    }

    private View.OnClickListener mRefreshViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadData();
        }
    };

    private void loadData(){
        requestGetPatientSchedules();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InfoHandler myHandler = new InfoHandler(getContext());
        String scheduleRecording = myHandler.getExtraStored(RecordingFragment.IN_RECORDING);
        if(scheduleRecording!=null) {


            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date = null;
            long time = 0;

            try {

                date = formatter.parse(citas.get(position).getFecha());
                time = sdf.parse(citas.get(position).getHora()).getTime();

                System.out.println("time: "+date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date currentTime = Calendar.getInstance().getTime();
            currentTime.setTime(0);
            System.out.println("currentTime: "+currentTime);

            if(date != null) {
                if (date.getYear() == currentTime.getYear() && date.getMonth() == currentTime.getMonth() && date.getDay() == currentTime.getDay()) {
                    Date currentDate = Calendar.getInstance().getTime();
                    long currentHour = currentDate.getTime();

                    if (currentHour < time) {
                        System.out.println("Aun no es tiempo de tu grabacion, cita programada para las: "+ citas.get(position).getHora());
                        new ErrorDialog(getContext()).showErrorNotTimeSchedule(citas.get(position).getHora());
                    } else {
                        System.out.println("empieza grabacion");
                        String[] schedulePositionValues = scheduleRecording.split("-");
                        InfoHandler ih = new InfoHandler(getContext());
                        String jsonDevices = ih.getPatientDevicesJson();
                        ArrayList<Dispositivo> dispositivos = ih.getPatientDevices(jsonDevices, Dispositivo.class);

                        if(dispositivos != null) {

                            //if (Boolean.parseBoolean(schedulePositionValues[1]) && Integer.parseInt(schedulePositionValues[0]) == position) {
                            Intent intent = new Intent(getActivity(), ContentScheduleActivity.class);

                            myHandler.saveExtraFromActivity(SchedulesFragment.DATE_COLOR, (String) listView.getAdapter().getItem(position));
                            myHandler.saveExtraFromActivity(SchedulesFragment.DATE_TEXT, stringScheduleList.get(position));
                            myHandler.saveExtraFromActivity(Palabras.SPETIALIST_SUGGESTIONS, citas.get(position).getObservaciones());
                            myHandler.saveCurrentSchedule(citas.get(position));
                            myHandler.saveExtraFromActivity(RecordingFragment.TO_RECORDING, "true");
                            myHandler.saveExtraFromActivity(RecordingFragment.IN_RECORDING, "true");
                            //intent.putExtra(SchedulesFragment.DATE_COLOR,(String)listView.getAdapter().getItem(position));
                            //intent.putExtra(SchedulesFragment.DATE_TEXT,stringArrayList.get(position));
                            //intent.putExtra(Palabras.SPETIALIST_SUGGESTIONS, citas.get(position).getObservaciones());

                            ImageView ivDate = (ImageView) view.findViewById(R.id.date_schedule);
                            TextView tvDate = (TextView) view.findViewById(R.id.date);
                            Pair<View, String> p1 = Pair.create((View) ivDate, "p");
                            Pair<View, String> p2 = Pair.create((View) tvDate, "date_text_container_schedule");

                            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2);

                            startActivity(intent, transitionActivityOptions.toBundle());
                            //} else
                            //    new ErrorDialog(getContext()).showErrorNewRecording();
                        } else {
                            new ErrorDialog(getContext()).showErrorNotDevices();
                        }
                    }
                } else if (date.getYear() > currentTime.getYear() && date.getMonth() > currentTime.getMonth() && date.getDay() > currentTime.getDay()) {
                    System.out.println("Aun no es tiempo de tu grabacion, cita programada para el dia: "+ citas.get(position).getFecha() + " a las: "+ citas.get(position).getHora());
                    new ErrorDialog(getContext()).showErrorNotSheduleDate(citas.get(position).getFecha(), citas.get(position).getHora());
                } else if (date.getYear() < currentTime.getYear() && date.getMonth() < currentTime.getMonth() && date.getDay() < currentTime.getDay()) {
                    System.out.println("No se puede hacer una grabacion de citas pasadas");
                    new ErrorDialog(getContext()).showErrorNotAllowRecording();
                }
            }
        }
        else{



            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date date = null;
            long time = 0;

            try {

                date = formatter.parse(citas.get(position).getFecha());
                time = sdf.parse(citas.get(position).getHora()).getTime();

                System.out.println("time: "+date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date currentTime = Calendar.getInstance().getTime();
            currentTime.setHours(0);
            currentTime.setMinutes(0);
            currentTime.setSeconds(0);
            System.out.println("currentTime: "+currentTime);

            if(date != null) {
                if (date.getYear() == currentTime.getYear() && date.getMonth() == currentTime.getMonth() && date.getDay() == currentTime.getDay()) {
                    Date currentDate = Calendar.getInstance().getTime();
                    long currentHour = currentDate.getTime();

                    if (currentHour < time) {
                        System.out.println("Aun no es tiempo de tu grabacion, cita programada para las: "+ citas.get(position).getHora());
                        new ErrorDialog(getContext()).showErrorNotTimeSchedule(citas.get(position).getHora());
                    } else {
                        System.out.println("empieza grabacion");

                        InfoHandler ih = new InfoHandler(getContext());
                        String jsonDevices = ih.getPatientDevicesJson();
                        ArrayList<Dispositivo> dispositivos = ih.getPatientDevices(jsonDevices, Dispositivo.class);

                        if(dispositivos != null) {
                            Intent intent = new Intent(getActivity(), ContentScheduleActivity.class);

                            myHandler.saveExtraFromActivity(SchedulesFragment.DATE_COLOR, (String) listView.getAdapter().getItem(position));
                            myHandler.saveExtraFromActivity(SchedulesFragment.DATE_TEXT, stringScheduleList.get(position));
                            myHandler.saveExtraFromActivity(Palabras.SPETIALIST_SUGGESTIONS, citas.get(position).getObservaciones());
                            myHandler.saveExtraFromActivity(Palabras.SCHEDULE_POSITION, position + "-" + "false");
                            myHandler.saveExtraFromActivity(RecordingFragment.TO_RECORDING, "true");
                            myHandler.saveExtraFromActivity(RecordingFragment.IN_RECORDING, "true");

                            myHandler.saveCurrentSchedule(citas.get(position));
                            //intent.putExtra(SchedulesFragment.DATE_COLOR,(String)listView.getAdapter().getItem(position));
                            //intent.putExtra(SchedulesFragment.DATE_TEXT,stringArrayList.get(position));
                            //intent.putExtra(Palabras.SPETIALIST_SUGGESTIONS, citas.get(position).getObservaciones());

                            ImageView ivDate = (ImageView) view.findViewById(R.id.date_schedule);
                            TextView tvDate = (TextView) view.findViewById(R.id.date);
                            Pair<View, String> p1 = Pair.create((View) ivDate, "p");
                            Pair<View, String> p2 = Pair.create((View) tvDate, "date_text_container_schedule");

                            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2);

                            startActivity(intent, transitionActivityOptions.toBundle());
                        } else {
                            new ErrorDialog(getContext()).showErrorNotDevices();
                        }
                    }
                } else if (date.getYear() > currentTime.getYear() && date.getMonth() > currentTime.getMonth() && date.getDay() > currentTime.getDay()) {
                    System.out.println("Aun no es tiempo de tu grabacion, cita programada para el dia: "+ citas.get(position).getFecha() + " a las: "+ citas.get(position).getHora());
                    new ErrorDialog(getContext()).showErrorNotSheduleDate(citas.get(position).getFecha(), citas.get(position).getHora());
                } else if (date.getYear() < currentTime.getYear() && date.getMonth() < currentTime.getMonth() && date.getDay() < currentTime.getDay()) {
                    System.out.println("No se puede hacer una grabacion de citas pasadas");
                    new ErrorDialog(getContext()).showErrorNotAllowRecording();
                }
            }

        }

    }


    @Override
    public void onGetPatientSchedulesSuccess(String response) {
        super.onGetPatientSchedulesSuccess(response);
        setData();
        if(stringScheduleList.isEmpty()) {
            mErrorSchedule.setText("Aun  no tienes citas");
            listView.setVisibility(View.GONE);
            mErrorSchedule.setVisibility(View.VISIBLE);
        } else {
            mErrorSchedule.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new SchedulesAdapter(getContext(), R.layout.item_schedule_listview, stringScheduleList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onGetPatientSchedulesFail(String response) {
        super.onGetPatientSchedulesFail(response);
    }
}
