package com.example.flickrapp;

import android.util.Log;
import android.widget.ImageView;

import androidx.loader.content.CursorLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

class GetLocationImageOnClickListener extends GetImageOnClickListener {
    static final String TAG = "GetLocationImageOnClick";

    public GetLocationImageOnClickListener(ImageView iv) {
        super(iv);
        urlAddress = "https://api.flickr.com/services/rest/?method=flickr.photos.search&license=4&api_key=4167810fbc47cc7f1da6d151edf224d5&has_geo=1&lat=37.421998333333335&lon=-122.084&per_page=1&format=json";
    }

    public String getImageLink(int index) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        // Photo link format : https://live.staticflickr.com/{server-id}/{id}_{secret}.jpg
        JSONObject photo = getImageList(urlAddress).getJSONObject("photos").getJSONArray("photo").getJSONObject(index);
        String server_id = photo.getString("server");
        String id = photo.getString("id");
        String secret = photo.getString("secret");

        String link = "https://live.staticflickr.com/" + server_id + "/" + id + "_" + secret + ".jpg";
        Log.i("TAG", link);
        return link;
    }

}
