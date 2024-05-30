package com.example.uidesign.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uidesign.DetailProductActivity;
import com.example.uidesign.Model.ProductsItem;
import com.example.uidesign.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<ProductsItem> dataItem;
    Context context;

    public RecyclerAdapter(List<ProductsItem> dataItem, Context context) {
        this.dataItem = dataItem;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductsItem responseDataItem = dataItem.get(position);
        holder.price.setText("₹" + responseDataItem.getPrice() + "");
        holder.discription.setText("Discription :" + responseDataItem.getDescription() + "");
        holder.title.setText("Title :" + responseDataItem.getTitle());
        holder.dis.setText("%" + responseDataItem.getDiscountPercentage() + "");
        holder.category.setText(responseDataItem.getCategory());
        Picasso.get().load(responseDataItem.getThumbnail()).into(holder.img);
        holder.rating.setRating(Float.parseFloat(responseDataItem.getRating() + ""));

        holder.img.setOnClickListener(v -> moveToAddressFrom(v,holder.getAdapterPosition()));
    }

    private void moveToAddressFrom(View v, int adapterPosition) {
        Intent i = new Intent(v.getContext(), DetailProductActivity.class);
        i.putExtra("title",dataItem.get(adapterPosition).getTitle());
        i.putExtra("thumbnail",dataItem.get(adapterPosition).getThumbnail());
        i.putExtra("description",dataItem.get(adapterPosition).getDescription());
        i.putExtra("description",dataItem.get(adapterPosition).getDescription());
        i.putExtra("price",dataItem.get(adapterPosition).getPrice());
        v.getContext().startActivity(i);
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView price, dis, discription,title,category;
        ImageView img;
        LinearLayout card;
        RatingBar rating;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            price = itemView.findViewById(R.id.price_p);
            dis = itemView.findViewById(R.id.discount_p);
            discription = itemView.findViewById(R.id.dis_p);
            title = itemView.findViewById(R.id.tit_p);
            img=itemView.findViewById(R.id.img_p);
            category=itemView.findViewById(R.id.category_p);
            rating=itemView.findViewById(R.id.rating);
            itemView.setClickable(true);
        }
    }
}


