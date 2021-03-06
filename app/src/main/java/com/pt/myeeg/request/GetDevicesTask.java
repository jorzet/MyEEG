package com.pt.myeeg.request;

import android.content.Context;

import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge Zepeda Tinoco on 23/12/17.
 * jorzet.94@gmail.com
 */

public class GetDevicesTask extends AbstractRequestTask {

    private Paciente mPatient;
    private Context mContext;

    public GetDevicesTask(Context context, Paciente patient){
        this.mContext = context;
        this.mPatient = patient;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.GET_DEVICES_BY_PATIENT + mPatient.getId();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestGetDevicesByPatient(getUrl());
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
