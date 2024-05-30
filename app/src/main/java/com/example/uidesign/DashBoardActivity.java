package com.example.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uidesign.Adapter.RecyclerAdapter;
import com.example.uidesign.Model.ProductDataItem;
import com.example.uidesign.Model.ProductsItem;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView profile, log_out;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    private ShimmerFrameLayout shimmerFrameLayout;
    RecyclerAdapter recyclerAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        shimmerFrameLayout = findViewById(R.id.shimmer);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recycler_view);
        log_out = findViewById(R.id.logOut);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

//        Intent intent = getIntent();
//        String img = intent.getExtras().getString("image");
//        Glide.with(DashBoardActivity.this).load(img).into(profile);

        if (firebaseUser != null) {
            Glide.with(DashBoardActivity.this).load(firebaseUser.getPhotoUrl()).into(profile);
        }

        googleSignInClient = GoogleSignIn.getClient(DashBoardActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        profile.setOnClickListener(view -> {
            Intent i = new Intent(DashBoardActivity.this, ProfileActivity.class);
            startActivity(i);
        });

        log_out.setOnClickListener(view -> {
            googleSignInClient.signOut().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    firebaseAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });

        getApiRecyclerData();
    }

    private void getApiRecyclerData() {
        Call<ProductDataItem> call = RetrofitClient.getInstance().getApi().getDataProduct();
        call.enqueue(new Callback<ProductDataItem>() {
            @Override
            public void onResponse(@NonNull Call<ProductDataItem> call, @NonNull Response<ProductDataItem> response) {

                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        getItem(response.body().getProducts());
                    }
                }
                //  Log.d("TAG", response.code() + "");
            }

            @Override
            public void onFailure(@NonNull Call<ProductDataItem> call, @NonNull Throwable throwable) {
                Toast.makeText(DashBoardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("Responsedata", throwable + "");
            }
        });
    }

    private void getItem(List<ProductsItem> itemList) {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(itemList, this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}