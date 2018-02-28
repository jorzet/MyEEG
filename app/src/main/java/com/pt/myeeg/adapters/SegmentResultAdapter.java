package com.pt.myeeg.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.models.ResultadosSegmento;

import java.util.ArrayList;

/**
 * Created by Jorge Zepeda Tinoco on 01/01/18.
 * jorzet.94@gmail.com
 */

public class SegmentResultAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ResultadosSegmento> mSegmentResults;

    public SegmentResultAdapter(Context context, ArrayList<ResultadosSegmento> segmentResults){
        this.mContext = context;
        this.mSegmentResults = segmentResults;
    }

    @Override
    public int getCount() {
        return mSegmentResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mSegmentResults.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ResultadosSegmento segmentResult = (ResultadosSegmento) getItem(position);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_table_segment_results, viewGroup, false);
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.second.setText(String.valueOf(segmentResult.getSegundo()));
        holder.channel.setText(segmentResult.getCanal());
        holder.dominantFrequency.setText(String.valueOf(segmentResult.getFrecuenciaDominante()));
        holder.waveType.setText(segmentResult.getTipoOnda());
        if(segmentResult.isAnormal())
            holder.isAnormal.setText("Si");
        else
            holder.isAnormal.setText("No");

        return convertView;
    }

    private class ViewHolder{
        TextView second;
        TextView channel;
        TextView dominantFrequency;
        TextView waveType;
        TextView isAnormal;
        public ViewHolder(View v) {
            second = (TextView) v.findViewById(R.id.second);
            channel = (TextView) v.findViewById(R.id.channel);
            dominantFrequency = (TextView) v.findViewById(R.id.dominant_frequency);
            waveType = (TextView) v.findViewById(R.id.wave_type);
            isAnormal = (TextView) v.findViewById(R.id.is_anormal);
        }
    }
}
