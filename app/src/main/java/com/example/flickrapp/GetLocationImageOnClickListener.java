package com.example.flickrapp;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Recover a geo localised image from Flickr API on click.
 * Use AsyncTasks.
 * Extends GetImageOnClickListener as the only differences are the url to send
 * and the JSON answer structure.
 */
class GetLocationImageOnClickListener extends GetImageOnClickListener {
    static final String TAG = "GetLocationImageOnClick";
    MainActivity parent;

    /**
     * Constructor
     *
     * @param iv           the imageView to update
     * @param mainActivity the parent activity. Used to recover the coordinates
     */
    public GetLocationImageOnClickListener(ImageView iv, MainActivity mainActivity) {
        super(iv);
        parent = mainActivity;
        urlAddress = "https://api.flickr.com/services/rest/?method=flickr.photos.search&license=4&api_key=4167810fbc47cc7f1da6d151edf224d5&has_geo=1&"
                + "lat=" + parent.getLatitude()
                + "&lon=" + parent.getLongitude()
                + "&per_page=1&format=json";
    }

    /**
     * @param index the index of the image inside the JSON list
     * @return the link to a geo localised image from the Flickr API
     * The override is there to deal with a different JSON structure
     */
    @Override
    public String getImageLink(int index) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        // Update the url with the last coordinates
        urlAddress = "https://api.flickr.com/services/rest/?method=flickr.photos.search&license=4&api_key=4167810fbc47cc7f1da6d151edf224d5&has_geo=1&"
                + "lat=" + parent.getLatitude()
                + "&lon=" + parent.getLongitude()
                + "&per_page=1&format=json";

        // Send the geo localised search request to Flickr and extract the info
        JSONObject photo = getImageList().getJSONObject("photos").getJSONArray("photo").getJSONObject(index);
        String server_id = photo.getString("server");
        String id = photo.getString("id");
        String secret = photo.getString("secret");

        // Use the recovered info to build the image url
        // Photo link format : https://live.staticflickr.com/{server-id}/{id}_{secret}.jpg
        String link = "https://live.staticflickr.com/" + server_id + "/" + id + "_" + secret + ".jpg";
        Log.i("TAG", link);

        return link;
    }

}
