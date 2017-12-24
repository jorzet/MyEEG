package com.pt.myeeg.request;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Cita;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge on 23/12/17.
 */

public class ScheduleAppointmentTask extends AbstractRequestTask {
    Cita mSchedule;
    Context mContext;

    public ScheduleAppointmentTask(Context context, Cita schedule){
        this.mSchedule = schedule;
        this.mContext = context;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.APPOINTMENT_SCHEDULE + JSONBuilder.bildJsonFromObject(mSchedule).replace("{","%7B").replace("}","%7D");
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Log.d("ScheduleAppointment",getUrl());
        return MetadataInfo.requestAppointmentSchdule(getUrl(), mContext);
    }

    @Override
    protected void onPostExecute(Object response) {
        super.onPostExecute(response);

        if (response==null || response.equals("") || ((String)response).contains("Error")) {
            onRequestFailListener.onFailed(response);
        } else {
            onRequestSuccessListener.onSuccess(response);
        }
    }
}
