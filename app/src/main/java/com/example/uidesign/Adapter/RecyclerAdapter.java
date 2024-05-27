package com.example.uidesign.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uidesign.Model.ProductsItem;
import com.example.uidesign.Model.ResponseDataItem;
import com.example.uidesign.Model.ResponseDataModel;
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
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modeldata, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ProductsItem responseDataItem = dataItem.get(position);
        holder.id.setText("Id :" + responseDataItem.getId() + "");
        holder.album_id.setText("Album Id : " + responseDataItem.getRating() + "");
        holder.title.setText("Title :" + responseDataItem.    getTitle());
        Picasso.get().load(responseDataItem.getThumbnail()).into(holder.img);
        holder.share.setOnClickListener(view -> {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Title: " + holder.title.getText().toString() +
                    "Id : \n" + holder.id.getText().toString()
                    + "Album Id \n" + holder.album_id.getText().toString());
            //intent.putExtra("Id", dataItem.get(position).getTitle().toString());
            intent.setType("text/plain");
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView album_id, id, title;
        ImageView img, share;
        LinearLayout card;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            album_id = itemView.findViewById(R.id.album_id);
            id = itemView.findViewById(R.id.id_tv);
            title = itemView.findViewById(R.id.title_tv);
            img = itemView.findViewById(R.id.img_iv);
            share = itemView.findViewById(R.id.share_im);
            card = itemView.findViewById(R.id.card_data);
            itemView.setClickable(true);
        }
    }
}


