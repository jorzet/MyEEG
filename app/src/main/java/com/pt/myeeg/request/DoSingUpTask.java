package com.pt.myeeg.request;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jorge Zepeda Tinoco on 23/12/17.
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
            Log.i("DoSingUp3: ","url:" + MetadataInfo.URL + MetadataInfo.SING_UP_PATIENT + JSONBuilder.buildSingupJson(mUser));
            return MetadataInfo.URL + MetadataInfo.SING_UP_PATIENT + JSONBuilder.buildSingupJson(mUser);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestSingupPatient(getUrl(), mContext);
    }

    @Override
    protected void onPostExecute(Object response) {
        super.onPostExecute(response);

        Log.i("DoSingUp3: ","login:" + response);
        if (response==null || response.equals("") || ((String)response).contains("Error")) {
            onRequestFailListener.onFailed(response);
        } else {
            onRequestSuccessListener.onSuccess(response);
        }
    }
}
