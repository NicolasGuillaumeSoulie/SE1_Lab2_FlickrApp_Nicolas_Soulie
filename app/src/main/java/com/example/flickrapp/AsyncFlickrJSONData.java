package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An AsyncTask dedicated to the recovery of JSON
 * answers from Flickr
 */
public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    final String TAG = "AsyncFlickrJSONData";

    /**
     * @param strings a list of url
     * @return the JSON answer of the first url
     */
    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        JSONObject result = null;

        try {
            // Build and connect to the URL
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                // Read the answer from the url
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);

                // From the answer, remove the extra bits surrounding the JSON
                // Answer format : jsonFlickrFeed ( "the JSON answer" )
                s = (String) s.subSequence(s.indexOf('(') + 1, s.length() - 2);

                // Convert the JSON string to a JSONObject
                result = new JSONObject(s);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Keep a trace in log of the JSON answer and the
     * link of the first image
     *
     * @param result the JSON answer
     */
    protected void onPostExecute(JSONObject result) {
        Log.i(TAG, result.toString());
        try {
            String link = result.getJSONArray("items").getJSONObject(0).getString("link");
            Log.i(TAG, link);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param is an inputStream
     * @return a string conversion of the inputStream
     */
    private String readStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
            }
        }
        return sb.toString();
    }
}
