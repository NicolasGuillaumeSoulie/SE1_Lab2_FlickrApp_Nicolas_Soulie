package com.example.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    final String TAG = "AsyncBitmapDownloader";

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap bm = null;
        HttpURLConnection urlConnection = null;
        try {
            Log.i(TAG, "Attempt at downloading : " + strings[0]);
            url = new URL(strings[0].replace("\\", ""));
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            bm = BitmapFactory.decodeStream(in);
            if (bm != null) Log.i(TAG, "Bitmap downloaded");
            else {
                Log.e(TAG, "Bitmap null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return bm;
    }

}
