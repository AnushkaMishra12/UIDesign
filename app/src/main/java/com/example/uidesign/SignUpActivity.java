package com.example.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
    TextView sign_bt;
    EditText userName_et, email_et, gender_et, phone_et, password_et, designation_et;
    ImageView image_im, camera;
    CardView signUp_bt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


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

        signUp_bt.setOnClickListener(view -> {
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference("users");

            String name_st = userName_et.getText().toString();
            String gender_st = gender_et.getText().toString();
            String email_st = email_et.getText().toString();
            String pass_st = password_et.getText().toString();
            String phone_st = phone_et.getText().toString();
            String design_st = designation_et.getText().toString();

            SignUpDataItem item = new SignUpDataItem(name_st, email_st, gender_st, design_st, phone_st, pass_st);
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

}