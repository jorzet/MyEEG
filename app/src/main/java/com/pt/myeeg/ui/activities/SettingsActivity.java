package com.pt.myeeg.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.RoundedImageView;
import com.pt.myeeg.models.Paciente;
import com.pt.myeeg.services.android.Utility;
import com.pt.myeeg.services.database.InfoHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jorge Zepeda Tinoco on 22/08/17.
 */

public class SettingsActivity extends BaseActivityLifecycle{

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    private EditText mUserName;
    private EditText mUserFirstLastName;
    private EditText mUserSecondLastName;
    private EditText mUserAge;
    private EditText mUserIllness;
    private EditText mUserEmail;
    private EditText mUserPassword;
    private RoundedImageView mProfileFoto;
    private ImageView mChangeProfilePhoto;
    private ImageView mBackButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        mUserName = (EditText) findViewById(R.id.edit_user_name);
        mUserFirstLastName = (EditText) findViewById(R.id.edit_user_fistLastName);
        mUserSecondLastName = (EditText) findViewById(R.id.edit_user_secondLastName);
        mUserAge = (EditText) findViewById(R.id.edit_user_age);
        mUserIllness = (EditText) findViewById(R.id.edit_user_illness);
        mUserEmail = (EditText) findViewById(R.id.edit_user_email);
        mUserPassword = (EditText) findViewById(R.id.edit_user_password);
        mProfileFoto = (RoundedImageView) findViewById(R.id.user_profile_photo);
        mChangeProfilePhoto = (ImageView) findViewById(R.id.change_user_profile_photo);
        mBackButton = (ImageView) findViewById(R.id.arrow_back);

        loadUserData();

        mChangeProfilePhoto.setOnClickListener(mChangePhoto);
        mBackButton.setOnClickListener(backAction);
    }

    private View.OnClickListener backAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void loadUserData(){
        Paciente patient = new InfoHandler(getApplication()).getPatientInfo();

        mUserName.setText(patient.getName());
        mUserFirstLastName.setText(patient.getFirstLastName());
        mUserSecondLastName.setText(patient.getSecondLastName());
        mUserAge.setText("" + patient.getAge());
        mUserIllness.setText(patient.getPadecimiento());
        mUserEmail.setText(patient.getEmail());
        mUserPassword.setText("**************");
    }

    private ImageView.OnClickListener mChangePhoto = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
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
        mProfileFoto.setImageBitmap(thumbnail);
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

        mProfileFoto.setImageBitmap(bm);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        //user.setFotografia(stream.toByteArray());
    }
}
