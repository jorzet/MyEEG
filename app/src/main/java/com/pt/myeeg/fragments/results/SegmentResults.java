package com.pt.myeeg.fragments.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseFragment;

/**
 * Created by Jorge Zepeda Tinoco on 19/12/17.
 */

public class SegmentResults extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.segment_results_fragment, container, false);

        return rootView;
    }
}
