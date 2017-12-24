package com.pt.myeeg.fragments.logintemsandconditions;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.models.Dispositivo;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.Usuario;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;
import com.pt.myeeg.ui.activities.ContentActivity;
import com.pt.myeeg.ui.activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jorge Zepeda Tinoco on 7/2/2017.
 */

public class SinginFragment extends BaseContentFragment implements View.OnClickListener{

    /* For the View */
    private Button mLogin;
    //private Button mSingUp;
    private TextView mRestartPassword;
    private EditText mEmail;
    private EditText mPassword;
    private TextView mErrorLogin;
    private ProgressBar mProgressBar;
    private View mLoginContent;

    /* For the SaveInstanceState */
    private static final String EMAIL_TEXT = "email_text";
    private static final String PASSWORD_TEXT = "password_text";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null)
            return null;
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this.getContext());
        View rootView = inflater.inflate(R.layout.signin_fragment,container,false);

        mLogin = (Button) rootView.findViewById(R.id.singin_button);
        //mSingUp = (Button) rootView.findViewById(R.id.link_to_signup);
        mRestartPassword = (TextView) rootView.findViewById(R.id.link_to_forgot);
        mEmail = (EditText) rootView.findViewById(R.id.email_user_singin);
        mPassword = (EditText) rootView.findViewById(R.id.password_user_singin);
        mErrorLogin = (TextView) rootView.findViewById(R.id.error_login);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.login_progress);
        mLoginContent = (View) rootView.findViewById(R.id.login_content);

        mLogin.setOnClickListener(this);
        //mSingUp.setOnClickListener(this);
        mRestartPassword.setOnClickListener(this);

        if(savedInstanceState!=null){
            mEmail.setText(savedInstanceState.getString(EMAIL_TEXT));
            mPassword.setText(savedInstanceState.getString(PASSWORD_TEXT));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        savedInstanceState.putString(EMAIL_TEXT,email);
        savedInstanceState.putString(PASSWORD_TEXT,password);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.singin_button:
                doLogIn();
                break;
            /*case R.id.link_to_signup:
                goSingupFragment();
                break;*/
            case R.id.link_to_forgot:
                goForgotFragment();
                break;
        }
    }

    private void doLogIn() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if(!email.equals("") && !password.equals("")) {
            mErrorLogin.setText("");
            requestDoLogIn(email,password);
            //new DoLogIn().execute(email, password);
            mLoginContent.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else
            mErrorLogin.setText(Palabras.ERROR_EMTY_USER_AND_PASSWORD);
    }

    private void goHomeActivity() {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void goSingupFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SingupFragment())
                .addToBackStack(LoginActivity.TAG)
                .commit();

    }
    private void goForgotFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RestartPasswordFragment())
                .addToBackStack(LoginActivity.TAG)
                .commit();
    }




    /********************************************************************************************/
    /********************************************************************************************/
    /********************************************************************************************/


    @Override
    public void onDoLogInSuccess(String response) {
        super.onDoLogInSuccess(response);
        Log.i("MyTAG: ","login:" + response);

        new InfoHandler(getContext()).saveUserAndToken(response);
        Usuario user = new InfoHandler(getContext()).getUserInfo();
        if(user != null){
            InfoHandler ih = new InfoHandler(getContext());
            if(user.getTipoUsuario().equals(Palabras.SPETIALIST_TYPE)) {
                ih.saveSpetialistAndToken(response);
                ih.saveIsMedic(true);
                requestGetSpetialistData();
                //new GetSpetialistData().execute();

            } else {
                ih.savePatientAndToken(response);
                ih.saveIsMedic(false);
                requestGetPatientSchedules();
                //new GetPatientSchedules().execute();
            }
        } else {
            mErrorLogin.setText(Palabras.ERROR_FROM_WEB_WERVICE);
            mProgressBar.setVisibility(View.GONE);
            mLoginContent.setVisibility(View.VISIBLE);
            new InfoHandler(getContext()).removePatientAndToken();
        }
    }

    @Override
    public void onDoLogInFail(String response) {
        super.onDoLogInFail(response);
        showErrorMessage(response);
    }

    @Override
    public void onGetSpetialistDataSuccess(String response) {
        super.onGetSpetialistDataSuccess(response);
        Log.i("MyTAG: ", response);
        new InfoHandler(getContext()).saveSpetilistInfo(response);
        requestGetPatientsBySpetialist();
        //new GetPatientsBySpetialist().execute();
    }

    @Override
    public void onGetSpetialistDataFail(String response) {
        super.onGetSpetialistDataFail(response);
        showErrorMessage(response);
    }

    @Override
    public void onGetSpetialistSchedulesSuccess(String response) {
        super.onGetSpetialistSchedulesSuccess(response);
        new InfoHandler(getContext()).savePatientSchedules(response);
        goHomeActivity();
    }

    @Override
    public void onGetSpetialistSchedulesFail(String response) {
        super.onGetSpetialistSchedulesFail(response);
        showErrorMessage(response);
    }

    @Override
    public void onGetPatientsBySpetialistSuccess(String response) {
        super.onGetPatientsBySpetialistSuccess(response);
        new InfoHandler(getContext()).savePatiensSpetialist(response);
        requestGetSpetialistSchedules();
        //new GetSpetialistSchedules().execute();
    }

    @Override
    public void onGetPatientsBySpetialistFail(String response) {
        super.onGetPatientsBySpetialistFail(response);
        showErrorMessage(response);
    }

    @Override
    public void onGetPatientSchedulesSuccess(String response) {
        super.onGetPatientSchedulesSuccess(response);
        new InfoHandler(getContext()).savePatientSchedules(response);
        requestGetDevices();
        //new GetDevices().execute();
    }

    @Override
    public void onGetPatientSchedulesFail(String response) {
        super.onGetPatientSchedulesFail(response);
        showErrorMessage(response);
    }

    @Override
    public void onGetDevicesSuccess(String response) {
        super.onGetDevicesSuccess(response);

        System.out.println("response: "+response);
        new InfoHandler(getContext()).saveDevices(response);
        String savedDevices = new InfoHandler(getContext()).getPatientDevicesJson();
        ArrayList<Dispositivo> dispositivos = new InfoHandler(getContext()).getPatientDevices(savedDevices, Dispositivo.class);
        if(dispositivos != null)
            System.out.println("tamaño de arreglo: "+dispositivos.size());
        else
            System.out.println("tamaño de arreglo: "+0);
        goHomeActivity();
    }

    @Override
    public void onGetDevicesFail(String response) {
        super.onGetDevicesFail(response);
        showErrorMessage(response);
    }


    private void showErrorMessage(String response) {
        if(response==null || response.equals("")) {
            mErrorLogin.setText(Palabras.ERROR_FROM_WEB_WERVICE);
            mProgressBar.setVisibility(View.GONE);
            mLoginContent.setVisibility(View.VISIBLE);
        }
        else if(response.equals(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED)) {
            mErrorLogin.setText(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
            mProgressBar.setVisibility(View.GONE);
            mLoginContent.setVisibility(View.VISIBLE);
        }
        else if(response.contains("Error") && response.contains("Message")) {
            try {
                JSONObject json = new JSONObject(response);
                int codeError = json.getInt("Error");
                String message = json.getString("Message");

                mErrorLogin.setText(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressBar.setVisibility(View.GONE);
            mLoginContent.setVisibility(View.VISIBLE);
        }
    }
}
