package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.modles.ApiModel;

import java.util.ArrayList;

public class RecyclerNewsAdapter extends RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder> {
    Context context;
    ArrayList<ApiModel> modelClassArrayList;



    public void setfilterData(ArrayList<ApiModel> filterData) {
        this.modelClassArrayList = filterData;
        notifyDataSetChanged();
    }

    public RecyclerNewsAdapter(Context context, ArrayList<ApiModel> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    @NonNull
    @Override
    public RecyclerNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_show,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerNewsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(modelClassArrayList.get(position).getUrlToImage()).error(R.drawable.image).into(holder.imgNews);
        holder.author.setText(modelClassArrayList.get(position).author);
        holder.title.setText(modelClassArrayList.get(position).title);
        holder.description.setText(modelClassArrayList.get(position).description);
        holder.time.setText(modelClassArrayList.get(position).publishedAt);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, webView.class);
                intent.putExtra("url", modelClassArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView title, description, author, time;
        CardView cardView;
        LinearLayout row;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardView);
            author = itemView.findViewById(R.id.author);
            description = itemView.findViewById(R.id.description);
            row = itemView.findViewById(R.id.row);
        }
    }
}
