package com.pt.myeeg.request;

import android.content.Context;

import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge Zepeda Tinoco on 24/12/17.
 */

public class GetGeneralResultTask extends AbstractRequestTask {

    int idSchedule;
    Context mContext;

    public GetGeneralResultTask(Context context, int idSchedule) {
        this.mContext = context;
        this.idSchedule = idSchedule;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.GET_GENERAL_RESULTS + idSchedule;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestGetGeneralResults(getUrl(), mContext);
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
