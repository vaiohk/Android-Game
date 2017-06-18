package com.example.brian.memorableplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Create array list to store and access the final locations
    static ArrayList<String> places = new ArrayList<>();

    //arraylist to store the memorable location
    static ArrayList<LatLng> locations = new ArrayList<>();

    static  ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables
        ListView listView = (ListView) findViewById(R.id.PlaceView);



        //Initial place
        places.add("Add a new place");
        locations.add(new LatLng(0,0));

        //Array adapter : creates a view by calling toString()
        //on each data object in the collection you provide,
        // and places the result in a TextView.
         arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,places);

        //Set adapter
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                //Toast.makeText(MainActivity.this, i, Toast.LENGTH_SHORT).show();

                //create the jump to map activity when user click on the item
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                //Put extra data with Intent
                intent.putExtra("placeNumber",i);

                startActivity(intent);
            }
        });
    }
}
