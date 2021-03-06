package com.pt.myeeg.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.RoundedImageView;
import com.pt.myeeg.models.Especialista;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.services.android.Utility;
import com.pt.myeeg.services.database.InfoHandler;
import com.pt.myeeg.services.webservice.JSONBuilder;
import com.pt.myeeg.services.webservice.MetadataInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jorge Zepeda Tinoco on 22/08/17.
 * jorzet.94@gmail.com
 */

public class SettingsActivity extends BaseActivityLifecycle{

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    private TextView mErrorUpdate;
    private TextView mSaveSettings;
    private EditText mUserName;
    private EditText mUserFirstLastName;
    private EditText mUserSecondLastName;
    private EditText mUserAge;
    private EditText mUserIllness;
    private EditText mUserEmail;
    private EditText mUserPassword;
    private RoundedImageView mProfilePhoto;
    private ImageView mChangeProfilePhoto;
    private ImageView mBackButton;

    private ProgressBar mProgressBar;

    private RelativeLayout mLayoutUserAge;
    private RelativeLayout mLayoutUserIllness;

    private Especialista spetialist;
    private Paciente patient;

    /* Variable for SharedPreferences */
    public boolean isMedic = true;

    public static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        mErrorUpdate = (TextView) findViewById(R.id.error_update);
        mSaveSettings = (TextView) findViewById(R.id.save_changes_settings);
        mUserName = (EditText) findViewById(R.id.edit_user_name);
        mUserFirstLastName = (EditText) findViewById(R.id.edit_user_fistLastName);
        mUserSecondLastName = (EditText) findViewById(R.id.edit_user_secondLastName);
        mUserAge = (EditText) findViewById(R.id.edit_user_age);
        mUserIllness = (EditText) findViewById(R.id.edit_user_illness);
        mUserEmail = (EditText) findViewById(R.id.edit_user_email);
        mUserPassword = (EditText) findViewById(R.id.edit_user_password);
        mProfilePhoto = (RoundedImageView) findViewById(R.id.user_profile_photo);
        mChangeProfilePhoto = (ImageView) findViewById(R.id.change_user_profile_photo);
        mBackButton = (ImageView) findViewById(R.id.arrow_back);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_updating);


        mLayoutUserAge = (RelativeLayout) findViewById(R.id.layout_user_age);
        mLayoutUserIllness = (RelativeLayout) findViewById(R.id.layout_user_illness);

        mContext = getApplication();
        isMedic = getCurrentUser();

        loadUserData();

        mSaveSettings.setOnClickListener(mSaveListener);
        mChangeProfilePhoto.setOnClickListener(mChangePhoto);
        mBackButton.setOnClickListener(backAction);

    }

    private View.OnClickListener backAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    public boolean getCurrentUser(){
        return new InfoHandler(mContext).getIsMedic();
    }

    private void loadUserData(){
        if(isMedic) {
            spetialist = new InfoHandler(getApplication()).getSpetialistInfo();

            mUserName.setText(spetialist.getName());
            mUserFirstLastName.setText(spetialist.getFirstLastName());
            mUserSecondLastName.setText(spetialist.getSecondLastName());
            mUserEmail.setText(spetialist.getEmail());
            String dotPassword = "";
            int pasLength = spetialist.getPassword().length();
            for(int i =0;i<pasLength;i++)
                dotPassword = dotPassword + "*";
            mUserPassword.setText(spetialist.getPassword());

            mLayoutUserAge.setVisibility(View.GONE);
            mLayoutUserIllness.setVisibility(View.GONE);

            Bitmap bmp = BitmapFactory.decodeByteArray(spetialist.getPrifilePhoto(), 0, spetialist.getPrifilePhoto().length);
            mProfilePhoto.setImageBitmap(bmp);
        } else {
            patient = new InfoHandler(getApplication()).getPatientInfo();
            mUserName.setText(patient.getName());
            mUserFirstLastName.setText(patient.getFirstLastName());
            mUserSecondLastName.setText(patient.getSecondLastName());
            mUserAge.setText("" + patient.getAge());
            mUserIllness.setText(patient.getPadecimiento());
            mUserEmail.setText(patient.getEmail());

            String dotPassword = "";
            int pasLength = patient.getPassword().length();
            for(int i =0;i<pasLength;i++)
                dotPassword = dotPassword + "*";
            mUserPassword.setText(patient.getPassword());

            Bitmap bmp = BitmapFactory.decodeByteArray(patient.getPrifilePhoto(), 0, patient.getPrifilePhoto().length);
            mProfilePhoto.setImageBitmap(bmp);
        }
    }

    private View.OnClickListener mSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveChanges();
        }
    };

    private void saveChanges() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (isMedic) {
            spetialist.setName(mUserName.getText().toString());
            spetialist.setFistLastName(mUserFirstLastName.getText().toString());
            spetialist.setSecondLastName(mUserSecondLastName.getText().toString());
            spetialist.setEmail(mUserEmail.getText().toString());
            spetialist.setPassword(mUserPassword.getText().toString());

            new SendRequestUpdateSpetialistData().execute();
        } else {
            patient.setName(mUserName.getText().toString());
            patient.setFistLastName(mUserFirstLastName.getText().toString());
            patient.setSecondLastName(mUserSecondLastName.getText().toString());
            patient.setAge(Integer.parseInt(mUserAge.getText().toString()));
            patient.setPadecimiento(mUserIllness.getText().toString());
            patient.setEmail(mUserEmail.getText().toString());
            patient.setPassword(mUserPassword.getText().toString());

            new SendRequestUpdatePatientData().execute();
        }
    }

    private ImageView.OnClickListener mChangePhoto = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mErrorUpdate.setText("Servicio no disponible");
            //new ErrorDialog(getBaseContext()).showErrorServiceNotAvailable();
            //selectImage();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = { "Tomar Foto", "Seleccionar de Galeria","Cerrar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(SettingsActivity.this);

                if (items[item].equals("Tomar Foto")) {
                    userChoosenTask ="Tomar Foto";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Seleccionar de Galeria")) {
                    userChoosenTask ="Seleccionar de Galeria";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cerrar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mProfilePhoto.setImageBitmap(thumbnail);
        //user.setFotografia(bytes.toByteArray());
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mProfilePhoto.setImageBitmap(bm);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        //user.setFotografia(stream.toByteArray());
    }


    private class SendRequestUpdatePatientData extends AsyncTask<String, Long, String> {

        @Override
        protected String doInBackground(String... params) {
            String res = new MetadataInfo().requestUpdatePatientData(patient, mContext);
            return res;
        }

        protected void onPostExecute(String response) {
            if (response == null || response.equals("")) {
                //new ErrorDialog(mContext).showErrorUpdateInfo(Palabras.ERROR_FROM_WEB_WERVICE);
                mErrorUpdate.setText(Palabras.ERROR_FROM_WEB_WERVICE);
                mProgressBar.setVisibility(View.GONE);
            } else if (response.equals(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED)) {
                //new ErrorDialog(mContext).showErrorUpdateInfo(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
                mErrorUpdate.setText(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
                mProgressBar.setVisibility(View.GONE);
            } else if (response.contains("Error") && response.contains("Message")) {
                try {
                    JSONObject json = new JSONObject(response);
                    int codeError = json.getInt("Error");
                    String message = json.getString("Message");
                    //new ErrorDialog(mContext).showErrorUpdateInfo(message);
                    mErrorUpdate.setText(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);
            } else if (JSONBuilder.checkJsonStructure(response)) {
                System.out.println("response: " + response);
                mProgressBar.setVisibility(View.GONE);
                String newPatientJson = JSONBuilder.buildJsonFromObject(patient);
                new InfoHandler(mContext).savePatientInfo(newPatientJson);
                //mSucessEmailSended.setText(response);
                //mSucessEmailSended.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        }
    }

    private class SendRequestUpdateSpetialistData extends AsyncTask<String, Long, String> {

        @Override
        protected String doInBackground(String... params) {
            String res = new MetadataInfo().requestUpdateSpetialistData(spetialist, mContext);
            return res;
        }

        protected void onPostExecute(String response) {
            if (response == null || response.equals("")) {
                //new ErrorDialog(mContext).showErrorUpdateInfo(Palabras.ERROR_FROM_WEB_WERVICE);
                mErrorUpdate.setText(Palabras.ERROR_FROM_WEB_WERVICE);
                mProgressBar.setVisibility(View.GONE);
            } else if (response.equals(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED)) {
                //new ErrorDialog(mContext).showErrorUpdateInfo(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
                mErrorUpdate.setText(Palabras.ERROR_FROM_NETWORK_NOT_CONNECTED);
                mProgressBar.setVisibility(View.GONE);
            } else if (response.contains("Error") && response.contains("Message")) {
                try {
                    JSONObject json = new JSONObject(response);
                    int codeError = json.getInt("Error");
                    String message = json.getString("Message");
                    //new ErrorDialog(mContext).showErrorUpdateInfo(message);
                    mErrorUpdate.setText(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);
            } else if (JSONBuilder.checkJsonStructure(response)) {
                System.out.println("response: " + response);
                mProgressBar.setVisibility(View.GONE);
                String newSpetialistson = JSONBuilder.buildJsonFromObject(spetialist);
                new InfoHandler(mContext).saveSpetilistInfo(newSpetialistson);
                //mSucessEmailSended.setText(response);
                //mSucessEmailSended.setVisibility(View.VISIBLE);
                onBackPressed();
            }
        }
    }



}
