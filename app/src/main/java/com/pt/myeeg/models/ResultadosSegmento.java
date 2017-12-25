/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pt.myeeg.models;
/**
 *
 * @author Jorge Zepeda Tinoco
 */
public class ResultadosSegmento {
    private int idResultadosSegmento;
    private Grabacion grabacion;
    private int segundo;
    private String canal;
    private float frecuenciaDominante;
    private String tipoOnda;
    private String senal;
    private double[] doubleSignal;

    public ResultadosSegmento(){}

    public int getIdResultadosSegmento(){
        return this.idResultadosSegmento;
    }

    public Grabacion getGrabacion(){
        return this.grabacion;
    }

    public int getSegundo(){
        return this.segundo;
    }

    public String getCanal(){
        return this.canal;
    }

    public float getFrecuenciaDominante(){
        return this.frecuenciaDominante;
    }

    public String getTipoOnda(){
        return this.tipoOnda;
    }

    public String getSenal(){
        return this.senal;
    }

    public double[] getDoubleSignal() {
        String[] doubles = senal.split(",");
        doubleSignal = new double[doubles.length];
        for(int i = 0;i < doubles.length; i++) {
            doubleSignal[i] = Double.parseDouble(doubles[i]);
        }
        return doubleSignal;
    }

    /**
     *
     * @param idResultadosSegmento
     */
    public void setIdResultadosSegmento(int idResultadosSegmento){
        this.idResultadosSegmento = idResultadosSegmento;
    }

    /**
     *
     * @param grabacion
     */
    public void setGrabacion(Grabacion grabacion){
        this.grabacion = grabacion;
    }

    /**
     *
     * @param segundo
     */
    public void setSegundo(int segundo){
        this.segundo = segundo;
    }

    /**
     *
     * @param canal
     */
    public void setCanal(String canal){
        this.canal = canal;
    }

    /**
     *
     * @param frecuenciaDominante
     */
    public void setFrecuenciaDominante(float frecuenciaDominante){
        this.frecuenciaDominante = frecuenciaDominante;
    }

    /**
     *
     * @param tipoOnda
     */
    public void setTipoOnda(String tipoOnda){
        this.tipoOnda = tipoOnda;
    }

    /**
     *
     * @param senal
     */
    public void setSenal(String senal){
        this.senal = senal;
    }

}
