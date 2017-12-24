package com.pt.myeeg.models;

/**
 * Created by Jorge Zepeda Tinoco on 13/08/17.
 */

public class Palabras {

    public static final int ERROR_INCORRECT_PASSWORD =                  0x00;
    public static final int WITHOUT_ACOUNT =                              -1;
    public static final int PATIENT_USER =                              0x01;
    public static final int SPETIALIST_USER =                           0x02;
    public static final int ADMINISTRATOR_USER =                        0x03;

    public static final int CODE_ERROR_NOT_SPETIALISTS =                0x04;
    public static final int CODE_ERROR_NOT_PATIENTS =                   0x05;
    public static final int CODE_ERROR_NOT_SCHEDULES =                  0x06;
    public static final int CODE_ERROR_FROM_JSON =                      0x07;
    public static final int CODE_SYNTAX_ERROR_FROM_JSON =               0x08;
    public static final int CODE_ERROR_HAPPENED_A_PROBLEM =             0x09;
    public static final int CODE_ERROR_SPETIALIST_NOT_EXISTS =          0x0A;
    public static final int CODE_ERROR_PATIENT_NOT_EXISTS =             0x0B;
    public static final int CODE_ERROR_USER_NOT_EXISTS =                0x0B;
    public static final int CODE_ERROR_SCHEDULE_NOT_EXISTS =            0x0D;
    public static final int CODE_ERROR_STUDY_NOT_EXISTS =               0x0E;
    public static final int CODE_ERROR_INCORRECT_EMAIL_OR_PASSWORD =    0x0F;

    public static final int CODE_SUCESSFULL_SING_IN =                   0x10;
    public static final int CODE_SUCESSFULL_SCHEDULE_APOINTMENT =       0x11;
    public static final int CODE_SUCESSFULL_STORE_RECORDING =           0x12;
    public static final int CODE_SUCESSFULL_STORE_GENERAL_RESULTS=      0x13;
    public static final int CODE_SUCESSFULL_STORE_CHANEL_RESULTS =      0x14;
    public static final int CODE_SUCESSFULL_STORE_SEGMENT_RESULTS =     0x15;

    /* log in */
    public static final String SUCESSFULL_LOG_IN = "sucesfull_log_in";
    public static final String ERROR_INCORRECT_EMAIL_OR_PASSWORD = "Error, el usuario y/o contraseña son invalidos";
    public static final String ERROR_EMTY_USER_AND_PASSWORD = "Error, por favor ingresa el usuario y contraseña";

    /* sing up*/
    public static final String SUCESSFULL_SINGUP = "Usuario registrado correctamente";
    public static final String ERROR_EMTY_INPUTS = "Error, por favor llena todos los campos";
    public static final String ERROR_PASSWORDS_NOT_MATCH = "Error, las contraseñas no coinciden";
    public static final String ERROR_GENDER_NOT_CHECKED = "Error, selecciona tu genero";

    /* restrt password */
    public static final String ERROR_EMTY_EMAIL = "Error, por favor ingresa tu email";

    public static final String ERROR_FROM_WEB_WERVICE = "Error al obtener información del servidor, vuelve a intentarlo más tarde";
    public static final String ERROR_FROM_NETWORK_NOT_CONNECTED = "Error, Conexión a internet no disponible";

    public static final String SPETIALIST_SUGGESTIONS = "spetialist_suggestion";
    public static final String SCHEDULE_POSITION = "schedule_position";
    public static final String USER = "User";
    public static final String ID_USER = "idUsuario";
    public static final String TOKEN = "Token";
    public static final String PATIENT_TYPE = "Paciente";
    public static final String SPETIALIST_TYPE = "Especialista";

    public static final String FP1 = "fp1";
    public static final String FP2 = "fp2";
    public static final String G = "g";
    public static final String F3 = "f3";
    public static final String F4 = "f4";
    public static final String F7 = "f7";
    public static final String F8 = "f8";
    public static final String C3 = "c3";
    public static final String C4 = "c4";
    public static final String T3 = "t3";
    public static final String T4 = "t4";
    public static final String A1 = "a1";
    public static final String A2 = "a2";
    public static final String P3 = "p3";
    public static final String P4 = "p4";
    public static final String T5 = "t5";
    public static final String T6 = "t6";
    public static final String O1 = "o1";
    public static final String O2 = "o2";


    public static final String ID_PATIENT = "id_patient";
    public static final String ID_SCHEDULE = "id_schedule";
    public static final String DURATION = "duration";
    public static final String DATE = "date";
    public static final String CHANNELS = "channels";
    public static final String MAC_ADDRESS = "mac_address";
    public static final String CURRENT_SCHEDULE = "current_schedule";


    /* to schedule an appointment */
    public static final String OK = "OK";
    public static final String SELECT_A_PATIENT = "Seleccione un paciente";
    public static final String WITHOUT_PATIENTS= "Sin pacientes";
    public static final String WITHOUT_OBSERVATIONS = "Sin observaciones";

}
