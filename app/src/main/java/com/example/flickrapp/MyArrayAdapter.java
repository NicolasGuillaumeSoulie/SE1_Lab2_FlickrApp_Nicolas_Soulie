package com.example.flickrapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Vector;

public class MyArrayAdapter extends ArrayAdapter<String> {
    static final String TAG = "MyAdapter";
    Vector<String> vector;

    public MyArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        vector = new Vector<String>();
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
    public String getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.textviewlayout, parent, false);
        }
        // Lookup view for data population
        TextView imageUrl = (TextView) convertView.findViewById(R.id.textViewLink);
        // Populate the data into the template view using the data object
        imageUrl.setText(vector.get(position));
        Log.i(TAG, "TODO : " + vector.get(position));
        // Return the completed view to render on screen
        return convertView;
    }
}
