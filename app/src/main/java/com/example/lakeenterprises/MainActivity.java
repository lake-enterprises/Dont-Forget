package com.example.lakeenterprises;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Home page displays the current dnager level and allws the user to enable or disable the sound from the device
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private double distance;
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference;
    private int min;
    private Switch aSwitch;
    private String group;
    private Toast toast;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        min=pref.getInt("user", 0);
        group=pref.getString("GroupName", "");
        Log.d(TAG, "group name is:"+group);
        distance=0.0;


        databaseReference=FirebaseDatabase.getInstance().getReference().child(group).child("settings").child("sound");
        intent=new Intent(this, GroupActivity.class);


        mDatabase= FirebaseDatabase.getInstance().getReference();
        // Read from the database
        mDatabase.child(group).child("pi").child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()==null){
                   toast.makeText(getApplicationContext(), "Group not found", Toast.LENGTH_SHORT).show();
                   startActivity(intent);
                }
                else {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    distance = Double.valueOf(dataSnapshot.getValue().toString());
                    Log.d(TAG, "Value is: " + distance);
                    if (distance < min) {
                        TextView distanceText = findViewById(R.id.distanceText);
                        distanceText.setTextColor(Color.parseColor("#FF032D"));
                        distanceText.setText("Red- out of range or check battery");

                        //notificationManager.notify(1, builder.build());

                    } else if (distance >= min && distance <= 40) {
                        TextView distanceText = findViewById(R.id.distanceText);
                        distanceText.setTextColor(Color.parseColor("#F9C723"));
                        distanceText.setText("Yellow- keep an eye out, danger could be imminent");

                    } else if (distance > 40) {
                        TextView distanceText = findViewById(R.id.distanceText);
                        distanceText.setTextColor(Color.parseColor("#2AE13D"));
                        distanceText.setText("Green- all good in this neighborhood");

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });
        aSwitch=(Switch) findViewById(R.id.soundSwitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    soundOn();
                } else {
                    soundOff();

                }
            }
        });

    }


    /**
     * takes user to the menu page
     * @param v
     */
    public void menu(View v){
        Intent intent=new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void soundOn(){
        databaseReference.setValue(0);
    }

    public void soundOff(){
        databaseReference.setValue(-1);
    }
    public void playSound(View v){
        databaseReference.setValue(1);
    }



}