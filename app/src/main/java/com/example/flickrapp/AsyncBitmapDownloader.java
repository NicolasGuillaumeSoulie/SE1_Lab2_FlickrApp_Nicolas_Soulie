package com.example.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An AsyncTask dedicated to the recovery of images
 * in Bitmap format from Flickr
 */
public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    final String TAG = "AsyncBitmapDownloader";

    /**
     * @param strings a list of url
     * @return the image at the first url as a Bitmap
     */
    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap bm = null;
        HttpURLConnection urlConnection = null;
        try {
            Log.i(TAG, "Attempt at downloading : " + strings[0]);

            // Build and connect to the URL
            url = new URL(strings[0].replace("\\", ""));
            urlConnection = (HttpURLConnection) url.openConnection();

            // Read the answer from the url
            InputStream in = urlConnection.getInputStream();
            bm = BitmapFactory.decodeStream(in);

            // Keep a trace in log of the result (success or not) of the operation
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
