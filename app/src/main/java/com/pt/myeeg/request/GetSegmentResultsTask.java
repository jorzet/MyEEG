package com.pt.myeeg.request;

import android.content.Context;

import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

/**
 * Created by Jorge on 24/12/17.
 */

public class GetSegmentResultsTask extends AbstractRequestTask {

    boolean byInterval = true;

    private int scheduleId;
    private int siceSecond;
    private int toSecond;
    private String channel;

    private Context mContext;

    public GetSegmentResultsTask(Context context, int scheduleId, String channel, int siceSecond, int toSecond) {
        this.mContext = context;
        this.scheduleId = scheduleId;
        this.siceSecond = siceSecond;
        this.toSecond = toSecond;
        this.channel = channel;
    }

    @Override
    protected String getUrl() {
        return MetadataInfo.URL + MetadataInfo.GET_SEGMENT_RESULTS_BY_INTERVAL +
                JSONBuilder.buildSegmentResultsByIntervalJson(scheduleId, siceSecond, toSecond, channel);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return MetadataInfo.requestGetSegmentResults(getUrl(), mContext);
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
