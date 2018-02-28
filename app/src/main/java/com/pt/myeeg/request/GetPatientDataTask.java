package com.pt.myeeg.request;

import android.content.Context;

import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge on 01/01/18.
 * jorzet.94@gmail.com
 */

public class GetPatientDataTask extends AbstractRequestTask {


    private Context mContext;
    private Paciente mPatient;

    public GetPatientDataTask(Context context, Paciente patient){
        this.mContext = context;
        this.mPatient = patient;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.GET_PATIENT_DATA + mPatient.getId();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestGetPatientData(getUrl(), mContext);
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
