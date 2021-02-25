package com.example.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    static final String TAG = "MyAdapter";
    Vector<String> vector;
    Context context;

    public MyAdapter(Context context) {
        super();
        vector = new Vector<String>();
        this.context = context;
    }

    public void add(String url) {
        vector.add(url/*.replace("\\", "")*/);
        Log.i("JFL", "Adding to adapter url : " + url);
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtV = new TextView(context);
        ImageView imgV = new ImageView(context);
        txtV.setText(vector.get(position));
        Log.i(TAG, "TODO : " + vector.get(position) + " | text : " + txtV.getText());

        Response.Listener<Bitmap> rep_listener = response -> {
            imgV.setImageBitmap(response);
        };

        ImageRequest imgRqst = new ImageRequest(vector.get(position),
                rep_listener,
                0, 0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                null);

        MySingleton.getInstance(context).addToRequestQueue(imgRqst);

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
