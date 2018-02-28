package com.pt.myeeg.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.models.Paciente;

import java.util.ArrayList;

/**
 * Created by Jorge Zepeda Tinoco on 16/12/17.
 * jorzet.94@gmail.com
 */

public class PatientsAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<Paciente> mPatients;


    public PatientsAdapter(Context context, ArrayList<Paciente> patients){
        this.mContext = context;
        this.mPatients = patients;
    }

    @Override
    public int getCount() {
        return mPatients.size();
    }

    @Override
    public Object getItem(int position) {
        return mPatients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Paciente patient = (Paciente)getItem(position);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_patient_listview, viewGroup, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mPatientName.setText(patient.getName() + " "  + patient.getFirstLastName() + " " + patient.getSecondLastName());
        if(patient.getPrifilePhoto() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(patient.getPrifilePhoto(), 0, patient.getPrifilePhoto().length);
            holder.mProfilePhoto.setImageBitmap(bmp);
        }
        return convertView;
    }


    private class ViewHolder{
        RoundedImageView mProfilePhoto;
        TextView mPatientName;
        //Button mViewResults;

        public ViewHolder(View v) {
            mProfilePhoto = (RoundedImageView) v.findViewById(R.id.profile_photo);
            mPatientName = (TextView) v.findViewById(R.id.name_patient);
            //mViewResults = (Button) v.findViewById(R.id.view_results);
        }

    }

}
