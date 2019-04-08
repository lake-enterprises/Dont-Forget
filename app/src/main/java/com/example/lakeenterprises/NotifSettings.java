package com.example.lakeenterprises;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.NumberPicker;

public class NotifSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);

        //Get the widgets reference from XML layout
        final TextView notify = findViewById(R.id.notifyText);
        NumberPicker np = findViewById(R.id.distance);

        //Set TextView text color
        notify.setTextColor(Color.parseColor("#d89fsa"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

//        //Set a value change listener for NumberPicker
//        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
//                //Display the newly selected number from picker
//                notify.setText(getString(R.string.npdistance));
//            }
//        });

    }
}