package com.example.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileActivity extends AppCompatActivity {

    ImageView save_bt;
    private EditText name_et, gender_et, phone_et, design_et, email_et, pass_et;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String nameUser, emailUser, passUser, phoneUser, designUser, genderUser;

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