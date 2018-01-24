package com.pt.myeeg.services.bluetoothservice;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.pt.myeeg.models.Cita;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.Usuario;
import com.pt.myeeg.services.database.InfoHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Jorge Zepeda Tinoco on 22/08/17.
 */

public class BluetoothService {
    private static final String TAG = BluetoothService.class.getName();

    private static final int ERROR_FROM_DATA =              -1;
    public static final int DATA_SUCESSFULLY_SENDED =      0x00;
    private static final int CLIENT_NOT_ENABLE =            0x01;
    public static final int ERROR_IN_SENDING =             0x02;
    public static final int CODE_CONNECTION_SUCCESSFULLY =  0x03;
    public static final int CODE_ERROR_CONNECTION =         0x04;
    public static final int CODE_TESTING_ERROR =            0x05;

    public static final String CONNECTION_SUCCESSFULLY = "connection_successfully";
    private static final String TESTING_ERROR =          "connection_device_error";

    /* To chech the bluetooth adapter status  */
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int BLUETOOTH_ADAPTER_NOT_AVAILABLE = -1;
    public static final int BLUETOOTH_ADAPTER_TURNED_ON = 1;
    public static final int BLUETOOTH_ADAPTER_TURNED_OFF = 0;
    public int currentBluetoothState;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice = null;

    private UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");

    private static final String  CAN_NOT_BE_LINKED = "No se pudó conectar a el dispositivo BT";
    private static final String ERROR_IN_SENDING_USER_INFO = "Error, no se pudó enviar la información de usuario";
    private static final String ERROR_IN_SENDING_STATUS = "Error, no se pudo iniciar la grabacion";

    private Context mContext;

    private String mJsonTestingConnectionError;
    private String mLinkedError;
    private String mErrorInSending;

    public interface IBluetoothListener {
        void onDeviceConnected();
        void onBluetoothReciver();
    }

    public BluetoothService(Context context, String MacAddress){
        this.mContext = context;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mmDevice = mBluetoothAdapter.getRemoteDevice(MacAddress);
    }



    public int getBluetoothStatus(){
        if (mBluetoothAdapter == null){
            return BluetoothService.BLUETOOTH_ADAPTER_NOT_AVAILABLE;
        }else{
            if (mBluetoothAdapter.isEnabled()){
                return BluetoothService.BLUETOOTH_ADAPTER_TURNED_ON;
            }else{
                return BluetoothService.BLUETOOTH_ADAPTER_TURNED_OFF;
            }
        }
    }


