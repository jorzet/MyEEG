package com.pt.myeeg.services.database;

import android.content.Context;
import android.util.Log;

import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.ResultadosSegmento;
import com.pt.myeeg.models.Usuario;
import com.pt.myeeg.security.AccessToken;
import com.pt.myeeg.services.webservice.JSONBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 17/08/17.
 */

public class InfoHandler {
    Context mContext;
    public InfoHandler(Context context){
        this.mContext = context;
    }

    public void savePatientAndToken(String json){
        DataBase db = new DataBase(mContext);

        String patient = JSONBuilder.getJsonFromJson(json, Palabras.USER);
        db.storeJSONPatient(patient);

        String token = JSONBuilder.getJsonFromJson(json, Palabras.TOKEN);
        AccessToken.setAccessToken(token, mContext);
    }

    public void saveSpetialistAndToken(String json){
        DataBase db = new DataBase(mContext);

        String spetialist = JSONBuilder.getJsonFromJson(json, Palabras.USER);
        db.storeJSONSpetialist(spetialist);

        String token = JSONBuilder.getJsonFromJson(json, Palabras.TOKEN);
        AccessToken.setAccessToken(token, mContext);
    }

    public void saveDevices(String json){
        DataBase db = new DataBase(mContext);
        db.storeDevices(json);
    }

    public void saveUserAndToken(String json){
        DataBase db = new DataBase(mContext);
        Log.d("MyTAG","json: "+ json);
        String user = JSONBuilder.getJsonFromJson(json, Palabras.USER);
        Log.d("MyTAG", "saveUserAndToken: s" + user);
        db.storeJSONUser(user);

        String token = JSONBuilder.getJsonFromJson(json, Palabras.TOKEN);
        AccessToken.setAccessToken(token, mContext);
    }

    public void removePatientAndToken(){
        AccessToken.setAccessToken(null,mContext);
    }

    public void saveSpetilistInfo(String json){
        DataBase db = new DataBase(mContext);

        Especialista spetialist = (Especialista) JSONBuilder.getObjectFromJson(json, Especialista.class);


        String newJson = JSONBuilder.buildJsonFromObject(spetialist);

        db.storeJSONSpetialist(newJson);
    }

    public Paciente getPatientInfo(){
        DataBase db = new DataBase(mContext);
        Log.i("MyTAG: ",db.getJsonPatient() );
        return (Paciente) JSONBuilder.getObjectFromJson(db.getJsonPatient(), Paciente.class);
    }

    public Especialista getSpetialistInfo(){
        DataBase db = new DataBase(mContext);
        Log.i("MyTAG: ",db.getJsonSpetialist() );
        return (Especialista) JSONBuilder.getObjectFromJson(db.getJsonSpetialist(), Especialista.class);
    }

    public Usuario getUserInfo(){
        DataBase db = new DataBase(mContext);
        Log.i("MyTAG: ","getUserInfo" + db.getJsonUser() );
        return (Usuario) JSONBuilder.getObjectFromJson(db.getJsonUser(), Usuario.class);
    }

    public void savePatientSchedules(String json){
        DataBase db = new DataBase(mContext);
        db.storeJSONPatientSchedules(json);
    }

    public String getPatientSchedulesJson(){
        DataBase db = new DataBase(mContext);
        return db.getJsonPatientSchedules();
    }

    public String getPatientDevicesJson(){
        DataBase db = new DataBase(mContext);
        return db.getJsonPatientDevices();
    }

