package com.pt.myeeg.services.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.webservice.JSONBuilder;

/**
 * Created by Jorge Zepeda Tinoco on 17/08/17.
 */

public class DataBase extends SQLiteOpenHelper{
    Context mContext;

    /* To build the DB */
    private static final String DATABASE_NAME = "PTDB";
    private static final SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;
    private static final int DATABASE_VERSION = 1;

    /* Table names */
    private static final String TABLE_USER = "Usuario";
    private static final String TABLE_PATIENT = "Paciente";
    private static final String TABLE_SPETIALIST = "Especialista";
    private static final String TABLE_SCHEDULE = "Cita";
    private static final String TABLE_STUDY = "Estudio";
    private static final String TABLE_DEVICE = "Dispositivo";
    private static final String TABLE_CURRENT_SCHEDULE = "CitaActual";

    /* Columns name */
    private static final String COL_ID_PATIENT = "idPaciente";
    private static final String COL_ID_SPETIALIST = "idEspecialista";
    private static final String COL_NAME = "name";
    private static final String COL_FIRST_LASTNAME = "firstLastName";
    private static final String COL_SECOND_LASTNAME = "secondLastName";
    private static final String COL_ILLNESS = "illness";
    private static final String COL_AGE = "age";
    private static final String COL_EMAIL = "email";
    private static final String COL_GENDER = "gender";
    private static final String COL_ID_DEVICE = "idDevice";
    private static final String COL_DEVICE_NAME = "deviceName";
    private static final String COL_DEVICE_MAC = "deviceMAc";

    /* SQL instructions */
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private static final String PATIENT_ATRIBUTS = "("+COL_ID_PATIENT + " integer primary key," +
                                                    COL_ID_SPETIALIST + " integer," +
                                                    COL_NAME + " text," +
                                                    COL_FIRST_LASTNAME + " text," +
                                                    COL_SECOND_LASTNAME + " text," +
                                                    COL_ILLNESS + " text" +
                                                    COL_AGE + " integer," +
                                                    COL_EMAIL + " text," +
                                                    COL_GENDER + " text); ";
    private static final String SPETIALIST_ATRIBUTS = "idPaciente integer primary key," +
                                                    COL_NAME + " text," +
                                                    COL_FIRST_LASTNAME + " text," +
                                                    COL_SECOND_LASTNAME + " text," +
                                                    COL_AGE + " integer," +
                                                    COL_EMAIL + " text," +
                                                    COL_GENDER + " text); ";
    private static final String DEVICE_ATRIBUTS = COL_ID_DEVICE+" integer primary key," +
                                                    COL_ID_PATIENT + " text," +
                                                    COL_DEVICE_NAME + " text," +
                                                    COL_DEVICE_MAC + " text); ";

