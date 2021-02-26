package com.example.flickrapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An AsyncTask dedicated to the recovery of JSON
 * answers from Flickr.
 * Send the recovered image url to an adapter
 */
public class AsyncFlickrJSONDataForList extends AsyncFlickrJSONData {
    final String TAG = "AsyncFlickrJSONDataForList";
    MyAdapter adapter;

    /**
     * Constructor
     *
     * @param adapter the MyAdapter to send the image url to
     */
    public AsyncFlickrJSONDataForList(MyAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Add the image links to the adapter.
     * Notify the adapter of the data update.
     *
     * @param result the JSON answer
     */
    @Override
    protected void onPostExecute(JSONObject result) {
        Log.i(TAG, result.toString());
        try {
            // Get the JSON array of photo info (items)
            JSONArray imageArray = result.getJSONArray("items");

            // For each photo, recover the link and add it to the adapter
            for (int index = 0; index < imageArray.length(); index++) {
                String link = imageArray.getJSONObject(index).getJSONObject("media").getString("m");
                adapter.add(link);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Notify the adapter of the update
        adapter.notifyDataSetChanged();
    }
}
