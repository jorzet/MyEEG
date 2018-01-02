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
public class ResultadosCanal {
    private int idResultadosCanal;
    private Grabacion grabacion;
    private String canal;
    private String tipoOndaDominanteCanal;
    private float frecuenciaDominanteCanal;
    private float promedioAmplitudesCanal;
    private float porcentajeAparicionRitmoAlpha;
    private float porcentajeAparicionRitmoBeta;
    private float porcentajeAparicionRitmoDelta;
    private float porcentajeAparicionRitmoTheta;
    private float porcentajeAparicionFrecuenciaAlpha;
    private float porcentajeAparicionFrecuenciaBeta;
    private float porcentajeAparicionFrecuenciaDelta;
    private float porcentajeAparicionFrecuenciaTheta;
    private float promedioAmplitudesRitmoAlpha;
    private float promedioAmplitudesRitmoBeta;
    private float promedioAmplitudesRitmoDelta;
    private float promedioAmplitudesRitmoTheta;
    private float promedioAmplitudesFrecuenciaAlpha;
    private float promedioAmplitudesFrecuenciaBeta;
    private float promedioAmplitudesFrecuenciaDelta;
    private float promedioAmplitudesFrecuenciaTheta;
    private boolean anormal;

    public ResultadosCanal(){}

    public int getIdResultadosCanal(){
        return this.idResultadosCanal;
    }

    public Grabacion getGrabacion(){
        return this.grabacion;
    }

    public String getCanal(){
        return this.canal;
    }

    public String getTipoOndaDominanteCanal(){
        return this.tipoOndaDominanteCanal;
    }

    public float getFrecuenciaDominanteCanal(){
        return this.frecuenciaDominanteCanal;
    }

    public float getPromedioAmplitudesCanal(){
        return this.promedioAmplitudesCanal;
    }

    public float getPorcentajeAparicionRitmoAlpha(){
        return this.porcentajeAparicionRitmoAlpha;
    }

    public float getPorcentajeAparicionRitmoBeta(){
        return this.porcentajeAparicionRitmoBeta;
    }

    public float getPorcentajeAparicionRitmoDelta(){
        return this.porcentajeAparicionRitmoDelta;
    }

    public float getPorcentajeAparicionRitmoTheta(){
        return this.porcentajeAparicionRitmoTheta;
    }

    public float getPorcentajeAparicionFrecuenciaAlpha(){
        return this.porcentajeAparicionFrecuenciaAlpha;
    }

    public float getPorcentajeAparicionFrecuenciaBeta(){
        return this.porcentajeAparicionFrecuenciaBeta;
    }

    public float getPorcentajeAparicionFrecuenciaDelta(){
        return this.porcentajeAparicionFrecuenciaDelta;
    }

    public float getPorcentajeAparicionFrecuenciaTheta(){
        return this.porcentajeAparicionFrecuenciaTheta;
    }

    public float getPromedioAmplitudesRitmoAlpha(){
        return this.promedioAmplitudesRitmoAlpha;
    }

    public float getPromedioAmplitudesRitmoBeta(){
        return this.promedioAmplitudesRitmoBeta;
    }

    public float getPromedioAmplitudesRitmoDelta(){
        return this.promedioAmplitudesRitmoDelta;
    }

    public float getPromedioAmplitudesRitmoTheta(){
        return this.promedioAmplitudesRitmoTheta;
    }

    public float getPromedioAmplitudesFrecuenciaAlpha(){
        return this.promedioAmplitudesFrecuenciaAlpha;
    }

    public float getPromedioAmplitudesFrecuenciaBeta(){
        return this.promedioAmplitudesFrecuenciaBeta;
    }

    public float getPromedioAmplitudesFrecuenciaDelta(){
        return this.promedioAmplitudesFrecuenciaDelta;
    }

    public float getPromedioAmplitudesFrecuenciaTheta(){
        return this.promedioAmplitudesFrecuenciaTheta;
    }

    public boolean IsAnormal(){
        return this.anormal;
    }

