package com.pt.myeeg.request;

import android.content.Context;

import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge Zepeda Tinoco on 23/12/17.
 * jorzet.94@gmail.com
 */

public class GetSpetialistSchedulesTask extends AbstractRequestTask {
    private Especialista mSpetialist;
    private Context mContext;

    public GetSpetialistSchedulesTask(Context context, Especialista spetialist){
        this.mContext = context;
        this.mSpetialist = spetialist;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.GET_SPETIALIST_SCHEDULES + mSpetialist.getId();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestGetSpetialistSchedules(getUrl());
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
