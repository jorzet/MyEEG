package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;

/**
 * Created by Jorge on 19/12/17.
 */

public class ChooseParameters extends BaseFragment {

    private Spinner mChooseChannel;
    private EditText mSiceSecond;
    private EditText mToSecond;
    private Button mGetResults;
    private ProgressBar mProgressBar;

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
        mSiceSecond = (EditText) rootView.findViewById(R.id.sice_second);
        mToSecond = (EditText) rootView.findViewById(R.id.to_second);
        mGetResults = (Button) rootView.findViewById(R.id.show_segment_results);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_getting_results);

        mGetResults.setOnClickListener(getResultsAction);

        return rootView;
    }

    private View.OnClickListener getResultsAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goSegmentResults();
        }
    };

    private void goSegmentResults(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_results, new SegmentResults());
        ft.commit();
    }
}
