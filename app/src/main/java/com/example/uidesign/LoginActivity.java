package com.example.uidesign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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

    TextView signUp_tv;
    CardView login_cv;
    EditText name_et, pass_et;
    private ProgressDialog pd;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    ImageView google_sbt;
    FirebaseUser firebaseUser;

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
            if (!validateUser() | !validatePassword()) {
            } else {
                checkUser();
            }
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
                        pd.show();

                        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("gender", genderFromDB);
                        intent.putExtra("phoneNo", phoneFromDB);
                        intent.putExtra("designation", designFromDB);
                        intent.putExtra("password", passwordFromDB);
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