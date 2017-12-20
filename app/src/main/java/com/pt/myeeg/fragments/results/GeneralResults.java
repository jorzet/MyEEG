package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;

/**
 * Created by Jorge on 19/12/17.
 */

public class GeneralResults extends BaseFragment{

    TextView mScheduleId;
    TextView mBrainZone;
    TextView mDominantWaveType;
    TextView mPercentage;
    Button mGoSelectorResults;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.general_results, container, false);

        mScheduleId = (TextView) rootView.findViewById(R.id.schedule_id);
        mBrainZone = (TextView) rootView.findViewById(R.id.brain_zone);
        mDominantWaveType = (TextView) rootView.findViewById(R.id.dominant_wave_type);
        mPercentage = (TextView) rootView.findViewById(R.id.percentage_wave_type);
        mGoSelectorResults = (Button) rootView.findViewById(R.id.show_choose_parameters_fragment);

        mGoSelectorResults.setOnClickListener(selectorAction);

        return rootView;
    }

    private View.OnClickListener selectorAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goSelectorFragment();
        }
    };

    private void goSelectorFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_results, new ChooseParameters());
        ft.commit();
    }

}
