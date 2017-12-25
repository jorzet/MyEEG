package com.pt.myeeg.fragments.results;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.ResultadosGenerales;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.ui.activities.SegmentResultsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 19/12/17.
 */

public class ChooseParameters extends BaseContentFragment {

    private List<String> mChannels;

    private RelativeLayout mChooseSeconds;
    private Spinner mChooseChannel;
    private Switch mByInterval;
    private EditText mSiceSecond;
    private EditText mToSecond;
    private EditText mSecond;
    private Button mGetSegmentResults;
    private ProgressBar mProgressBar;

    private TextView mIntevalText;
    private TextView mSecondText;

    private boolean isBySecond;
    private int scheduleId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.choose_parameters_fragment, container, false);


        mChooseChannel = (Spinner) rootView.findViewById(R.id.choose_channel_spinner);
        mChooseSeconds = (RelativeLayout) rootView.findViewById(R.id.choose_seconds);
        mByInterval = (Switch) rootView.findViewById(R.id.by_interval);
        mSiceSecond = (EditText) rootView.findViewById(R.id.sice_second);
        mToSecond = (EditText) rootView.findViewById(R.id.to_second);
        mSecond = (EditText) rootView.findViewById(R.id.second);
        mGetSegmentResults = (Button) rootView.findViewById(R.id.show_segment_results);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_getting_results);

        mIntevalText = (TextView) rootView.findViewById(R.id.interval_text);
        mSecondText = (TextView) rootView.findViewById(R.id.second_text);

        mGetSegmentResults.setOnClickListener(getResultsAction);
        mByInterval.setOnCheckedChangeListener(changeListener);

        // gets channels from cache
        loadChannels();

        // load channels in spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mChannels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mChooseChannel.setAdapter(dataAdapter);

        return rootView;
    }

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean bySecond) {
            isBySecond = bySecond;
            if (bySecond) {
                mIntevalText.setVisibility(View.GONE);
                mSecondText.setVisibility(View.VISIBLE);
                mChooseSeconds.setVisibility(View.GONE);
                mSecond.setVisibility(View.VISIBLE);
            } else {
                mIntevalText.setVisibility(View.VISIBLE);
                mSecondText.setVisibility(View.GONE);
                mChooseSeconds.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener getResultsAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getSegmentResults();
        }
    };

    private void loadChannels(){
        mChannels = new ArrayList<>();

        ResultadosGenerales generalResults = (ResultadosGenerales) JSONBuilder.getObjectFromJson(
                        new InfoHandler(getContext()).getJsonGeneralResults(),
                        ResultadosGenerales.class);
        String[] electrodes = generalResults.getCita().getElectrodos();
        scheduleId = generalResults.getCita().getFolioCita();

        if(electrodes.length > 0) {
            mChannels.add(Palabras.SELECT_A_CHANNEL);
            for (int i = 0; i < electrodes.length; i++) {
                mChannels.add(electrodes[i]);
            }
        } else {
            mChannels.add("Sin electrodos");
        }
    }

    private void getSegmentResults(){
        String channel = mChooseChannel.getSelectedItem().toString();

        if (!channel.equals(Palabras.SELECT_A_CHANNEL)) {
            if (isBySecond) {
                int second = Integer.parseInt(mSecond.getText().toString());
                mProgressBar.setVisibility(View.VISIBLE);
                requestGetSegmentResults(scheduleId, channel, second, second);
            } else {
                int since = Integer.parseInt(mSiceSecond.getText().toString());
                int to = Integer.parseInt(mToSecond.getText().toString());
                mProgressBar.setVisibility(View.VISIBLE);
                requestGetSegmentResults(scheduleId, channel, since, to);
            }
        } else {

        }

    }

    private void goSegmentResults(){
        Intent intent = new Intent(getContext(), SegmentResultsActivity.class);
        startActivity(intent);
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_results, new SegmentResultsFragment());
        ft.commit();*/
    }

    @Override
    public void onGetSegmentResultsSuccess(String response) {
        super.onGetSegmentResultsSuccess(response);
        mProgressBar.setVisibility(View.GONE);
        Log.d("SegmentResult",response);
        new InfoHandler(getContext()).saveSegmentResults(response);
        goSegmentResults();
    }

    @Override
    public void onGetSegmentResultsFail(String response) {
        super.onGetSegmentResultsFail(response);
        mProgressBar.setVisibility(View.GONE);
        Log.d("SegmentResult","Error al obtener resultados: "+response);
    }
}
