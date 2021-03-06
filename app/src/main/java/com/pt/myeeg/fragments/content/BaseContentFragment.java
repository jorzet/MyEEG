package com.pt.myeeg.fragments.content;

import android.os.Bundle;
import android.util.Log;

import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.request.ContentRequestManager;

/**
 * Created by Jorge Zepeda Tinoco on 22/12/17.
 * jorzet.94@gmail.com
 */

public abstract class BaseContentFragment extends BaseFragment {

    private ContentRequestManager mContentRequestManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentRequestManager = new ContentRequestManager(getContext());
    }


    protected void requestDoLogIn(String email, String password) {
        mContentRequestManager.requestDoLogIn(email, password, new ContentRequestManager.OnDoLogInListener(){
            @Override
            public void onDoLogInLoaded(String result) {
                Log.i("DoLogIn1: ","login:" + result);
                onDoLogInSuccess(result);
            }

            @Override
            public void onDoLogInError(String throwable) {
                onDoLogInFail(throwable);
            }
        });
    }

    protected void requestSingUpPatient(Paciente patient) {
        mContentRequestManager.requestSingUpPatient(patient, new ContentRequestManager.OnSingUpPatientListener(){
            @Override
            public void onSingUpPatientLoaded(String result) {
                Log.i("DoLogIn1: ","login:" + result);
                onSingUpPatientSuccess(result);
            }

            @Override
            public void onSingUpPatientError(String throwable) {
                onSingUpPatientFail(throwable);
            }
        });
    }

    protected void requestGetSpetialistData() {
        mContentRequestManager.requestGetSpetialistData(new ContentRequestManager.OnGetSpetialistDataListener() {
            @Override
            public void onGetSpetialistDataLoaded(String result) {
                onGetSpetialistDataSuccess(result);
            }

            @Override
            public void onGetSpetialistDataError(String throwable) {
                onGetSpetialistDataFail(throwable);
            }
        });
    }

    protected void requestGetPatientData() {
        mContentRequestManager.requestGetPatientData(new ContentRequestManager.OnGetPatientDataListener() {
            @Override
            public void onGetPatientDataLoaded(String result) {
                onGetPatientDataSuccess(result);
            }

            @Override
            public void onGetPatientDataError(String throwable) {
                onGetPatientDataFail(throwable);
            }
        });
    }

    protected void requestGetSpetialistSchedules() {
        mContentRequestManager.requestGetSpetialistSchedules(new ContentRequestManager.OnGetSpetialistSchedulesListener() {
            @Override
            public void onGetSpetialistSchedulesLoaded(String result) {
                onGetSpetialistSchedulesSuccess(result);
            }

            @Override
            public void onGetSpetialistSchedulesError(String throwable) {
                onGetSpetialistSchedulesFail(throwable);
            }
        });
    }

    protected void requestGetPatientsBySpetialist() {
        mContentRequestManager.requestGetPatientsBySpetialist(new ContentRequestManager.OnGetPatientsBySpetialistListener() {
            @Override
            public void onGetPatientsBySpetialistLoaded(String result) {
                onGetPatientsBySpetialistSuccess(result);
            }

            @Override
            public void onGetPatientsBySpetialistError(String throwable) {
                onGetPatientsBySpetialistFail(throwable);
            }
        });
    }


    protected void requestGetPatientSchedules() {
        mContentRequestManager.requestGetPatientSchedules(new ContentRequestManager.OnGetPatientSchedulesListener(){
            @Override
            public void onGetPatientSchedulesLoaded(String result) {
                onGetPatientSchedulesSuccess(result);
            }

            @Override
            public void onGetPatientSchedulesError(String throwable) {
                onGetPatientSchedulesFail(throwable);
            }
        });
    }


    protected void requestGetDevices() {
        mContentRequestManager.requestGetDevices(new ContentRequestManager.OnGetDevicesListener() {
            @Override
            public void onGetDevicesLoaded(String result) {
                onGetDevicesSuccess(result);
            }

            @Override
            public void onGetDevicesError(String throwable) {
                onGetDevicesFail(throwable);
            }
        });
    }

    protected void requestScheduleAppointment(Cita schedule) {
        mContentRequestManager.requestScheduleAppointment(schedule, new ContentRequestManager.OnScheduleAppointmentListener() {
            @Override
            public void onScheduleAppointmentLoaded(String result) {
                onScheduleAppointmentSuccess(result);
            }

            @Override
            public void onScheduleAppointmentError(String throwable) {
                onScheduleAppointmentFail(throwable);
            }
        });
    }

    protected void requestGetGeneralResults(int scheduleId) {
        mContentRequestManager.requestGetGeneralResults(scheduleId, new ContentRequestManager.OnGetGeneralResultsListener() {
            @Override
            public void onGetGeneralResultsLoaded(String result) {
                onGetGeneralResultsSuccess(result);
            }

            @Override
            public void onGetGeneralResultsError(String throwable) {
                onGetGeneralResultsFail(throwable);
            }
        });
    }

    protected void requestGetSegmentResults(int scheduleId, String channel, int sinceSecond, int toSecond) {
        mContentRequestManager.requestGetSegmentResults(scheduleId, channel, sinceSecond, toSecond, new ContentRequestManager.OnGetSegmentResultsListener() {
            @Override
            public void onGetSegmentResultsLoaded(String result) {
                onGetSegmentResultsSuccess(result);
            }

            @Override
            public void onGetSegmentResultsError(String throwable) {
                onGetSegmentResultsFail(throwable);
            }
        });
    }

    public void onDoLogInSuccess(String response) {
    }

    public void onDoLogInFail(String response) {
    }

    public void onSingUpPatientSuccess(String response) {

    }

    public void onSingUpPatientFail(String response) {

    }

    public void onGetSpetialistDataSuccess(String response) {
    }

    public void onGetSpetialistDataFail(String response) {
    }

    public void onGetPatientDataSuccess(String response) {
    }

    public void onGetPatientDataFail(String response) {
    }

    public void onGetSpetialistSchedulesSuccess(String response) {
    }

    public void onGetSpetialistSchedulesFail(String response) {
    }

    public void onGetPatientsBySpetialistSuccess(String response) {
    }

    public void onGetPatientsBySpetialistFail(String response) {
    }

    public void onGetPatientSchedulesSuccess(String response) {
    }

    public void onGetPatientSchedulesFail(String response) {
    }

    public void onGetDevicesSuccess(String response) {
    }

    public void onGetDevicesFail(String response) {
    }

    public void onScheduleAppointmentSuccess(String response) {

    }

    public void onScheduleAppointmentFail(String response) {

    }

    public void onGetGeneralResultsSuccess(String response) {

    }

    public void onGetGeneralResultsFail(String response) {

    }

    public void onGetSegmentResultsSuccess(String response) {

    }

    public void onGetSegmentResultsFail(String response) {

    }

}
