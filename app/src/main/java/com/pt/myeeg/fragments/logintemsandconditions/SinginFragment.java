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

public class SinginFragment extends Fragment implements View.OnClickListener{

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
            new DoLogIn().execute(email, password);
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

    private class DoLogIn extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... data) {
            return new MetadataInfo().requestLogin(data[0], data[1], getContext());
        }

        protected void onPostExecute(String response) {

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
            else if(JSONBuilder.checkJsonStructure(response)) {
                Log.i("MyTAG: ","login:" + response);

                new InfoHandler(getContext()).saveUserAndToken(response);
                Usuario user = new InfoHandler(getContext()).getUserInfo();
                if(user != null){
                    InfoHandler ih = new InfoHandler(getContext());
                    if(user.getTipoUsuario().equals(Palabras.SPETIALIST_TYPE)) {
                        ih.saveSpetialistAndToken(response);
                        ih.saveIsMedic(true);
                        new GetSpetialistData().execute();

                    } else {
                        ih.savePatientAndToken(response);
                        ih.saveIsMedic(false);
                        new GetPatientSchedules().execute();
                    }
                } else {
                    mErrorLogin.setText(Palabras.ERROR_FROM_WEB_WERVICE);
                    mProgressBar.setVisibility(View.GONE);
                    mLoginContent.setVisibility(View.VISIBLE);
                    new InfoHandler(getContext()).removePatientAndToken();
                }
            }
        }
    }


    /********************************************************************************************/
    /********************************************************************************************/
    /********************************************************************************************/



    private class GetSpetialistData extends AsyncTask<Integer, Long, String>{

        @Override
        protected String doInBackground(Integer... params) {
            Especialista especialista = new InfoHandler(getContext()).getSpetialistInfo();
            return new MetadataInfo().requestGetSpetialistData(especialista.getId(), getContext());
        }

        protected void onPostExecute(String response) {

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
            else if(JSONBuilder.checkJsonStructure(response)) {
                Log.i("MyTAG: ", response);
                new InfoHandler(getContext()).saveSpetilistInfo(response);
                new GetPatientsBySpetialist().execute();
            }
        }
    }

    private class GetPatientsBySpetialist extends AsyncTask<Integer, Long, String> {

        protected String doInBackground(Integer... params) {
            Especialista spetialist = new InfoHandler(getContext()).getSpetialistInfo();
            String res = new MetadataInfo().requestGetPatientsBySpetialist(spetialist.getId());
            return res;
        }

        protected void onPostExecute(String response) {
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
            else if(JSONBuilder.checkJsonStructure(response)) {
                new InfoHandler(getContext()).savePatiensSpetialist(response);
                new GetSpetialistSchedules().execute();
            }
        }
    }


    private class GetSpetialistSchedules extends AsyncTask<Integer, Long, String> {

        protected String doInBackground(Integer... params) {
            Especialista spetialist = new InfoHandler(getContext()).getSpetialistInfo();
            String res = new MetadataInfo().requestGetSpetialistSchedules(spetialist.getId());
            return res;
        }

        protected void onPostExecute(String response) {
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
            else if(JSONBuilder.checkJsonStructure(response)) {
                new InfoHandler(getContext()).savePatientSchedules(response);
                goHomeActivity();
            }
        }
    }



    /********************************************************************************************/
    /********************************************************************************************/
    /********************************************************************************************/
    /********************************************************************************************/


    private class GetPatientSchedules extends AsyncTask<Integer, Long, String> {

        protected String doInBackground(Integer... params) {
            Paciente paciente = new InfoHandler(getContext()).getPatientInfo();
            String res = new MetadataInfo().requestGetPatientSchedules(paciente.getId());
            return res;
        }

        protected void onPostExecute(String response) {
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
            else if(JSONBuilder.checkJsonStructure(response)) {
                new InfoHandler(getContext()).savePatientSchedules(response);
                new GetDevices().execute();
            }
        }
    }

    private class GetDevices extends AsyncTask<Integer, Long, String> {

        protected String doInBackground(Integer... params) {

            Paciente paciente = new InfoHandler(getContext()).getPatientInfo();
            System.out.println("id: "+paciente.getId()+"-------------------------------");
            String res = new MetadataInfo().requestGetDevicesByPatient(paciente.getId());
            return res;
        }

        protected void onPostExecute(String response) {
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
            else if(JSONBuilder.checkJsonStructure(response)) {
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
        }
    }
}
