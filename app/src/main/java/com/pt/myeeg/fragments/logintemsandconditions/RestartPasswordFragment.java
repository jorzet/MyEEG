package com.pt.myeeg.fragments.logintemsandconditions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge Zepeda Tinoco on 7/2/2017.
 * jorzet.94@gmail.com
 */

public class RestartPasswordFragment extends Fragment{

    /* For the View */
    private EditText mEmail;
    private Button mRestartPassword;
    private TextView mErrorRestartPassword;
    private TextView mSucessEmailSended;
    private ImageView mLogo;
    private ProgressBar mProgressBar;
    private View mForgotView;

    /* For the SaveInstanceState */
    private static final String EMAIL_USER_FORGOT = "email_user_forgot";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.forgot_fragment,container,false);
        mEmail = (EditText) rootView.findViewById(R.id.email_user_forgot);
        mRestartPassword = (Button) rootView.findViewById(R.id.restart_password_button);
        mErrorRestartPassword = (TextView) rootView.findViewById(R.id.error_forgot);
        mSucessEmailSended = (TextView) rootView.findViewById(R.id.sucess_send);
        mLogo = (ImageView) rootView.findViewById(R.id.imgLogo);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.forgot_progress);
        mForgotView = (View) rootView.findViewById(R.id.restart_password_view);

        mRestartPassword.setOnClickListener(sendEmailAction);
        return rootView;
    }

    private View.OnClickListener sendEmailAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendEmail();
        }
    };

    private void sendEmail(){
        String email = mEmail.getText().toString();
        if(!email.equals("")){
            mErrorRestartPassword.setText("");

            //TODO
            new SendRequestRestorePassword().execute(email);
            mForgotView.setVisibility(View.GONE);
            mLogo.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);

        }
        else
            mErrorRestartPassword.setText(Palabras.ERROR_EMTY_EMAIL);
    }

    private class SendRequestRestorePassword extends AsyncTask<String, Long, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            System.out.println("email: " + email + "-------------------------------");
            String res = new MetadataInfo().requestGetRestorePassword(email);
            return res;
        }

        protected void onPostExecute(String response) {
            if (response == null || response.equals("")) {
                mErrorRestartPassword.setText(Palabras.ERROR_FROM_WEB_WERVICE);
                mProgressBar.setVisibility(View.GONE);
            } else if (response.equals(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED)) {
                mErrorRestartPassword.setText(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
                mProgressBar.setVisibility(View.GONE);
            } else if (response.contains("Error") && response.contains("Message")) {
                try {
                    JSONObject json = new JSONObject(response);
                    int codeError = json.getInt("Error");
                    String message = json.getString("Message");

                    mErrorRestartPassword.setText(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);
            } else if (JSONBuilder.checkJsonStructure(response)) {
                System.out.println("response: " + response);
                mProgressBar.setVisibility(View.GONE);
                mSucessEmailSended.setText(response);
                mSucessEmailSended.setVisibility(View.VISIBLE);
            }
        }
    }

}
