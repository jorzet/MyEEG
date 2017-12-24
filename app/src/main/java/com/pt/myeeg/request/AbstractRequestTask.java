package com.pt.myeeg.request;

import android.os.AsyncTask;

/**
 * Created by Jorge Zepeda Tinoco on 22/12/17.
 */

public class AbstractRequestTask<A, B, C> extends AsyncTask<A, B, C> {

    protected AbstractRequestTask.OnRequestListenerSuccess onRequestSuccessListener;
    protected AbstractRequestTask.OnRequestListenerFailed onRequestFailListener;

    public interface OnRequestListenerSuccess{
        void onSuccess(Object result);
    }

    public interface OnRequestListenerFailed{
        void onFailed(Object result);
    }

    public void setOnRequestSuccess(AbstractRequestTask.OnRequestListenerSuccess onRequestSuccess){
        this.onRequestSuccessListener = onRequestSuccess;
    }

    public void setOnRequestFailed(AbstractRequestTask.OnRequestListenerFailed onRequestFailed){
        this.onRequestFailListener = onRequestFailed;
    }

    protected String getUrl() {
        return null;
    }

    @Override
    protected C doInBackground(A... as) {
        return null;
    }

    @Override
    protected void onPostExecute(C c) {
        super.onPostExecute(c);
    }

    protected void onGettingResponse(String response){

    }


}