    public DataBase(Context context) {
        super(context, DataBase.DATABASE_NAME, DataBase.DATABASE_FACTORY, DataBase.DATABASE_VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("MyTAG","onCreate");
        db.execSQL(
                DataBase.CREATE_TABLE + DataBase.TABLE_PATIENT + DataBase.PATIENT_ATRIBUTS
                //CREATE_TABLE + TABLE_SPETIALIST + SPETIALIST_ATRIBUTS
        );
        Log.i("MyTAG","onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("MyTAG: ","onUpgrade");
        db.execSQL("DROP TABLE " + DataBase.TABLE_PATIENT + ";");
        onCreate(db);
        Log.i("MyTAG: ","onUpgrade");
    }

    public boolean removePatient(){

        return true;
    }

    public boolean storePatient(Paciente patient){
        Log.i("MyTAG ","storePatient");
        try{
        SQLiteDatabase db = this.getWritableDatabase();
        /*String sql =
                "INSERT INTO "+TABLE_PATIENT+
                        " ("+COL_ID_PATIENT+","+COL_ID_SPETIALIST+","+COL_NAME+","+COL_FIRST_LASTNAME+","+COL_SECOND_LASTNAME+","+COL_ILLNESS+","+COL_AGE+","+COL_EMAIL+","+COL_GENDER+") " +
                        "VALUES("+patient.getId()+","+patient.getEspecialista().getId()+","+patient.getName()+","+patient.getFirstLastName()+","+patient.getSecondLastName()+","+patient.getPadecimiento()+","+patient.getAge()+","+patient.getEmail()+","+patient.getGender()+")" ;
        db.execSQL(sql);
        */
            Log.i("MyTAG", "Table Name=> "+patient.getId());
            Log.i("MyTAG", "Table Name=> "+patient.getEspecialista().getId());
            Log.i("MyTAG", "Table Name=> "+patient.getName());
            Log.i("MyTAG", "Table Name=> "+patient.getFirstLastName());
            Log.i("MyTAG", "Table Name=> "+patient.getSecondLastName());
            Log.i("MyTAG", "Table Name=> "+patient.getPadecimiento());
            Log.i("MyTAG", "Table Name=> "+patient.getAge());
            Log.i("MyTAG", "Table Name=> "+patient.getEmail());
            Log.i("MyTAG", "Table Name=> "+patient.getGender());
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_NAME,"Hola");
            /*contentValues.put(DataBase.COL_ID_PATIENT, patient.getId());
            contentValues.put(DataBase.COL_ID_SPETIALIST, patient.getEspecialista().getId());
            contentValues.put(DataBase.COL_NAME, patient.getName());
            contentValues.put(DataBase.COL_FIRST_LASTNAME, patient.getFirstLastName());
            contentValues.put(DataBase.COL_SECOND_LASTNAME, patient.getSecondLastName());
            contentValues.put(DataBase.COL_ILLNESS, patient.getPadecimiento());
            contentValues.put(DataBase.COL_AGE, patient.getAge());
            contentValues.put(DataBase.COL_EMAIL, patient.getEmail());
            contentValues.put(DataBase.COL_GENDER, patient.getGender());*/
            long i =db.insert(DataBase.TABLE_PATIENT, null, contentValues);
            Log.i("MyTAG: ","result "+i);

        }
        catch (Exception e) {
            Log.i("MyTAG", "ERROR "+e.toString());
        }
        Log.i("MyTAG ","storePatient");
        return true; // just when all lines executed well
    }

    public void storeJSONPatient(String json){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TABLE_PATIENT, json);
        editor.apply();
    }

    public void storeDevices(String json){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TABLE_DEVICE, json);
        editor.apply();
    }

    public void storeJSONUser(String json){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TABLE_USER, json);
        editor.apply();
    }

    public void storeJSONPatientSchedules(String json){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TABLE_SCHEDULE, json);
        editor.apply();
    }

    public Paciente getPatient(){
        Log.i("MyTAG: ","getPatient");
        Paciente patient = new Paciente();
        Especialista spetialist = new Especialista();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM Paciente",null);

        if (cursor.moveToFirst()) {
            Log.i("MyTag", COL_ID_PATIENT + cursor.getString(1));
            Log.i("MyTag", COL_ID_SPETIALIST + cursor.getString(2));
            Log.i("MyTag", COL_NAME + cursor.getString(3));
            /*cursor.moveToFirst();
            patient.setId(Integer.parseInt(cursor.getString(0)));
            spetialist.setId(Integer.parseInt(cursor.getString(1)));
            patient.setEspecialista(spetialist);
            patient.setName(cursor.getString(2));
            patient.setFistLastName(cursor.getString(3));
            patient.setSecondLastName(cursor.getString(4));
            patient.setPadecimiento(cursor.getString(5));
            patient.setAge(Integer.parseInt(cursor.getString(6)));
            patient.setEmail(cursor.getString(7));
            patient.setGender(cursor.getString(8));
            */
            cursor.close();
        } else {
            patient = null;
        }
        db.close();
        return patient;
    }

    public String getJsonPatient(){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TABLE_PATIENT, null);
    }

    public String getJsonUser(){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TABLE_USER, null);
    }

    public String getJsonPatientSchedules(){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TABLE_SCHEDULE, null);
    }

    public String getJsonPatientDevices(){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TABLE_DEVICE, null);
    }

    public  void saveReference(String json, String TAG){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TAG, json);
        editor.apply();
    }

    public String getReference(String TAG, Class clase){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Log.i("MyTAG: ","get TAG: " +TAG + " json reference: " + prefs.getString(TAG, null));
        return prefs.getString(TAG, null);
    }

    public void saveExtra(String TAG, String param){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TAG, param);
        editor.apply();
    }

    public String getExtra(String TAG){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TAG, null);
    }

    public void saveJsonCurrentSchedule(String param){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TABLE_CURRENT_SCHEDULE, param);
        editor.apply();
    }

    public String getJsonCurrentSchedule(){
        SharedPreferences prefs = mContext.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TABLE_CURRENT_SCHEDULE, null);
    }

}
