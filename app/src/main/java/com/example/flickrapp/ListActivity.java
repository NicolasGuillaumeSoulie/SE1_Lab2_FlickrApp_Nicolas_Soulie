package com.example.flickrapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends Activity {
    ListView list;
    Button backButton;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new MyAdapter(this);
        list = findViewById(R.id.list);
        backButton = findViewById(R.id.backButton);
        list.setAdapter(adapter);
        AsyncFlickrJSONDataForList getImages = new AsyncFlickrJSONDataForList(adapter);
        getImages.execute(getString(R.string.app_image_url));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });

    }
}
