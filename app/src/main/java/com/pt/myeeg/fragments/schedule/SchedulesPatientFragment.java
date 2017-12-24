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
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.fragments.results.GeneralResults;
import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.ui.activities.ContentScheduleActivity;
import com.pt.myeeg.ui.dialogs.ErrorDialog;

import java.util.ArrayList;

import static com.pt.myeeg.models.Palabras.ID_PATIENT;

/**
 * Created by Jorge Zepeda Tinoco on 22/12/17.
 */

public class SchedulesPatientFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    /* for Bundle */
    public static final String DATE_COLOR = "date_color";
    public static final String DATE_TEXT = "date_text";

    /* for View */
    private ListView listView;
    private TextView mErrorSchedule;
    private ArrayList<String> stringScheduleList;
    private ArrayAdapter<String> adapter;

    private ArrayList<Cita> citas;

    private int idPatient;

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

        idPatient = Integer.parseInt(new InfoHandler(getContext()).getExtraStored(ID_PATIENT));

        setData();
        if(stringScheduleList.isEmpty()) {
            mErrorSchedule.setText("Este paciente aun no tiene citas");
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
                    if(citas.get(i).getPaciente().getId() == idPatient) {
                        stringScheduleList.add(citas.get(i).getDayAndMonthFormath());
                    }
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
        goGeneralResultsFragment();
    }

    private void goGeneralResultsFragment(){
        getFragmentManager().beginTransaction().replace(R.id.fragment_container_results, new GeneralResults()).commit();
    }
}