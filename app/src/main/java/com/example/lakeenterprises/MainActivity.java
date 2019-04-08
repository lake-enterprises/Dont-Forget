package com.example.lakeenterprises;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private double distance;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        // Read from the database
        mDatabase=mDatabase.child("pi").child("data");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                distance= Integer.valueOf(dataSnapshot.getValue().toString());
                Log.d(TAG, "Value is: " + distance);
                if(distance<15){
                    TextView distanceText= findViewById(R.id.distanceText);
                    distanceText.setTextColor(Color.parseColor("#FF032D"));
                    distanceText.setText("Red- out of range or check battery");


                }
                else if(distance>=15&&distance<=40){
                    TextView distanceText= findViewById(R.id.distanceText);
                    distanceText.setTextColor(Color.parseColor("#F9C723"));
                    distanceText.setText("Yellow- keep an eye out, danger could be imminent");

                }
                else if(distance>40){
                    TextView distanceText= findViewById(R.id.distanceText);
                    distanceText.setTextColor(Color.parseColor("#2AE13D"));
                    distanceText.setText("Green- all good in this neighborhood");

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
    public void menu(View v){
        Intent intent=new Intent(this, MenuActivity.class);
        startActivity(intent);
    }



}
