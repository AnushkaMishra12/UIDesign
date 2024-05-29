package com.example.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    ImageView back, edit;
    TextView profileName, profileGender, profileEmail, profilePhoneNo, profileDesignation;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        edit = findViewById(R.id.edit_bt);
        back = findViewById(R.id.back_bt);
        profileName = findViewById(R.id.profile_name);
        profileGender = findViewById(R.id.profile_gender);
        profilePhoneNo = findViewById(R.id.profile_phoneNo);
        profileEmail = findViewById(R.id.profile_email);
        profileDesignation = findViewById(R.id.profile_designation);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");
        back.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, DashBoardActivity.class);
            startActivity(intent);
        });
       edit.setOnClickListener(v -> passUserData());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot modelSnapshot : dataSnapshot.getChildren()) {
                        String name_st = String.valueOf(modelSnapshot.child("name").getValue());
                        String gender_st = String.valueOf(modelSnapshot.child("gender").getValue());
                        String phone_st = String.valueOf(modelSnapshot.child("phoneNo").getValue());
                        String design_st = String.valueOf(modelSnapshot.child("designation").getValue());
                        String email_st = String.valueOf(modelSnapshot.child("email").getValue());
                        profileName.setText(name_st);
                        profileGender.setText(gender_st);
                        profilePhoneNo.setText(phone_st);
                        profileEmail.setText(email_st);
                        profileDesignation.setText(design_st);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void passUserData() {
        String userName = profileName.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userName);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nameFromDB = snapshot.child(userName).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userName).child("email").getValue(String.class);
                    String phoneNoFromDB = snapshot.child(userName).child("phoneNo").getValue(String.class);
                    String designFromDB = snapshot.child(userName).child("designation").getValue(String.class);
                    String genderFromDB = snapshot.child(userName).child("gender").getValue(String.class);
                    String passFromDB = snapshot.child(userName).child("password").getValue(String.class);

                    Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("phoneNo", phoneNoFromDB);
                    intent.putExtra("designation", designFromDB);
                    intent.putExtra("gender", genderFromDB);
                    intent.putExtra("password", passFromDB);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}