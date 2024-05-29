package com.example.uidesign;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.example.uidesign.Model.ProductsItem;
import com.example.uidesign.Model.ResponseDataItem;
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

        Call<List<ProductsItem>> call = RetrofitClient.getInstance().getApi().getDataProduct();
        call.enqueue(new Callback<List<ProductsItem>>() {

            @Override
            public void onResponse(@NonNull Call<List<ProductsItem>> call, @NonNull Response<List<ProductsItem>> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                Log.d("TAG", response.code() + "");
                ProductsItem resource = (ProductsItem) response.body();
                List<ProductsItem> datumList = resource.data;

            }

            @Override
            public void onFailure(@NonNull Call<List<ProductsItem>> call, @NonNull Throwable throwable) {
                Toast.makeText(DashBoardActivity.this, "Failed No Internet Connection", Toast.LENGTH_SHORT).show();
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