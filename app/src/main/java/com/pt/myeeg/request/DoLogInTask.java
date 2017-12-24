package com.pt.myeeg.request;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.webservice.HttpRequest;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge Zepeda Tinoco on 22/12/17.
 */

public class DoLogInTask extends AbstractRequestTask {


    private String email;
    private String password;

    private Context mContext;

    public DoLogInTask(Context context, String email, String password) {
        this.mContext = context;
        this.email = email;
        this.password = password;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.SING_IN + JSONBuilder.buildLoginJson(email,password);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestLogin(getUrl(), mContext);
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
