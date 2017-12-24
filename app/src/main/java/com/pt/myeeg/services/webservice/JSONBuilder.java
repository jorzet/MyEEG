package com.pt.myeeg.services.webservice;

import android.content.Intent;
import android.os.CountDownTimer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Zepeda Tinoco on 13/08/17.
 */

public class JSONBuilder {

    // SPETIAL CHARA
    private static final char OPEN_JSON = '{';
    private static final char CLOSE_JSON = '}';
    private static final char DOUBLE_QUOTES = '"';

    public static boolean checkJsonStructure(String json){
        return true;
        /*Stack pila = new Stack();
        // check if the general structure corresponds to a json object
        for(int i=0;i<json.length();i++){
            if(json.charAt(i)== JSONBuilder.OPEN_JSON)
                pila.add(JSONBuilder.OPEN_JSON);
            else if(json.charAt(i)== JSONBuilder.DOUBLE_QUOTES)
                pila.add(JSONBuilder.DOUBLE_QUOTES);
            else if(json.charAt(i) == JSONBuilder.CLOSE_JSON)
                pila.remove(pila.size()-1);
        }
        return pila.isEmpty();*/
    }

    public static String buildLoginJson(String email, String password){
        JSONObject json = new JSONObject();
        try {
            json.put("email",email);
            json.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString().replace("{","%7B").replace("}","%7D");
    }

    public static String buildPatientScheduleJson(int idPatient, int idSchedule){
        JSONObject json = new JSONObject();
        try {
            json.put("idPaciente",idPatient);
            json.put("folioCita",idSchedule);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString().replace("{","%7B").replace("}","%7D");
    }

    public static String buildSingupJson(Object object){
        return new Gson().toJson(object).replace("{","%7B").replace("}","%7D");
    }

    public static String buildJsonFromObject(Object object){
        return new Gson().toJson(object);
    }

    public static Object getObjectFromJson(String json, Class clase){
        return new Gson().fromJson(json, clase);
    }

    public static String getJsonFromJson(String json, String KEY ){
        JSONObject object = null;
        try {
            object = new JSONObject(json);
            return object.getString(KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getIntFromJson(String json, String KEY ){
        JSONObject object = null;
        try {
            object = new JSONObject(json);
            return object.getInt(KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static ArrayList<Object> getArrayListFromJsonArray(String json, Class clase){
        ArrayList<Object> objects = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                objects.add(JSONBuilder.getObjectFromJson(jsonArray.getJSONObject(i).toString(), clase));
            }
            return objects;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Intent> getListFromJson(String json, String KEY){
        try {
            JSONObject object = new JSONObject(json);
            JsonElement element = (JsonElement) object.get(KEY);
            Type listType = new TypeToken<List<Integer>>() {}.getType();
            return new Gson().fromJson(element , listType);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildObjectReferenceJson(Object object){
        return new Gson().toJson(object);
    }

    public static String buildObjectReferenceJson(Object object, String KEY){
        return new Gson().toJson(object);
        /*JSONObject json = new JSONObject();
        try {
            json.put(KEY,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();*/
    }

    public static Object getObjectReferenceFromJson(String json, String KEY){
        JSONObject object = null;
        try {
            object = new JSONObject(json);
            Object obj = object.get(KEY);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e){
            return null;
        }
    }
}
