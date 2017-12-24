package com.pt.myeeg.request;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.database.InfoHandler;

/**
 * Created by Jorge Zepeda Tinoco on 22/12/17.
 */

public class ContentRequestManager {

    private Context mContext;

    public ContentRequestManager(Context context){
        this.mContext = context;
    }

    public void requestDoLogIn(String email, String password, final OnDoLogInListener onDoLogInListener){
        DoLogInTask mDoLogInTask = new DoLogInTask(mContext, email, password);

        mDoLogInTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                Log.i("DoLogIn2: ","login:" + result);
                onDoLogInListener.onDoLogInLoaded((String)result);
            }
        });

        mDoLogInTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onDoLogInListener.onDoLogInError((String)result);
            }
        });

        try {
            mDoLogInTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestSingUpPatient(Paciente patient, final OnSingUpPatientListener onSingUpPatientListener){
        DoSingUpTask mDoSingUpTask = new DoSingUpTask(mContext, patient);

        mDoSingUpTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                Log.i("DoSingUp2: ","singup:" + result);
                onSingUpPatientListener.onSingUpPatientLoaded((String)result);
            }
        });

        mDoSingUpTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onSingUpPatientListener.onSingUpPatientError((String)result);
            }
        });

        try {
            mDoSingUpTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestGetPatientSchedules(final OnGetPatientSchedulesListener onGetPatientSchedulesListener){

        Paciente paciente = new InfoHandler(mContext).getPatientInfo();
        GetPatientSchedules mPatientSchedulesTask = new GetPatientSchedules(mContext, paciente);


        mPatientSchedulesTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onGetPatientSchedulesListener.onGetPatientSchedulesLoaded((String)result);
            }
        });

        mPatientSchedulesTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onGetPatientSchedulesListener.onGetPatientSchedulesError((String)result);
            }
        });

        try {
            mPatientSchedulesTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestGetSpetialistData(final OnGetSpetialistDataListener onGetSpetialistDataListener){

        Especialista especialista = new InfoHandler(mContext).getSpetialistInfo();
        GetSpetialistDataTask mSpetialistDataTask = new GetSpetialistDataTask(mContext, especialista);


        mSpetialistDataTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onGetSpetialistDataListener.onGetSpetialistDataLoaded((String)result);
            }
        });

        mSpetialistDataTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onGetSpetialistDataListener.onGetSpetialistDataError((String)result);
            }
        });

        try {
            mSpetialistDataTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestGetPatientsBySpetialist(final OnGetPatientsBySpetialistListener onGetPatientsBySpetialistListener){

        Especialista especialista = new InfoHandler(mContext).getSpetialistInfo();
        GetPatientsBySpetialistTask mPatientsBySpetialistTask = new GetPatientsBySpetialistTask(mContext, especialista);


        mPatientsBySpetialistTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onGetPatientsBySpetialistListener.onGetPatientsBySpetialistLoaded((String)result);
            }
        });

        mPatientsBySpetialistTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onGetPatientsBySpetialistListener.onGetPatientsBySpetialistError((String)result);
            }
        });

        try {
            mPatientsBySpetialistTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void requestGetSpetialistSchedules(final OnGetSpetialistSchedulesListener onGetSpetialistSchedulesListener){

        Especialista especialista = new InfoHandler(mContext).getSpetialistInfo();
        GetSpetialistSchedulesTask mSpetialistScheduleTask = new GetSpetialistSchedulesTask(mContext, especialista);


        mSpetialistScheduleTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onGetSpetialistSchedulesListener.onGetSpetialistSchedulesLoaded((String)result);
            }
        });

        mSpetialistScheduleTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onGetSpetialistSchedulesListener.onGetSpetialistSchedulesError((String)result);
            }
        });

        try {
            mSpetialistScheduleTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestGetDevices(final OnGetDevicesListener onGetDevicesListener){

        Paciente paciente = new InfoHandler(mContext).getPatientInfo();
        GetDevicesTask mDevicesTask = new GetDevicesTask(mContext, paciente);


        mDevicesTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onGetDevicesListener.onGetDevicesLoaded((String)result);
            }
        });

        mDevicesTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onGetDevicesListener.onGetDevicesError((String)result);
            }
        });

        try {
            mDevicesTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestScheduleAppointment(Cita schedule, final OnScheduleAppointmentListener onScheduleAppointmentListener){

        ScheduleAppointmentTask mScheduleAppointment = new ScheduleAppointmentTask(mContext, schedule);


        mScheduleAppointment.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onScheduleAppointmentListener.onScheduleAppointmentLoaded((String)result);
            }
        });

        mScheduleAppointment.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onScheduleAppointmentListener.onScheduleAppointmentError((String)result);
            }
        });

        try {
            mScheduleAppointment.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestGetGeneralResults(int scheduleId, final OnGetGeneralResultsListener onGetGeneralResultsListener) {
        GetGeneralResultTask mGeneralResultsTask = new GetGeneralResultTask(mContext, scheduleId);


        mGeneralResultsTask.setOnRequestSuccess(new AbstractRequestTask.OnRequestListenerSuccess() {
            @Override
            public void onSuccess(Object result) {
                onGetGeneralResultsListener.onGetGeneralresultsLoaded((String)result);
            }
        });

        mGeneralResultsTask.setOnRequestFailed(new AbstractRequestTask.OnRequestListenerFailed() {
            @Override
            public void onFailed(Object result) {
                onGetGeneralResultsListener.onGetGeneralresultsError((String)result);
            }
        });

        try {
            mGeneralResultsTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnDoLogInListener{
        void onDoLogInLoaded(String result);
        void onDoLogInError(String throwable);
    }

    public interface OnSingUpPatientListener{
        void onSingUpPatientLoaded(String result);
        void onSingUpPatientError(String throwable);
    }

    public interface OnGetSpetialistDataListener{
        void onGetSpetialistDataLoaded(String result);
        void onGetSpetialistDataError(String throwable);
    }

    public interface OnGetPatientsBySpetialistListener{
        void onGetPatientsBySpetialistLoaded(String result);
        void onGetPatientsBySpetialistError(String throwable);
    }

    public interface OnGetSpetialistSchedulesListener{
        void onGetSpetialistSchedulesLoaded(String result);
        void onGetSpetialistSchedulesError(String throwable);
    }

    public interface OnGetPatientSchedulesListener{
        void onGetPatientSchedulesLoaded(String result);
        void onGetPatientSchedulesError(String throwable);
    }

    public interface OnGetDevicesListener{
        void onGetDevicesLoaded(String result);
        void onGetDevicesError(String throwable);
    }

    public interface  OnScheduleAppointmentListener{
        void onScheduleAppointmentLoaded(String result);
        void onScheduleAppointmentError(String throwable);
    }

    public interface OnGetGeneralResultsListener {
        void onGetGeneralresultsLoaded(String result);
        void onGetGeneralresultsError(String throwable);
    }
}
