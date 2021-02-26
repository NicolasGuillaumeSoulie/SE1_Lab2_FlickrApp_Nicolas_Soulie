package com.example.flickrapp;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class GetImageOnClickListener implements View.OnClickListener {
    static final String TAG = "GetImageOnClick";
    String urlAddress;
    ImageView iv;

    public GetImageOnClickListener(ImageView iv) {
        this.urlAddress = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";
        this.iv = iv;
    }

    @Override
    public void onClick(View v) {
        if (iv != null) {
            Bitmap bmp = null;
            try {
                bmp = getImage(getImageLink(0));
            } catch (ExecutionException | InterruptedException | TimeoutException | JSONException e) {
                e.printStackTrace();
            }
            if (bmp != null) {
                Bitmap bmpDisplay = Bitmap.createScaledBitmap(bmp, 600, 600, true);
                iv.setImageBitmap(bmpDisplay);
                Log.i(TAG, "Bitmap displayed");
                return;
            }
            Log.e(TAG, "No Bitmap to display");
        }
    }

    public JSONObject getImageList(String link) throws InterruptedException, ExecutionException, TimeoutException {
        AsyncFlickrJSONData task = (AsyncFlickrJSONData) new AsyncFlickrJSONData().execute(urlAddress);
        JSONObject images = task.get();
        return images;
    }

    public String getImageLink(int index) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        String link = getImageList(urlAddress).getJSONArray("items").getJSONObject(index).getJSONObject("media").getString("m");
        // String link = getImageList(urlAddress).getJSONArray("items").getJSONObject(index).getString("link");
        return link;
    }

    public Bitmap getImage(String link) throws ExecutionException, InterruptedException {
        AsyncBitmapDownloader downloader = (AsyncBitmapDownloader) new AsyncBitmapDownloader().execute(link);
        Bitmap map = downloader.get();
        return map;
    }
}