    /**
     *
     * @param idResultadosCanal
     */
    public void setIdResultadosCanal(int idResultadosCanal){
        this.idResultadosCanal = idResultadosCanal;
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
     * @param canal
     */
    public void setCanal(String canal){
        this.canal = canal;
    }

    /**
     *
     * @param tipoOndaDominanteCanal
     */
    public void setTipoOndaDominanteCanal(String tipoOndaDominanteCanal){
        this.tipoOndaDominanteCanal = tipoOndaDominanteCanal;
    }

    /**
     *
     * @param frecuenciaDominanteCanal
     */
    public void setFrecuenciaDominanteCanal(float frecuenciaDominanteCanal){
        this.frecuenciaDominanteCanal = frecuenciaDominanteCanal;
    }

    /**
     *
     * @param promedioAmplitudesCanal
     */
    public void setPromedioAmplitudesCanal(float promedioAmplitudesCanal){
        this.promedioAmplitudesCanal = promedioAmplitudesCanal;
    }

    /**
     *
     * @param porcentajeAparicionRitmoAlpha
     */
    public void setPorcentajeAparicionRitmoAlpha(float porcentajeAparicionRitmoAlpha){
        this.porcentajeAparicionRitmoAlpha = porcentajeAparicionRitmoAlpha;
    }

    /**
     *
     * @param porcentajeAparicionRitmoBeta
     */
    public void setPorcentajeAparicionRitmoBeta(float porcentajeAparicionRitmoBeta){
        this.porcentajeAparicionRitmoBeta = porcentajeAparicionRitmoBeta;
    }

    /**
     *
     * @param porcentajeAparicionRitmoDelta
     */
    public void setPorcentajeAparicionRitmoDelta(float porcentajeAparicionRitmoDelta){
        this.porcentajeAparicionRitmoDelta = porcentajeAparicionRitmoDelta;
    }

    /**
     *
     * @param porcentajeAparicionRitmoTheta
     */
    public void setPorcentajeAparicionRitmoTheta(float porcentajeAparicionRitmoTheta){
        this.porcentajeAparicionRitmoTheta = porcentajeAparicionRitmoTheta;
    }

    /**
     *
     * @param porcentajeAparicionFrecuenciaAlpha
     */
    public void setPorcentajeAparicionFrecuenciaAlpha(float porcentajeAparicionFrecuenciaAlpha){
        this.porcentajeAparicionFrecuenciaAlpha = porcentajeAparicionFrecuenciaAlpha;
    }

    /**
     *
     * @param porcentajeAparicionFrecuenciaBeta
     */
    public void setPorcentajeAparicionFrecuenciaBeta(float porcentajeAparicionFrecuenciaBeta){
        this.porcentajeAparicionFrecuenciaBeta = porcentajeAparicionFrecuenciaBeta;
    }

    /**
     *
     * @param porcentajeAparicionFrecuenciaDelta
     */
    public void setPorcentajeAparicionFrecuenciaDelta(float porcentajeAparicionFrecuenciaDelta){
        this.porcentajeAparicionFrecuenciaDelta = porcentajeAparicionFrecuenciaDelta;
    }

    /**
     *
     * @param porcentajeAparicionFrecuenciaTheta
     */
    public void setPorcentajeAparicionFrecuenciaTheta(float porcentajeAparicionFrecuenciaTheta){
        this.porcentajeAparicionFrecuenciaTheta = porcentajeAparicionFrecuenciaTheta;
    }

    /**
     *
     * @param promedioAmplitudesRitmoAlpha
     */
    public void setPromedioAmplitudesRitmoAlpha(float promedioAmplitudesRitmoAlpha){
        this.promedioAmplitudesRitmoAlpha = promedioAmplitudesRitmoAlpha;
    }

    /**
     *
     * @param promedioAmplitudesRitmoBeta
     */
    public void setPromedioAmplitudesRitmoBate(float promedioAmplitudesRitmoBeta){
        this.promedioAmplitudesRitmoBeta = promedioAmplitudesRitmoBeta;
    }

    /**
     *
     * @param promedioAmplitudesRitmoDelta
     */
    public void setPromedioAmplitudesRitmoDelta(float promedioAmplitudesRitmoDelta){
        this.promedioAmplitudesRitmoDelta = promedioAmplitudesRitmoDelta;
    }

    /**
     *
     * @param promedioAmplitudesRitmoTheta
     */
    public void setPromedioAmplitudesRitmoTheta(float promedioAmplitudesRitmoTheta){
        this.promedioAmplitudesRitmoTheta = promedioAmplitudesRitmoTheta;
    }

    /**
     *
     * @param promedioAmplitudesFrecuenciaAlpha
     */
    public void setPromedioAmplitudesFrecuenciaAlpha(float promedioAmplitudesFrecuenciaAlpha){
        this.promedioAmplitudesFrecuenciaAlpha = promedioAmplitudesFrecuenciaAlpha;
    }

    /**
     *
     * @param promedioAmplitudesFrecuenciaBeta
     */
    public void setPromedioAmplitudesFrecuenciaBeta(float promedioAmplitudesFrecuenciaBeta){
        this.promedioAmplitudesFrecuenciaBeta = promedioAmplitudesFrecuenciaBeta;
    }

    /**
     *
     * @param promedioAmplitudesFrecuenciaDelta
     */
    public void setPromedioAmplitudesFrecuenciaDelta(float promedioAmplitudesFrecuenciaDelta){
        this.promedioAmplitudesFrecuenciaDelta = promedioAmplitudesFrecuenciaDelta;
    }

    /**
     *
     * @param promedioAmplitudesFrecuenciaTheta
     */
    public void setPromedioAmplitudesFrecuenciaTheta(float promedioAmplitudesFrecuenciaTheta){
        this.promedioAmplitudesFrecuenciaTheta = promedioAmplitudesFrecuenciaTheta;
    }

    /**
     *
     * @param anormal
     */
    public void setIsAnormal(boolean anormal){
        this.anormal = anormal;
    }
}
