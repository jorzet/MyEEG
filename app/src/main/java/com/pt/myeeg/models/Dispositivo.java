package com.pt.myeeg.models;

/**
 * Created by Fernando Hernandez Molina on 20/11/2017.
 */

public class Dispositivo {
    int deviceId;
    Paciente patient;
    String deviceName;
    String deviceMacAddress;

    public Dispositivo(){
    }

    public int getId(){
        return this.deviceId;
    }

    public Paciente getPatient(){
        return this.patient;
    }

    public String getDeviceName(){
        return this.deviceName;
    }

    public String getDeviceMacAddress(){
        return this.deviceMacAddress;
    }

    public void setId(int id){
        this.deviceId = id;
    }

    public void setPatient(Paciente patient){
        this.patient = patient;
    }

    public void setDeviceName(String name){
        this.deviceName = name;
    }

    public void setDeviceMacAddres(String mac){
        this.deviceMacAddress = mac;
    }
}
