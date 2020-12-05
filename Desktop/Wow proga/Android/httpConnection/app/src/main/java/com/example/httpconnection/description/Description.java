package com.example.httpconnection.description;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.httpconnection.OkHttpImageHandler;
import com.example.httpconnection.R;

public class Description extends AppCompatActivity {

    public static String URL;

    TextView title;
    TextView author;
    TextView genre;
    TextView description;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        title = findViewById(R.id.book_name);
        author = findViewById(R.id.author);
        genre = findViewById(R.id.genre);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("iTitle"));
        author.setText(intent.getStringExtra("iAuthor"));
        genre.setText(intent.getStringExtra("iGenre"));
        description.setText(intent.getStringExtra("iDescription"));

        new OkHttpImageHandler(imageView).execute(URL);


    }
}
