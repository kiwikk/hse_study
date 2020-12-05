package com.example.httpconnection;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpImageHandler extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;

    public OkHttpImageHandler(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(params[0]).build();

        Response response = null;
        Bitmap bmp = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.isSuccessful())
            try {
                bmp = BitmapFactory.decodeStream(response.body().byteStream());
            } catch (Exception e) {
                Log.e("error", e.getMessage());
                e.printStackTrace();
            }

        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}