package com.pt.myeeg.request;

import android.content.Context;

import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge Zepeda Tinoco on 23/12/17.
 * jorzet.94@gmail.com
 */

public class GetPatientSchedules extends AbstractRequestTask {

    private Paciente mPatient;
    private Context mContext;

    public GetPatientSchedules(Context context, Paciente patient){
        this.mContext = context;
        this.mPatient = patient;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.GET_PATIENT_SCHEDULES + mPatient.getId();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestGetPatientSchedules(getUrl());
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
