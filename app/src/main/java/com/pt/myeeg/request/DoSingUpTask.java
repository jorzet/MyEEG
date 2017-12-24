package com.pt.myeeg.request;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge on 23/12/17.
 */

public class DoSingUpTask extends AbstractRequestTask {
    private Context mContext;
    private Paciente mUser;

    public DoSingUpTask(Context context, Paciente patient){
        this.mContext = context;
        this.mUser = patient;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.SING_UP_PATIENT + JSONBuilder.bildSingupJson(mUser).replace("{","%7B").replace("}","%7D");
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestSingupPatient(getUrl(), mContext);
    }

    @Override
    protected void onPostExecute(Object response) {
        super.onPostExecute(response);

        Log.i("DoLogIn3: ","login:" + response);
        if (response==null || response.equals("") || ((String)response).contains("Error")) {
            onRequestFailListener.onFailed(response);
        } else {
            onRequestSuccessListener.onSuccess(response);
        }
    }
}
