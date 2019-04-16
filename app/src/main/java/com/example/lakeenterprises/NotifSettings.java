package com.example.lakeenterprises;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.view.View;

import java.util.UUID;

public class NotifSettings extends Activity {

    private static final String TAG = "NotifSettings Java";
    NumberPicker np;
    Button saveSet;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        np = findViewById(R.id.picker);
//        //Get the widgets reference from XML layout
//        NumberPicker np = findViewById(R.id.picker);
//
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);
//
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(false);
        np.setValue(pref.getInt("user",0));



        saveSet = (Button) findViewById(R.id.setCommit);


        saveSet.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                Log.d(TAG, "np is: "+np.getValue());
                editor.putInt("user",np.getValue() );
                editor.apply();

                Log.d(TAG,"set value is: " + pref.getInt("user", 0));
            }
        });
        Log.d(TAG, "pref is:"+ pref.getInt("user", 0));

    }

    public void menu(View v){
        Intent intent=new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}



