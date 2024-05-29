package com.example.uidesign;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText emailInput;
    Button resetPassword;
    TextView returnToLogin, txtEmailStatusMessage, statusMessage;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        emailInput = findViewById(R.id.editTextEmailInput);
        resetPassword = findViewById(R.id.buttonResetYourPassword);
        returnToLogin = findViewById(R.id.textReturnToLoginPage);
        txtEmailStatusMessage = findViewById(R.id.emailInputStatus);
        statusMessage = findViewById(R.id.statusMessages);
        firebaseAuth = FirebaseAuth.getInstance();
        returnToLogin.setOnClickListener(v -> finish());

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String inputEmail = emailInput.getText().toString().trim();
                if (TextUtils.isEmpty(inputEmail)) {
                    txtEmailStatusMessage.setText("Enter your registered Email Address");
                } else {
                   sendVerification(inputEmail);
                    txtEmailStatusMessage.setText(" ");
                }
            }
        });
    }
        private void sendVerification(String inputEmail) {
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();
        FirebaseAuth.getInstance().sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "Email sent.");
                    progressDialog.dismiss();
                    statusMessage.setText("We have sent a link on your Email Address to Reset Your Password");
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                        finish();
                    }, 3000);
                }
            }
        }
        ).addOnFailureListener(e -> statusMessage.setText(e.getMessage()));
    }
}

