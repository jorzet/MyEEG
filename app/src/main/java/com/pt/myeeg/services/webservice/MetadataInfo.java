package com.pt.myeeg.services.webservice;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.Usuario;
/**
 * Created by Jorge Zepeda Tinoco on 13/08/17.
 */

public class MetadataInfo {

    //public static final String URL = "http://148.204.86.36:8084/WSEEG/terminalproject/electroencephalography"; // SEPI UPIITA network
    //public static final String URL = "http://10.7.6.207:8084/WSEEG/terminalproject/electroencephalography";// AMCO network
    public static final String URL = "http://192.168.1.77:8084/EEG_Final_1/terminalproject/electroencephalography"; // Home network
    //public static final String URL = "http://192.168.43.74:8084/EEG_Final_1/terminalproject/electroencephalography"; // Smartphone network

    private static final String SING_IN = "/singin/";
    private static final String SING_UP_PATIENT = "/singuppatient/";
    private static final String SING_UP_SPETIALIST = "/singupspetialist/";
    private static final String GET_PATIENT_DATA = "/getpatientdata/";
    private static final String GET_SPETIALIST_DATA = "/getspetialistdata/";
    private static final String GET_ALL_SPETIALIST = "/getallspetialist";
    private static final String GET_PATIENTS_BY_SPETIALIST = "/getpatientsbyspetialist/";
    private static final String GET_PATIENT_SCHEDULE = "/getpatientschedule/";
    private static final String GET_PATIENT_SCHEDULES = "/getpatientschedules/";
    private static final String GET_SPETIALIST_SCHEDULES = "/getallspetialistschedules/";
    private static final String GET_STUDY_BY_PATIENT = "/getstudybypatient/";
    private static final String GET_DEVICES_BY_PATIENT = "/getdevicesbypatient/";
    private static final String RESTART_PASSWORD = "/getrestorepassword/";

    public String requestLogin(String email, String hashPassword, Context context){
        /*TODO obtain the hash password*/
        if(HttpRequest.isConnected(context))
            return HttpRequest.sendGetRequest(MetadataInfo.URL +
                    MetadataInfo.SING_IN +
                    JSONBuilder.bildLoginJson(email,hashPassword));
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;

    }

    public String requestSingupPatient(Usuario user, Context context){
        if(HttpRequest.isConnected(context)) {
            String response = HttpRequest.sendGetRequest(MetadataInfo.URL +
                    MetadataInfo.SING_UP_PATIENT +
                    JSONBuilder.bildSingupJson(user));
            if(response!=null && response.equals(Palabras.SUCESSFULL_SINGUP)){
                return HttpRequest.sendGetRequest(MetadataInfo.URL +
                        MetadataInfo.SING_IN +
                        JSONBuilder.bildLoginJson(user.getEmail(), user.getPassword()));
            }else
                return response;
        }
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;

    }

    public String requestGetRestorePassword(String email){
        return HttpRequest.sendGetRequest(MetadataInfo.URL +
                MetadataInfo.RESTART_PASSWORD + email);
    }

    public String requestSingupSpetialist(Especialista spetialist){
        return HttpRequest.sendGetRequest(MetadataInfo.URL +
                MetadataInfo.SING_UP_SPETIALIST +
                JSONBuilder.bildSingupJson(spetialist));
    }

    public String requestGetPatientData(int idPatient, Context context){
        return HttpRequest.sendGetRequest(MetadataInfo.URL +
                MetadataInfo.GET_PATIENT_DATA +
                idPatient);
    }

    public String requestGetSpetialistData(int idSpetialist, Context context){
        if(HttpRequest.isConnected(context))
            return HttpRequest.sendGetRequest(MetadataInfo.URL +
                    MetadataInfo.GET_SPETIALIST_DATA +
                    idSpetialist);
        else
        return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

    public String requestGetAllSpetialist(){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_ALL_SPETIALIST);
    }

    public String requestGetPatientsBySpetialist(int idSpetialist){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_PATIENTS_BY_SPETIALIST + idSpetialist);
    }

    public String requestGetPatientSchedule(int idPatient, int idSchedule){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_PATIENT_SCHEDULE + JSONBuilder.buildPatientScheduleJson(idPatient,idSchedule));
    }

    public String requestGetPatientSchedules(int idPatient){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_PATIENT_SCHEDULES + idPatient);
    }

    public String requestGetSpetialistSchedules(int isSpetialist){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_SPETIALIST_SCHEDULES + isSpetialist);
    }

    public String requestGetStudyByPatient(int idPatient, int idSchedule){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_PATIENT_SCHEDULES + JSONBuilder.buildPatientScheduleJson(idPatient,idSchedule));
    }

    public String requestGetDevicesByPatient(int idPatient){
        Log.d("MyTAG:","url: "+ MetadataInfo.URL + MetadataInfo.GET_DEVICES_BY_PATIENT + idPatient);
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_DEVICES_BY_PATIENT + idPatient);
    }
}
