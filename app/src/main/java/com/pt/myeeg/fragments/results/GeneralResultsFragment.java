package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.ResultadosGenerales;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.services.webservice.JSONBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Zepeda Tinoco on 19/12/17.
 */

public class GeneralResultsFragment extends BaseContentFragment{

    private RelativeLayout mGeneralResultsView;
    private TextView mIdSchedule;
    private TextView mBrainZone;
    private TextView mDominantWaveType;
    private TextView mPercentage;
    private TextView mElectrodesUsed;
    private TextView mDuration;
    private Button mGoSelectorResults;

    private ProgressBar mLoadingGeneralResults;
    private TextView mErrorNotGeneralResults;

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

        mGeneralResultsView = (RelativeLayout) rootView.findViewById(R.id.general_results_view);
        mIdSchedule = (TextView) rootView.findViewById(R.id.id_schedule);
        mBrainZone = (TextView) rootView.findViewById(R.id.brain_zone);
        mDominantWaveType = (TextView) rootView.findViewById(R.id.dominant_wave_type);
        mPercentage = (TextView) rootView.findViewById(R.id.percentage_wave_type);
        mElectrodesUsed = (TextView) rootView.findViewById(R.id.electrodes_used);
        mDuration = (TextView) rootView.findViewById(R.id.study_duration);
        mGoSelectorResults = (Button) rootView.findViewById(R.id.show_choose_parameters_fragment);

        mLoadingGeneralResults = (ProgressBar) rootView.findViewById(R.id.loading_general_results);
        mErrorNotGeneralResults = (TextView) rootView.findViewById(R.id.error_not_general_results);

        mGoSelectorResults.setOnClickListener(selectorAction);

        mGeneralResultsView.setVisibility(View.GONE);
        mLoadingGeneralResults.setVisibility(View.VISIBLE);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int scheduleId = Integer.parseInt(new InfoHandler(getContext()).getExtraStored(Palabras.SCHEDULE_ID));
        requestGetGeneralResults(scheduleId);
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


    @Override
    public void onGetGeneralResultsSuccess(String response) {
        super.onGetGeneralResultsSuccess(response);
        Log.d("GeneralResults","response: "+response);
        mLoadingGeneralResults.setVisibility(View.GONE);
        if(!response.equals(Palabras.ERROR_NOT_GENERAL_RESULTS)) {
            new InfoHandler(getContext()).saveGeneralResults(response);

            ResultadosGenerales generalResults = (ResultadosGenerales) JSONBuilder.getObjectFromJson(response, ResultadosGenerales.class);

            mGeneralResultsView.setVisibility(View.VISIBLE);
            mIdSchedule.setText(generalResults.getCita().getFolioCita() + "");
            mBrainZone.setText(generalResults.getZonaCerebral());
            mDominantWaveType.setText(generalResults.getTipoOndaDominante());
            mPercentage.setText(generalResults.getPorcentajeTipoOnda() + " %");

            // gets electrode list in string
            String[] electrodes = generalResults.getCita().getElectrodos();
            String electodesUsed = "";
            for(int i = 0; i < electrodes.length; i++){
                electodesUsed = electodesUsed + electrodes[i] + ", ";
            }
            // removes last comma
            mElectrodesUsed.setText(electodesUsed.substring(0, electodesUsed.length() - 2));
            mDuration.setText(generalResults.getCita().getDuracion());

        } else {
            mGeneralResultsView.setVisibility(View.GONE);
            mErrorNotGeneralResults.setText(response);
            mErrorNotGeneralResults.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetGeneralResultsFail(String response) {
        super.onGetGeneralResultsFail(response);
        mLoadingGeneralResults.setVisibility(View.GONE);
        mGeneralResultsView.setVisibility(View.GONE);
        mErrorNotGeneralResults.setText(response);
        mErrorNotGeneralResults.setVisibility(View.VISIBLE);
    }

}
