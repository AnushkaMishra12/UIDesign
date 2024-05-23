package com.example.uidesign;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uidesign.Adapter.RecyclerAdapter;
import com.example.uidesign.Model.ResponseDataItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        recyclerView= findViewById(R.id.recycler_view);
        getApiRecyclerData();
    }
    private void getApiRecyclerData() {
        Call<List<ResponseDataItem>> call = RetrofitClient.getInstance().getApi().getData();
        call.enqueue(new Callback<List<ResponseDataItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ResponseDataItem>> call, @NonNull Response<List<ResponseDataItem>> response) {
                if (response.isSuccessful()){
                    getItem(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ResponseDataItem>> call, @NonNull Throwable throwable) {
                Toast.makeText(DashBoardActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getItem(List<ResponseDataItem> itemList) {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(itemList,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}