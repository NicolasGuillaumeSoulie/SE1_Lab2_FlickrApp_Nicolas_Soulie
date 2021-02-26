package com.example.flickrapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * An activity holding a list of images
 */
public class ListActivity extends Activity {
    // View element references
    ListView list;
    Button backButton;

    // List adapter
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new MyAdapter(this);

        // Prepare the image list
        list = findViewById(R.id.list);
        list.setAdapter(adapter);

        // Request for the images
        AsyncFlickrJSONDataForList getImages = new AsyncFlickrJSONDataForList(adapter);
        getImages.execute(getString(R.string.app_image_url));

        // Prepare the back button (return to MainActivity)
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });

    }
}
