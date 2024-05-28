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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class SignUpActivity extends AppCompatActivity {
    TextView sign_bt;
    EditText userName_et, email_et, gender_et, phone_et, password_et, designation_et;
    ImageView image_im, camera;
    CardView signUp_bt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        sign_bt = findViewById(R.id.sign_bt);
        userName_et = findViewById(R.id.user_rt);
        email_et = findViewById(R.id.email_rt);
        gender_et = findViewById(R.id.gender_rt);
        phone_et = findViewById(R.id.phone_rt);
        password_et = findViewById(R.id.pass_rt);
        designation_et = findViewById(R.id.desig_rt);
        signUp_bt = findViewById(R.id.signUp_bt);
        image_im = findViewById(R.id.image_im);
        camera = findViewById(R.id.camera);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        camera.setOnClickListener(v -> {
            if (checkAndRequestPermissions()) {
                chooseImage();
            }
        });

        signUp_bt.setOnClickListener(view -> {
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference("users");

            String name_st = userName_et.getText().toString();
            String gender_st = gender_et.getText().toString();
            String email_st = email_et.getText().toString();
            String pass_st = password_et.getText().toString();
            String phone_st = phone_et.getText().toString();
            String design_st = designation_et.getText().toString();
            String img= selectedImage.getPath();


            uploadImage();

            SignUpDataItem item = new SignUpDataItem(name_st, email_st, gender_st, design_st, phone_st, pass_st,img);
            reference.child(name_st).setValue(item);
            Toast.makeText(SignUpActivity.this, "you have SIgnUp Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(i);
        });

        sign_bt.setOnClickListener(view -> {
            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }

    private void uploadImage() {
        if (selectedImage != null) {
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(selectedImage).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(SignUpActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> {
                Toast.makeText(SignUpActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }).addOnProgressListener(taskSnapshot -> {

            });
        }
    }

    private void chooseImage() {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(optionsMenu, (dialogInterface, i) -> {
            if (optionsMenu[i].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (optionsMenu[i].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);

            } else if (optionsMenu[i].equals("Exit")) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private boolean checkAndRequestPermissions() {
        int WExtstorePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
        //    selectedImage = data.getData();
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        image_im.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                       selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                image_im.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                break;
            }
        }
    }
}