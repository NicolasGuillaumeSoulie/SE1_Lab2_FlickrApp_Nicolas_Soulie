package com.example.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

/**
 * A custom adapter to deal with image lists.
 * Contain said image links.
 */
public class MyAdapter extends BaseAdapter {
    static final String TAG = "MyAdapter";
    Vector<String> vector;
    Context context;

    /**
     * @param context the activity context
     *                Constructor
     */
    public MyAdapter(Context context) {
        super();
        vector = new Vector<String>();
        this.context = context;
    }

    /**
     * @param url the image url
     *            Add an url to the image list (vector)
     */
    public void add(String url) {
        vector.add(url/*.replace("\\", "")*/);
        Log.i("JFL", "Adding to adapter url : " + url);
    }

    /**
     * @return the number of image url in the list (vector)
     */
    @Override
    public int getCount() {
        return vector.size();
    }

    /**
     * @param position
     * @return the url at a certain position
     */
    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Request for the image to be downloaded in an image View.
     * Add the ImageView to the GUI list.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return the updated view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgV = new ImageView(context);
        Log.i(TAG, "TODO : " + vector.get(position));

        // Prepare to add the image to the view once the response to the request arrives
        Response.Listener<Bitmap> rep_listener = response -> {
            imgV.setImageBitmap(response);
        };

        // Prepare a request to download the image
        ImageRequest imgRqst = new ImageRequest(vector.get(position),
                rep_listener,
                0, 0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                null);

        // Add the request to the queue
        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(imgRqst);

        return imgV;
        /*
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.textviewlayout, parent, false);
            Log.i(TAG, "view null, now " + (convertView==null));
        }
        // Lookup view for data population
        TextView imageUrl = (TextView) convertView.findViewById(R.id.textViewLink);
        // Populate the data into the template view using the data object
        imageUrl.setText(vector.get(position));
        Log.i(TAG, "TODO : " + vector.get(position) + " | text : " + imageUrl.getText());
        // Return the completed view to render on screen
        return convertView;
    */
    }

}
