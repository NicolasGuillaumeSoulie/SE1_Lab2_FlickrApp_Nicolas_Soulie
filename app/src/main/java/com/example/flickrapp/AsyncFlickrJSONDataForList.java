package com.example.flickrapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AsyncFlickrJSONDataForList extends AsyncFlickrJSONData {
    final String TAG = "AsyncFlickrJSONDataForList";
    MyAdapter adapter;

    public AsyncFlickrJSONDataForList(MyAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        Log.i(TAG, result.toString());
        try {
            JSONArray imageArray = result.getJSONArray("items");
            for (int index = 0; index < imageArray.length(); index++) {
                String link = imageArray.getJSONObject(index).getJSONObject("media").getString("m");
                adapter.add(link);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
}
