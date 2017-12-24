package com.pt.myeeg.models;

/**
 * @author Jorge Zepeda Tinoco
 * @version 1.0
 * @created 02-Jul-2017 1:24:04 PM
 */
public class Usuario {

    protected int idUsuario;
    protected String tipoUsuario;
    protected String name;
    protected String fistLastName;
    protected String secondLastName;
    protected String email;
    protected String password;
    protected String gender;
    protected byte [] profilePhoto;

    public Usuario(){}

    public void finalize() throws Throwable {
    }

    public int getId(){
        return this.idUsuario;
    }

    public String getTipoUsuario(){
        return this.tipoUsuario;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFirstLastName(){
        return this.fistLastName;
    }

    public String getSecondLastName(){
        return this.secondLastName;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public byte [] getPrifilePhoto(){
        return this.profilePhoto;
    }

    public String getGender(){
        return this.gender;
    }


    /**
     *
     * @param idUsuario
     */
    public void setId(int idUsuario){
        this.idUsuario = idUsuario;
    }
    /**
     *
     * @param tipoUsuario
     */
    public void setTipoUsuario(String tipoUsuario){
        this.tipoUsuario = tipoUsuario;
    }
    /**
     *
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     *
     * @param fistLastName
     */
    public void setFistLastName(String fistLastName){
        this.fistLastName = fistLastName;
    }

    /**
     *
     * @param secondLastName
     */
    public void setSecondLastName(String secondLastName){
        this.secondLastName = secondLastName;
    }
    /**
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     *
     * @param profilePhoto
     */
    public void setProfilePhoto(byte [] profilePhoto){
        this.profilePhoto = profilePhoto;
    }

    /**
     *
     * @param gender
     */
    public void setGender(String gender){
        this.gender=gender;
    }
}//end Usuario