    public ArrayList<Cita> getPatientSchedules(String devices, Class clase){
        DataBase db = new DataBase(mContext);
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(devices, clase);
        ArrayList<Cita> citas = new ArrayList<>();
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++)
                citas.add((Cita) objects.get(i));
            return citas;
        }
        return null;
    }

    public void savePatiensSpetialist(String jsonPatients){
        DataBase db = new DataBase(mContext);
        db.storeJsonPatientsSpetialist(jsonPatients);
    }

    public ArrayList<Paciente> getPatientsSpetialist(){
        DataBase db = new DataBase(mContext);
        String patientsJson = db.getJsonPatientsSpetialist();
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(patientsJson, Paciente.class);
        ArrayList<Paciente> patients = new ArrayList<>();
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++)
                patients.add((Paciente) objects.get(i));
            return patients;
        }
        return null;
    }

    public ArrayList<ResultadosSegmento> getSegmetResultsArrayList(){
        DataBase db = new DataBase(mContext);
        String patientsJson = db.getSegmentResults();
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(patientsJson, ResultadosSegmento.class);
        ArrayList<ResultadosSegmento> segmentResults = new ArrayList<>();
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++)
                segmentResults.add((ResultadosSegmento) objects.get(i));
            return segmentResults;
        }
        return null;
    }


    public int getPatientsCount(){
        DataBase db = new DataBase(mContext);
        String patientsJson = db.getJsonPatientsSpetialist();
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(patientsJson, Paciente.class);
        return objects.size();
    }

    public int getSpetialistSchedulesCount(){
        DataBase db = new DataBase(mContext);
        String schedules = db.getJsonPatientSchedules();
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(schedules, Cita.class);
        return objects.size();
    }

    public int getPatientSchedulesCount(){
        DataBase db = new DataBase(mContext);
        String schedules = db.getJsonPatientSchedules();
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(schedules, Cita.class);
        return objects.size();
    }

    public String getLastPatientSchedule(){
        DataBase db = new DataBase(mContext);
        String schedules = db.getJsonPatientSchedules();
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(schedules, Cita.class);
        return ((Cita)objects.get(objects.size()-1)).getFecha();
    }

    public ArrayList<Dispositivo> getPatientDevices(String schedules, Class clase){
        DataBase db = new DataBase(mContext);
        ArrayList<Object> objects = JSONBuilder.getArrayListFromJsonArray(schedules, clase);
        ArrayList<Dispositivo> dispositivos = new ArrayList<>();
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++)
                dispositivos.add((Dispositivo) objects.get(i));
            return dispositivos;
        }
        return null;
    }

    public Cita getPatientSchedule(int idSchedule){
        Cita c = new Cita();
        String savedSchedules = getPatientSchedulesJson();
        ArrayList<Cita> citas = getPatientSchedules(savedSchedules, Cita.class);
        if (citas != null) {
            for (int i = 0; i <= idSchedule; i++) {
                c = citas.get(i);
            }
        }
        return c;
    }

    public void saveReferceObject(Object object){
        DataBase db = new DataBase(mContext);
        Log.i("MyTAG: ","object name 1: " + object.getClass().toString());
        String json = JSONBuilder.buildObjectReferenceJson(object, object.getClass().toString());
        Log.i("MyTAG: ","json reference: " + json + " object: " + object);
        db.saveReference(json, object.getClass().toString());
    }

    public void saveCurrentSchedule(Cita cita){
        DataBase db = new DataBase(mContext);
        System.out.println("beforeUildJson: ");
        String json = JSONBuilder.buildObjectReferenceJson(cita);
        System.out.println("jsonGenerated: "+json);
        db.saveJsonCurrentSchedule(json);
    }

    public Cita getCurrentScheduele(){
        DataBase db = new DataBase(mContext);
        String json = db.getJsonCurrentSchedule();
        System.out.println("currentSchedule: "+json);
        return (Cita)JSONBuilder.getObjectFromJson(json,Cita.class);
    }

    public Object getReferenceObject(Class clase){
        DataBase db = new DataBase(mContext);
        Log.i("MyTAG: ","object name 2: " + clase.toString());
        String json = db.getReference(clase.toString(), clase);
        Object object = JSONBuilder.getObjectReferenceFromJson(json, clase.toString());
        Log.i("MyTAG: ","object: " + object);
        return object;
    }

    /* Those methods are why the app needs to store the view values
     * when the user opens the app from the notification
     * it produces a null pointer exception */
    public void saveExtraFromActivity(String TAG, String param){
        DataBase db = new DataBase(mContext);
        db.saveExtra(TAG, param);
    }
    public String getExtraStored(String TAG){
        DataBase db = new DataBase(mContext);
        return db.getExtra(TAG);
    }

    public void saveIsMedic(boolean isMedic){
        DataBase db = new DataBase(mContext);
        db.saveIsMedic(isMedic);
    }

    public boolean getIsMedic(){
        DataBase db = new DataBase(mContext);
        return db.getIsMedic();
    }

    public void saveGeneralResults(String result) {
        DataBase db = new DataBase(mContext);
        db.saveGeneralResults(result);
    }

    public String getJsonGeneralResults() {
        DataBase db = new DataBase(mContext);
        return db.getGeneralResults();
    }

    public void saveSegmentResults(String result) {
        DataBase db = new DataBase(mContext);
        db.saveSegmentResults(result);
    }

    public String getJsonSegmentResults() {
        DataBase db = new DataBase(mContext);
        return db.getSegmentResults();
    }

}
