package com.example.flickrapp;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Recover an image from Flickr API on click.
 * Use AsyncTasks
 */
public class GetImageOnClickListener implements View.OnClickListener {
    static final String TAG = "GetImageOnClick";
    String urlAddress;
    ImageView iv;

    /**
     * Constructor
     *
     * @param iv the imageView to update
     */
    public GetImageOnClickListener(ImageView iv) {
        this.urlAddress = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";
        this.iv = iv;
    }

    /**
     * Recover an image from Flickr API on click
     *
     * @param v the button
     */
    @Override
    public void onClick(View v) {
        // Check if the image view exist
        if (iv != null) {
            Bitmap bmp = null;

            // Try to recover an image
            try {
                bmp = getImage(getImageLink(0));
            } catch (ExecutionException | InterruptedException | TimeoutException | JSONException e) {
                e.printStackTrace();
            }

            // If it succeed, scale the image and send it to the imageVew
            if (bmp != null) {
                Bitmap bmpDisplay = Bitmap.createScaledBitmap(bmp, 600, 600, true);
                iv.setImageBitmap(bmpDisplay);
                Log.i(TAG, "Bitmap displayed");
                return;
            }
            Log.e(TAG, "No Bitmap to display");
        }
    }

    /**
     * Ask the  Flickr API for a JSON describing a list of images.
     * Use an AsyncTask (AsyncFlickrJSONData).
     *
     * @return Flickr answer as a JSONObject
     */
    public JSONObject getImageList() throws InterruptedException, ExecutionException, TimeoutException {
        AsyncFlickrJSONData task = (AsyncFlickrJSONData) new AsyncFlickrJSONData().execute(urlAddress);
        JSONObject images = task.get();
        return images;
    }

    /**
     * @param index the index of the image inside the JSON list
     * @return the link to an image from the Flickr API
     */
    public String getImageLink(int index) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        String link = getImageList().getJSONArray("items").getJSONObject(index).getJSONObject("media").getString("m");
        return link;
    }

    /**
     * @param link the link of an image
     * @returnthe image as a Bitmap
     * use an AsyncTask (AsyncBitmapDownloader)
     */
    public Bitmap getImage(String link) throws ExecutionException, InterruptedException {
        AsyncBitmapDownloader downloader = (AsyncBitmapDownloader) new AsyncBitmapDownloader().execute(link);
        Bitmap map = downloader.get();
        return map;
    }
}
