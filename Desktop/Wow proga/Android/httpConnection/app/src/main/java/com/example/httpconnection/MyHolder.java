package com.example.httpconnection;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.httpconnection.description.ItemClickListener;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView;
    TextView mtitle, mAuth;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.imageView);
        this.mtitle = itemView.findViewById(R.id.title);
        this.mAuth = itemView.findViewById(R.id.author);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.OnItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
