package com.pt.myeeg.fragments.results;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.models.ResultadosSegmento;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.services.webservice.JSONBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 24/12/17.
 */

public class SegmentResultsFragment extends BaseContentFragment {

    ArrayList<ResultadosSegmento> segmentResults;

    LineChart mPlot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.segment_results_fragment, container, false);

        mPlot = (LineChart) rootView.findViewById(R.id.seconds_plot);

        segmentResults =  new InfoHandler(getContext()).getSegmetResultsArrayList();
        List<Entry> entries = new ArrayList<Entry>();
        int lastPosition = 0;

        for (int i = 0; i < segmentResults.size(); i++) {
            String[] senal = segmentResults.get(i).getSenal().split(",");
            int j = 0;
            for (j = 0; j < senal.length; j++) {
                // turn your data into Entry objects
                Log.d("Senal",(Float.valueOf(senal[j])*10)+ ", ");
                entries.add(new Entry(j + lastPosition, Float.valueOf(senal[j])*10));
            }
            lastPosition = j;
        }

        LineDataSet dataSet = new LineDataSet(entries, "señal"); // add entries to dataset
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK); // styling, ...

        LineData lineData = new LineData(dataSet);
        mPlot.setData(lineData);
        mPlot.invalidate(); // refresh

        return rootView;
    }

}
