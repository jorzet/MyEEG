package com.pt.myeeg.services.webservice;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.Usuario;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jorge Zepeda Tinoco on 13/08/17.
 */

public class MetadataInfo {

    //public static final String URL = "http://148.204.86.36:8084/WSEEG/terminalproject/electroencephalography"; // SEPI UPIITA network
    //public static final String URL = "http://10.7.6.207:8084/WSEEG/terminalproject/electroencephalography";// AMCO network
    public static final String URL = "http://192.168.1.67:8084/EEG_Final_1/terminalproject/electroencephalography"; // Home network
    //public static final String URL = "http://192.168.43.74:8084/EEG_Final_1/terminalproject/electroencephalography"; // Smartphone network

    public static final String SING_IN = "/singin/";
    public static final String SING_UP_PATIENT = "/singuppatient/";
    public static final String SING_UP_SPETIALIST = "/singupspetialist/";
    public static final String GET_PATIENT_DATA = "/getpatientdata/";
    public static final String GET_SPETIALIST_DATA = "/getspetialistdata/";
    public static final String GET_ALL_SPETIALIST = "/getallspetialist";
    public static final String GET_PATIENTS_BY_SPETIALIST = "/getpatientsbyspetialist/";
    public static final String GET_PATIENT_SCHEDULE = "/getpatientschedule/";
    public static final String GET_PATIENT_SCHEDULES = "/getpatientschedules/";
    public static final String GET_SPETIALIST_SCHEDULES = "/getallspetialistschedules/";
    public static final String GET_STUDY_BY_PATIENT = "/getstudybypatient/";
    public static final String GET_DEVICES_BY_PATIENT = "/getdevicesbypatient/";
    public static final String RESTART_PASSWORD = "/getrestorepassword/";
    public static final String APPOINTMENT_SCHEDULE = "/scheduleappointment/";
    public static final String GET_GENERAL_RESULTS = "/getgeneralresultsbyschedule/";
    public static final String GET_SEGMENT_RESULTS_BY_SECOND = "/getresultsegmentbysecond/";
    public static final String GET_SEGMENT_RESULTS_BY_INTERVAL = "/getresultsegmentbyinterval/";

    public static final String UPDATE_SPETIALIST_DATA = "/requestupdatespetialistdata/";
    public static final String UPDATE_PATIENT_DATA = "/requestupdatepatientdata/";

    public static String requestLogin(String url, Context context){
        /*TODO obtain the hash password*/
        if(HttpRequest.isConnected(context))
            return HttpRequest.sendGetRequest(url);
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;

    }

    public static String requestSingupPatient(String url, Context context){
        if(HttpRequest.isConnected(context))
            return HttpRequest.sendGetRequest(url);
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
                JSONBuilder.buildSingupJson(spetialist));
    }

    public static String requestGetPatientData(String url, Context context){
        if(HttpRequest.isConnected(context))
            return HttpRequest.sendGetRequest(url);
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

    public static String requestGetSpetialistData(String url, Context context){
        if(HttpRequest.isConnected(context))
            return HttpRequest.sendGetRequest(url);
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

    public String requestGetAllSpetialist(){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_ALL_SPETIALIST);
    }

    public static String requestGetPatientsBySpetialist(String url){
        return HttpRequest.sendGetRequest(url);
    }

    public String requestGetPatientSchedule(int idPatient, int idSchedule){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_PATIENT_SCHEDULE + JSONBuilder.buildPatientScheduleJson(idPatient,idSchedule));
    }

    public static String requestGetPatientSchedules(String url){
        return HttpRequest.sendGetRequest(url);
    }

    public static String requestGetSpetialistSchedules(String url){
        return HttpRequest.sendGetRequest(url);
    }

    public String requestGetStudyByPatient(int idPatient, int idSchedule){
        return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.GET_PATIENT_SCHEDULES + JSONBuilder.buildPatientScheduleJson(idPatient,idSchedule));
    }

    public static String requestGetDevicesByPatient(String url){
        Log.d("MyTAG:","url: "+ url);
        return HttpRequest.sendGetRequest(url);
    }

    public static String requestAppointmentSchdule(String url, Context context){
        if(HttpRequest.isConnected(context)) {
            Log.d("Appointment","sendGetrequest");
            return HttpRequest.sendGetRequest(url);
        }
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;

    }

    public static String requestGetGeneralResults(String url, Context context) {
        if(HttpRequest.isConnected(context)) {
            Log.d("Generalresults","sendGetrequest");
            return HttpRequest.sendGetRequest(url);
        }
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

    public static String requestGetSegmentResults(String url, Context context) {
        if(HttpRequest.isConnected(context)) {
            Log.d("SegmentResults","sendGetrequest");
            return HttpRequest.sendGetRequest(url);
        }
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

    public String requestUpdateSpetialistData(Especialista spetialist, Context context) {
        if(HttpRequest.isConnected(context)) {
            Log.d("SegmentResults","sendGetrequest");
            try {
                return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.UPDATE_SPETIALIST_DATA + java.net.URLEncoder.encode(JSONBuilder.buildJsonFromObject(spetialist),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

    public String requestUpdatePatientData(Paciente patient, Context context) {
        if(HttpRequest.isConnected(context)) {
            Log.d("SegmentResults","sendGetrequest");
            try {
                return HttpRequest.sendGetRequest(MetadataInfo.URL + MetadataInfo.UPDATE_PATIENT_DATA + java.net.URLEncoder.encode(JSONBuilder.buildJsonFromObject(patient),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;

        }
        else
            return Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED;
    }

}
