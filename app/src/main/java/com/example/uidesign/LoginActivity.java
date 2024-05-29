package com.example.uidesign;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    TextView signUp_tv,forgetPassword;
    CardView login_cv;
    EditText name_et, pass_et;
    private ProgressDialog pd;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    ImageView google_sbt;
    FirebaseUser firebaseUser;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        name_et = findViewById(R.id.user_lt);
        pass_et = findViewById(R.id.pass_lt);
        login_cv = findViewById(R.id.login_bt);
        signUp_tv = findViewById(R.id.reg_bt);
        google_sbt = findViewById(R.id.google_sbt);
        forgetPassword=findViewById(R.id.forgetPassword);
        rememberMe=findViewById(R.id.rememberMe);
        pd = new ProgressDialog(this);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);

        signUp_tv.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
        });
        login_cv.setOnClickListener(view -> {
            if(!validateUser() | !validatePassword()) {
            } else {
                checkUser();
            }
        });
        forgetPassword.setOnClickListener(v -> {
            showRecoverPasswordDialog();
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("712421544454-lbm83pn3dshjh63f8v8qc7di98d4d17p.apps.googleusercontent.com")
                .requestEmail()
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        google_sbt.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 100);
        });
        if (firebaseUser != null) {
            Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(i);
        }
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        builder.setIcon(R.drawable.logo);
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailet.setHint("Enter Email");
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", (dialog, which) -> {
            String email=emailet.getText().toString().trim();
            beginRecovery(email);
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        pd=new ProgressDialog(this);
        pd.setMessage("Sending Email....");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"Done sent",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Occurred",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this,"Error Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    public Boolean validateUser() {
        String userName = name_et.getText().toString();
        if (userName.isEmpty()) {
            name_et.setError("UserName cannot be empty");
            return false;
        } else {
            name_et.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = pass_et.getText().toString();
        if (val.isEmpty()) {
            pass_et.setError("Password cannot be empty");
            return false;

        } else {
            pass_et.setError(null);
            return true;
        }
    }

    public void checkUser() {
       
        String userName = name_et.getText().toString().trim();
        String userPassword = pass_et.getText().toString().trim();

        pd.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userName);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    signUp_tv.setError(null);
                    String passwordFromDB = dataSnapshot.child(userName).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        signUp_tv.setError(null);

                        String nameFromDB = dataSnapshot.child(userName).child("name").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userName).child("email").getValue(String.class);
                        String genderFromDB = dataSnapshot.child(userName).child("gender").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userName).child("phoneNo").getValue(String.class);
                        String designFromDB = dataSnapshot.child(userName).child("designation").getValue(String.class);
                        String img = dataSnapshot.child(userName).child("image").getValue(String.class);

                        pd.show();

                        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("gender", genderFromDB);
                        intent.putExtra("phoneNo", phoneFromDB);
                        intent.putExtra("designation", designFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("image", img);
                        pd.dismiss();
                        startActivity(intent);
                        finish();
                    } else {
                        pass_et.setError("Invalid Credentials");
                        pass_et.requestFocus();
                    }
                } else {
                    name_et.setError("User does not exist");
                    name_et.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            if (signInAccountTask.isSuccessful()) {

                String s = "Google sign in successful";

                displayToast(s);

                try {

                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);

                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    startActivity(new Intent(LoginActivity.this, DashBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    //  Glide.with(SignUpActivity.this).load(firebaseUser.getPhotoUrl()).into(image_im);
                                    displayToast("Firebase authentication successful");
                                } else {
                                    displayToast("Authentication Failed :" + Objects.requireNonNull(task.getException()).getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}