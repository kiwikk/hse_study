package com.example.httpconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://kiwikk.pythonanywhere.com/";
    boolean isFinished = false;

    TextView txtString;
    ImageView imageView;
    // public String url = "https://kiwikk.pythonanywhere.com/cover?name=book1";
    //public String url1 = "https://kiwikk.pythonanywhere.com/cover?name=book2";

    MyAdapter myAdapter;
    RecyclerView recyclerView;

    String[] result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        //  new OkHttpHandler(imageView).execute(url);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getMyList());
        recyclerView.setAdapter(myAdapter);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\"> Библиотека </font>"));
    }

    private ArrayList<Model> getMyList() {
        ArrayList<Model> models = new ArrayList<>();

        getBookNames(URL);

        while (!isFinished) {
        }

        String[] book_names = result[0].split(" ");

        for (int i = 0; i < book_names.length; ++i) {
            Book book = new Book();
            book.getInputStream(book_names[i]);

            while (!book.isFinished) {
            }
            book.parseDescription(book.is[0]);

            Model m = new Model();
            m.setTitle(book.bookName);
            m.setDescription(book.description);
            m.setGenre(book.genre);
            m.setAuthor(book.author);
            m.setImg(URL + "cover?name=" + book_names[i]);
            models.add(m);
        }

        return models;
    }

    private void getBookNames(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        result = new String[1];
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.d("OkHttpClient", "Book 61");
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code" + response);
                    else {
                        result[0] = response.body().string();
                        isFinished = true;
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Exception Book 49", e.getMessage());
            e.printStackTrace();
        }
    }
}
