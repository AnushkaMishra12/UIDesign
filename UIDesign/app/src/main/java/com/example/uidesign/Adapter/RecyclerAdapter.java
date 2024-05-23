package com.example.uidesign.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uidesign.Model.ResponseDataItem;
import com.example.uidesign.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<ResponseDataItem> dataItem;
    Context context;

    public RecyclerAdapter(List<ResponseDataItem> dataItem, Context context) {
        this.dataItem=dataItem;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modeldata, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseDataItem responseDataItem=dataItem.get(position);
        holder.id.setText("Id :"+responseDataItem.getAlbumId()+"");
        holder.album_id.setText("Album Id : "+responseDataItem.getId()+"");
        holder.title.setText("Title :"+responseDataItem.getTitle());
        Picasso.get().load(responseDataItem.getThumbnailUrl()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView album_id,id,title;
        ImageView img;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            album_id = itemView.findViewById(R.id.album_id);
            id = itemView.findViewById(R.id.id_tv);
            title = itemView.findViewById(R.id.title_tv);
            img= itemView.findViewById(R.id.img_iv);
            itemView.setClickable(true);
        }
    }
}


