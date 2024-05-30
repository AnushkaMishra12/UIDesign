package com.example.uidesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {
    TextView title,discription_d, price_d;
    String title_st,img_st,des_st,price_st;
    ImageView imageView;
    ImageView back;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        imageView=findViewById(R.id.img);
        title=findViewById(R.id.title_d);
        back =findViewById(R.id.back);
        discription_d=findViewById(R.id.discription_d);
        price_d =findViewById(R.id.price_d);

        back.setOnClickListener(v -> onBackPressed());

        Intent i = getIntent();
        title_st=String.valueOf(i.getStringExtra("title"));
        title.setText(title_st);

        des_st=String.valueOf(i.getStringExtra("description"));
        discription_d.setText(des_st);

        price_st=String.valueOf(i.getStringExtra("price"));
        price_d.setText("₹"+ price_st);

        img_st = i.getStringExtra("thumbnail");
        Picasso.get().load(img_st).into(imageView);

    }
}