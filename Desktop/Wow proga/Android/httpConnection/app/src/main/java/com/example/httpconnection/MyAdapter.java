package com.example.httpconnection;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.httpconnection.description.Description;
import com.example.httpconnection.description.ItemClickListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        myHolder.mtitle.setText(models.get(i).getTitle());
        myHolder.mAuth.setText(models.get(i).getAuthor());
        new OkHttpImageHandler(myHolder.imageView).execute(models.get(i).getUrl());

        myHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClickListener(View v, int position) {
                Intent intent = new Intent(c, Description.class);
                intent.putExtra("iTitle", models.get(position).getTitle());
                intent.putExtra("iAuthor", models.get(position).getAuthor());
                intent.putExtra("iGenre", models.get(position).getGenre());
                intent.putExtra("iDescription", models.get(position).getDescription());

                //TODO: genre, descriptioin
                Description.URL = models.get(position).getUrl();
                // new OkHttpImageHandler(myHolder.imageView).execute(models.get(i).getUrl());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
