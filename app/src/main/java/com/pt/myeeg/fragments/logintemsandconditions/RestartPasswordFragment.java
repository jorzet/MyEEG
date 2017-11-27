package com.pt.myeeg.fragments.logintemsandconditions;

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

/**
 * Created by Jorge Zepeda Tinoco on 7/2/2017.
 */

public class RestartPasswordFragment extends Fragment{

    /* For the View */
    private EditText mEmail;
    private Button mRestartPassword;
    private TextView mErrorRestartPassword;
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

            mForgotView.setVisibility(View.GONE);
            mLogo.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);

        }
        else
            mErrorRestartPassword.setText(Palabras.ERROR_EMTY_EMAIL);
    }

}
