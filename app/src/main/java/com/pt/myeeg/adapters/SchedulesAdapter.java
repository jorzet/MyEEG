package com.pt.myeeg.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pt.myeeg.R;
import com.pt.myeeg.services.database.InfoHandler;

import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco
 * jorzet.94@gmail.com
 * @version 1.0
 * @created 13-Jul-2017
 */

public class SchedulesAdapter extends ArrayAdapter<String> {

    private Context activity;
    private List<String> friendList;
    private InfoHandler ih;

    public SchedulesAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.friendList = objects;
        this.ih = new InfoHandler(context);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public String getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_schedule_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mGeneralDescription.setText("Estudio programado");
        
        if (ih.getIsMedic()) {
            holder.mDescription.setText("Tu paciente tiene una cita programada para el dia: " + getItem(position));
        } else {
            holder.mDescription.setText("Tienes una cita programada para el dia: " + getItem(position));
        }

        //get first letter of each String item

        String[] date = getItem(position).split(" ");
        holder.date.setText(date[1].substring(0,3));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(date[0], color); // radius in px

        holder.mDate.setImageDrawable(drawable);

        return convertView;
    }

    private class ViewHolder {
        private ImageView mDate;
        private TextView mGeneralDescription;
        private TextView mDescription;
        private TextView date;

        public ViewHolder(View v) {
            mDate = (ImageView) v.findViewById(R.id.date_schedule);
            date = (TextView) v.findViewById(R.id.date);
            mGeneralDescription = (TextView) v.findViewById(R.id.general_description_schedule);
            mDescription = (TextView) v.findViewById(R.id.description_schedule);

        }
    }
}