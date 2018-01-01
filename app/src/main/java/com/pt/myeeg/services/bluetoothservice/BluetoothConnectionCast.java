package com.pt.myeeg.services.bluetoothservice;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Jorge on 30/12/17.
 */

public class BluetoothConnectionCast extends Service {

    private static final String REQUEST_START_RECORDING = "1";
    private static final String RECORDING_SUCCESSFULLY = "recording_successfully";

    private BluetoothService mBluetoothService;
    private Activity mActivity;
    private String raspberryMacAddress;

    public interface OnRequestListenerSuccess{
        void onSuccess(Object result);
    }

    public interface OnRequestListenerFailed{
        void onFailed(Object result);
    }

    public interface  OnGetSegmentResultsListener {
        void onGetSegmentResultsLoaded(String result);
        void onGetSegmentResultsError(String throwable);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBluetoothService = new BluetoothService(getApplicationContext(), raspberryMacAddress);
        mBluetoothService.connect();

    }

    @Override
    public void onDestroy() {
        mBluetoothService.disconnect();
        Log.i("MyTAG", "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
