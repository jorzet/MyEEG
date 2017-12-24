package com.pt.myeeg.fragments.logintemsandconditions;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.fragments.content.BaseContentFragment;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.models.Usuario;
import com.pt.myeeg.security.Hash;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.services.webservice.MetadataInfo;
import com.pt.myeeg.ui.activities.ContentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**
 * Created by Jorge Zepeda Tinoco on 08/07/17.
 */

public class SingupFragment extends BaseContentFragment implements View.OnClickListener{

    /* For the View */
    private EditText mName;
    private EditText mFirstLastName;
    private EditText mSecondLastName;
    private EditText mAge;
    private EditText mIllness;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirmation;
    private CheckBox isMale;
    private CheckBox isFemale;
    private Button mSingup;

    private TextView mErrorSingup;
    private ImageView mBack;
    private ProgressBar mProgressBar;
    private View mInputSingup;

    /* For the SaveInstanceState */
    private static final String NAME_USER_SINGUP = "name_user_singup";
    private static final String LASTNAME_USER_SINGUP = "lastname_user_singup";
    private static final String EMAIL_USER_SINGUP = "email_user_singup";
    private static final String PASSWORD_USER_SINGUP = "password_user_singup";
    private static final String PASSWORD_CONFIRMATION_USER_SINGUP = "password_confirmation_user_singup";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.signup_fragment,container,false);

        mName = (EditText) rootView.findViewById(R.id.name_user_singup);
        mFirstLastName = (EditText) rootView.findViewById(R.id.fistlastname_user_singup);
        mSecondLastName = (EditText) rootView.findViewById(R.id.secondlastname_user_singup);
        mAge = (EditText) rootView.findViewById(R.id.age_user_singup);
        mIllness = (EditText) rootView.findViewById(R.id.illness_user_singup);
        mEmail = (EditText) rootView.findViewById(R.id.email_user_singup);
        mPassword = (EditText) rootView.findViewById(R.id.password_user_singup);
        mPasswordConfirmation = (EditText) rootView.findViewById(R.id.password_confirmation_user_singup);
        isMale = (CheckBox) rootView.findViewById(R.id.is_male);
        isFemale = (CheckBox) rootView.findViewById(R.id.is_female);
        mSingup = (Button) rootView.findViewById(R.id.singup_button);

        mErrorSingup = (TextView) rootView.findViewById(R.id.error_singup);
        mBack = (ImageView) rootView.findViewById(R.id.back_action);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.singup_progress);
        mInputSingup = (View) rootView.findViewById(R.id.inputsWrapper);

        mSingup.setOnClickListener(this);
        isMale.setOnClickListener(this);
        isFemale.setOnClickListener(this);

        mBack.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.is_male:
                isMale.setChecked(true);
                isFemale.setChecked(false);
                break;
            case R.id.is_female:
                isFemale.setChecked(true);
                isMale.setChecked(false);
                break;
            case R.id.singup_button:
                doSingUp();
                break;
            case R.id.back_action:
                goBackActivity(false);
                break;
        }

    }

    private void doSingUp(){
        String name = mName.getText().toString();
        String fistLastNames = mFirstLastName.getText().toString();
        String secondLastName = mSecondLastName.getText().toString();
        String age = mAge.getText().toString();
        String illnes = mIllness.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConf = mPasswordConfirmation.getText().toString();
        String gender = (isMale.isChecked())? (Palabras.MALE_GENDER):(Palabras.FEMALE_GENDER);

        if(!name.equals("") && !fistLastNames.equals("") && !secondLastName.equals("") &&
                !age.equals("") && !illnes.equals("") && !email.equals("") && !password.equals("") &&
                !passwordConf.equals("") && (isMale.isChecked() || isFemale.isChecked())){
            if(password.equals(passwordConf)) {
                mErrorSingup.setText("");
                Paciente patient = new Paciente();
                Especialista spetialist = new InfoHandler(getContext()).getSpetialistInfo();
                patient.setEspecialista(spetialist);
                patient.setName(name);
                patient.setFistLastName(fistLastNames);
                patient.setSecondLastName(secondLastName);
                patient.setAge(Integer.parseInt(age));
                patient.setPadecimiento(illnes);
                patient.setEmail(email);
                patient.setPassword(password);
                patient.setGender(gender);
                Drawable d = getResources().getDrawable(R.drawable.ic_profile);
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] profilePhoto = stream.toByteArray();
                patient.setProfilePhoto(profilePhoto);

                requestSingUpPatient(patient);
                //new SIngUp().execute(name, fistLastNames, secondLastName, age, illnes, email, password, gender);
                mInputSingup.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }else
                mErrorSingup.setText(Palabras.ERROR_PASSWORDS_NOT_MATCH);
        }
        else{
            mErrorSingup.setText(Palabras.ERROR_EMTY_INPUTS);
        }


    }

    private void goBackActivity(boolean singupSuccess){
        InfoHandler ih = new InfoHandler(getContext());
        if(singupSuccess) {
            ih.saveExtraFromActivity(Palabras.SINGUP, Palabras.SUCESSFULL_SINGUP);
            ih.saveExtraFromActivity(Palabras.FROM_SINGUP_FRAGMENT_SUCCESS, "true");
        } else {
            ih.saveExtraFromActivity(Palabras.FROM_SINGUP_FRAGMENT_SUCCESS, "false");
        }
        getActivity().onBackPressed();
    }

    private void showErrorMessage(String response) {
        if(response==null || response.equals("")) {
            mErrorSingup.setText(Palabras.ERROR_FROM_WEB_WERVICE);
            mProgressBar.setVisibility(View.GONE);
            mInputSingup.setVisibility(View.VISIBLE);
        }
        else if(response.equals(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED)) {
            mErrorSingup.setText(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
            mProgressBar.setVisibility(View.GONE);
            mInputSingup.setVisibility(View.VISIBLE);
        }
        else if(response.contains("Error") && response.contains("Message")) {
            try {
                JSONObject json = new JSONObject(response);
                int codeError = json.getInt("Error");
                String message = json.getString("Message");

                mErrorSingup.setText(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressBar.setVisibility(View.GONE);
            mInputSingup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSingUpPatientSuccess(String response) {
        super.onSingUpPatientSuccess(response);

        new InfoHandler(getContext()).savePatientSchedules(response);
        goBackActivity(true);
    }

    @Override
    public void onSingUpPatientFail(String response) {
        super.onSingUpPatientFail(response);

        showErrorMessage(response);
    }

}