    public static boolean isBluetoothHeadsetConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
                && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }

    public BluetoothAdapter isAvailable(){
        return mBluetoothAdapter;
    }

    public void disableBluetooth(){
        mBluetoothAdapter.disable();
    }

    public void enableBluetooth(){
        mBluetoothAdapter.enable();
    }

    public boolean isEnabled(){
        return mBluetoothAdapter.isEnabled();
    }

    public boolean isDiscovering(){
        return mBluetoothAdapter.isDiscovering();
    }

    //public int sendUserData(){
    //    return BluetoothService.DATA_SUCESSFULLY_SENDED;
    //}

    public long getCurrentCountDown(){
        return 10L;
    }

    private void setJsonTestingConnectionError(String json){
        this.mJsonTestingConnectionError = json;
    }

    public String getJsonTestingConnectionError(){
        return this.mJsonTestingConnectionError;
    }

    public void setCanNotConnectError(String error){
        this.mLinkedError = error;
    }

    public String getCanNotConnectError(){
        return this.mLinkedError;
    }

    public void setErrorInSending(String error){
        this.mErrorInSending = error;
    }

    public String getErrorInSending(){
        return this.mErrorInSending;
    }

    public int connect() {
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            if (!mmSocket.isConnected()){
                Log.d(TAG,"conectando.... " + mmDevice.getName() + "--" + mmDevice.getAddress());
                mmSocket.connect();
            }
            Log.d(TAG,"conectado ");
            return CODE_CONNECTION_SUCCESSFULLY;
        } catch (IOException | NullPointerException e) {
            Log.d(TAG,"error de conexion ");
            e.printStackTrace();
            setCanNotConnectError(CAN_NOT_BE_LINKED);
        }
        return CODE_ERROR_CONNECTION;
    }

    public void disconnect(){
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int sendData(String status){
        try {

            Log.d(TAG,"status: "+ status);
            OutputStream mmOutputStream = mmSocket.getOutputStream();
            mmOutputStream.write(status.getBytes());
            return BluetoothService.DATA_SUCESSFULLY_SENDED;


        } catch (IOException e) {
            e.printStackTrace();
            //setErrorInSendingStatus(ERROR_IN_SENDING_STATUS);
            return ERROR_IN_SENDING;
        }
    }

    public int sendUserData(){
        try {
            InputStream mmInputStream = mmSocket.getInputStream();
            int bytesAvailable = mmInputStream.available();
            Log.d(TAG,"bytesAvailable: "+ bytesAvailable);
            if(bytesAvailable > 0) {
                byte[] packetBytes = new byte[bytesAvailable];
                mmInputStream.read(packetBytes);
                String data = new String(packetBytes, "US-ASCII");
                Log.d(TAG,"datos: "+ data);
                if(data.contains(TESTING_ERROR)) {
                    Log.d(TAG,"jsonError: "+ data);
                    setJsonTestingConnectionError(Palabras.TESTING_BLUETOOTH_ERROR);
                    return CODE_TESTING_ERROR;
                } else if (data.contains(CONNECTION_SUCCESSFULLY)) {
                    // sends user data
                    Log.d(TAG,"jsonSuccess: "+ data);
                    OutputStream mmOutputStream = mmSocket.getOutputStream();
                    mmOutputStream.write(getUserInformation().getBytes());

                    // get devices connectivity
                    byte[] packetTestBytes = new byte[bytesAvailable];
                    mmInputStream.read(packetTestBytes);
                    String testMessage = new String(packetTestBytes, "US-ASCII");

                    // check response
                    if(testMessage.contains(TESTING_ERROR)) {
                        setJsonTestingConnectionError(Palabras.TESTING_BLUETOOTH_ERROR);
                        return BluetoothService.CODE_TESTING_ERROR;
                    } else if (testMessage.contains(CONNECTION_SUCCESSFULLY)) {
                        return BluetoothService.DATA_SUCESSFULLY_SENDED;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            setErrorInSending(ERROR_IN_SENDING_USER_INFO);
        }
        return ERROR_IN_SENDING;
    }

    public String readData(){
        try {
            InputStream mmInputStream = mmSocket.getInputStream();
            int bytesAvailable = mmInputStream.available();
            if(bytesAvailable > 0) {
                byte[] packetBytes = new byte[bytesAvailable];
                mmInputStream.read(packetBytes);
                return new String(packetBytes, "US-ASCII");
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getUserInformation(){
        InfoHandler ih = new InfoHandler(mContext);
        Paciente paciente = ih.getPatientInfo();
        Cita cita = ih.getCurrentScheduele();

        String jsonDevices = ih.getPatientDevicesJson();
        ArrayList<Dispositivo> dispositivos = ih.getPatientDevices(jsonDevices,Dispositivo.class);
        String[] devices = cita.getElectrodos();

        int idPatient = paciente.getId();
        int scheduleId = cita.getFolioCita();
        String duration = cita.getDuracion();
        String date = cita.getFecha();


        JSONObject json = new JSONObject();

        JSONArray channelArray = new JSONArray();
        JSONArray macAddressArray = new JSONArray();

        for(int i = 0; i < dispositivos.size(); i++){
            for(int j = 0; j < devices.length; j++){
                if(!dispositivos.get(i).getDeviceName().equals("raspberry") &&
                        dispositivos.get(i).getDeviceName().equals(devices[j])) {
                    JSONObject jsonChannels = new JSONObject();
                    JSONObject jsonMacs = new JSONObject();
                    try {
                        jsonChannels.put("channel", dispositivos.get(i).getDeviceName());
                        jsonMacs.put("mac", dispositivos.get(i).getDeviceMacAddress());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    channelArray.put(jsonChannels);
                    macAddressArray.put(jsonMacs);
                }
            }
        }


        /*for(int i=0;i<dispositivos.size();i++){

            if(!dispositivos.get(i).getDeviceName().equals("raspberry") &&
                    dispositivos.get(i).getDeviceName().equals(devices[i])) {
                JSONObject jsonChannels = new JSONObject();
                JSONObject jsonMacs = new JSONObject();
                try {
                    jsonChannels.put("channel", dispositivos.get(i).getDeviceName());
                    jsonMacs.put("mac", dispositivos.get(i).getDeviceMacAddress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                channelArray.put(jsonChannels);
                macAddressArray.put(jsonMacs);
            }
        }*/

        try {
            json.put(Palabras.ID_PATIENT,idPatient);
            json.put(Palabras.ID_SCHEDULE,scheduleId);
            json.put(Palabras.DURATION,duration);
            json.put(Palabras.DATE,date);
            json.put(Palabras.CHANNELS,channelArray);
            json.put(Palabras.MAC_ADDRESS,macAddressArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("bluetoothJson: "+json.toString());

        return json.toString();
    }



}
