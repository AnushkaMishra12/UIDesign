package com.example.uidesign;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileActivity extends AppCompatActivity {

    ImageView save_bt;
    private EditText name_et, gender_et, phone_et, design_et, email_et, pass_et;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String nameUser, emailUser, passUser, phoneUser, designUser, genderUser;
    ImageView cam, img;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");

        save_bt = findViewById(R.id.save);
        name_et = findViewById(R.id.name_up);
        gender_et = findViewById(R.id.gender_up);
        phone_et = findViewById(R.id.phone_up);
        design_et = findViewById(R.id.des_up);
        email_et = findViewById(R.id.email_up);
        pass_et = findViewById(R.id.pass_up);
        cam=findViewById(R.id.cam_up);
        img=findViewById(R.id.image_up);

        cam.setOnClickListener(v -> {
            if(checkAndRequestPermissionsForUpdate()){
                chooseImageForUpdate();
            }
        });

        showData();

        save_bt.setOnClickListener(view -> {
            if (isNameChanged() && isPasswordChanged() && isEmailChanged() && isGenderChanged() && isPhoneNoChanged() && isDesignationChanged()) {
                Intent i = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                Toast.makeText(UpdateProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                startActivity(i);
            } else {
                Toast.makeText(UpdateProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chooseImageForUpdate() {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(optionsMenu, (dialogInterface, i) -> {
            if(optionsMenu[i].equals("Take Photo")){

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
            else if(optionsMenu[i].equals("Choose from Gallery")){

                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
            else if (optionsMenu[i].equals("Exit")) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private boolean checkAndRequestPermissionsForUpdate() {
        int WExtstorePermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        img.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                break;
            }
        }
    }

    private boolean isDesignationChanged() {
        if (!designUser.equalsIgnoreCase(design_et.getText().toString())) {
            reference.child("users").child("designation").setValue(design_et.getText().toString());
            designUser = design_et.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isPhoneNoChanged() {
        if (!phoneUser.equalsIgnoreCase(phone_et.getText().toString())) {
            reference.child("users").child("phoneNo").setValue(phone_et.getText().toString());
            phoneUser = phone_et.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isGenderChanged() {
        if (!genderUser.equalsIgnoreCase(gender_et.getText().toString())) {
            reference.child("users").child("gender").setValue(gender_et.getText().toString());
            genderUser = gender_et.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isNameChanged() {
        if (!nameUser.equalsIgnoreCase(name_et.getText().toString())) {
            reference.child("users").child("name").setValue(name_et.getText().toString());
            nameUser = name_et.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isEmailChanged() {
        if (!emailUser.equalsIgnoreCase(email_et.getText().toString())) {
            reference.child("users").child("email").setValue(email_et.getText().toString());
            emailUser = email_et.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isPasswordChanged() {
        if (!passUser.equalsIgnoreCase(pass_et.getText().toString())) {
            reference.child("users").child("password").setValue(pass_et.getText().toString());
            passUser = pass_et.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private void showData() {
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        genderUser = intent.getStringExtra("gender");
        phoneUser = intent.getStringExtra("phoneNo");
        designUser = intent.getStringExtra("designation");
        passUser = intent.getStringExtra("password");
        name_et.setText(nameUser);
        email_et.setText(emailUser);
        gender_et.setText(genderUser);
        phone_et.setText(phoneUser);
        design_et.setText(designUser);
        pass_et.setText(passUser);
        //data save on update activity...
    }
}