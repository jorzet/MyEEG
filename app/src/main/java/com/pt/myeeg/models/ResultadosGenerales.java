package com.pt.myeeg.models;

/**
 *
 * @author Jorge Zepeda Tinoco
 * jorzet.94@gmail.com
 *
 */

public class ResultadosGenerales {
    private int idResultadosGenerales;
    private Grabacion grabacion;
    private Cita cita;
    private String zonaCerebral;
    private String tipoOndaDominante;
    private double porcentajeTipoOnda;

    public ResultadosGenerales(){}

    public int getIdResultadosGenerales(){
        return this.idResultadosGenerales;
    }

    public Grabacion getGrabacion(){
        return this.grabacion;
    }

    public Cita getCita(){
        return this.cita;
    }

    public String getZonaCerebral(){
        return this.zonaCerebral;
    }

    public String getTipoOndaDominante(){
        return this.tipoOndaDominante;
    }

    public double getPorcentajeTipoOnda(){
        return this.porcentajeTipoOnda;
    }

    /**
     *
     * @param idResultadosGenerales
     */
    public void setIdResultadosGenerales(int idResultadosGenerales){
        this.idResultadosGenerales = idResultadosGenerales;
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
     * @param cita
     */
    public void setCita(Cita cita){
        this.cita = cita;
    }

    /**
     *
     * @param zonaCerebral
     */
    public void setZonaCerebral(String zonaCerebral){
        this.zonaCerebral = zonaCerebral;
    }

    /**
     *
     * @param tipoOndaDominante
     */
    public void setTipoOndaDominate(String tipoOndaDominante) {
        this.tipoOndaDominante = tipoOndaDominante;
    }

    /**
     *
     * @param porcentajeTipoOnda
     */
    public void setPorcentajeTipoOnda(double porcentajeTipoOnda) {
        this.porcentajeTipoOnda = porcentajeTipoOnda;
    }
}