package com.example.lakeenterprises;

import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ArrayList<Double> distances = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //displays the distance
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("distance");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
        Random random=new Random();
        for (int r=0; r<=10; r++){
            DistanceData distance = new DistanceData(random.nextDouble() + random.nextInt(10));
            distances.add(distance.getDistance());
        }
        double dist= getMostRecent();
        String distText= dist+" ";
        TextView distanceText=findViewById(R.id.distanceText);
        distanceText.setText(distText);
    }
    public void menu(View v){
        Intent intent=new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public double getMostRecent(){
        return distances.get(distances.size()-1);
    }


}
