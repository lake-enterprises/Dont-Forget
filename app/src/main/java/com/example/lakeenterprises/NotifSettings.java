package com.example.lakeenterprises;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * Activity to set up distance threshhold for notifications and audio messages
 */
public class NotifSettings extends Activity {

    private static final String TAG = "NotifSettings Java";
    NumberPicker np;
    NumberPicker npi;
    Button saveSet;
    SharedPreferences pref;
    DatabaseReference databaseReference;
    Switch aSwitch;
    String group;
    private Toast toast;

    /**
     * Creates page with number pickers for each setting
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        group=pref.getString("GroupName", "");

        np = findViewById(R.id.picker);
        npi=findViewById(R.id.piPicker);
//        //Get the widgets reference from XML layout
//
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        npi.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);
        npi.setMaxValue(20);
//
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(false);
        npi.setWrapSelectorWheel(false);
        np.setValue(pref.getInt("user",0));
        npi.setValue(pref.getInt("pi", 0));


        //Checks for switch on; if on, then Notification Service is enabled
        aSwitch=(Switch) findViewById(R.id.NotifSwitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                      startService();
                } else {
                    stopService();

                }
            }
        });


        //Save settings button
        saveSet = (Button) findViewById(R.id.setCommit);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        saveSet.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                Log.d(TAG, "np is: "+np.getValue());
                editor.putInt("user",np.getValue());
                editor.putInt("pi", -npi.getValue());
                editor.apply();

                Log.d(TAG,"set value is: " + pref.getInt("user", 0));
                databaseReference.child(group).child("settings").child("data").setValue(pref.getInt("pi", 0));
                databaseReference.child(group).child("settings").child("user").setValue(pref.getInt("user", 0));
                toast.makeText(getApplicationContext(), "Settings have been saved", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "pi is:"+ pref.getInt("pi", 0));

    }

    //Takes you back to Menu
    public void menu(View v){
        Intent intent=new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    //starts notification service
    public void startService() {
        startService(new Intent(getBaseContext(), NotificationService.class));
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), NotificationService.class));
    }

}



