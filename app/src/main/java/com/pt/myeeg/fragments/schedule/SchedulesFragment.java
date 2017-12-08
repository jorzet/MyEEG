package com.pt.myeeg.fragments.schedule;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.SchedulesAdapter;
import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.activities.ContentScheduleActivity;
import com.pt.myeeg.ui.dialogs.ErrorDialog;

import java.util.ArrayList;

/**
 * Created by Jorge Zepeda Tinoco on 09/07/17.
 */

public class SchedulesFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    /* for Bundle */
    public static final String DATE_COLOR = "date_color";
    public static final String DATE_TEXT = "date_text";

    /* for View */
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
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    private void setData() {
        stringScheduleList = new ArrayList<>();
        String savedSchedules = new InfoHandler(getContext()).getPatientSchedulesJson();
        if(!savedSchedules.contains("Error")) {
            citas = new InfoHandler(getContext()).getPatientSchedules(savedSchedules, Cita.class);
            if (citas != null) {
                for (int i = 0; i < citas.size(); i++) {
                    stringScheduleList.add(citas.get(i).getDayAndMonthFormath());
                }
            }
        }
        else{
            mErrorSchedule.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            mErrorSchedule.setText(savedSchedules);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InfoHandler myHandler = new InfoHandler(getContext());
        String scheduleRecording = myHandler.getExtraStored(Palabras.SCHEDULE_POSITION);
        if(scheduleRecording!=null) {
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
        else{
            Intent intent = new Intent(getActivity(), ContentScheduleActivity.class);

            myHandler.saveExtraFromActivity(SchedulesFragment.DATE_COLOR, (String) listView.getAdapter().getItem(position));
            myHandler.saveExtraFromActivity(SchedulesFragment.DATE_TEXT, stringScheduleList.get(position));
            myHandler.saveExtraFromActivity(Palabras.SPETIALIST_SUGGESTIONS, citas.get(position).getObservaciones());
            myHandler.saveExtraFromActivity(Palabras.SCHEDULE_POSITION, position + "-" + "false");
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
        }

    }
}
