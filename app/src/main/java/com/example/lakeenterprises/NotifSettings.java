package com.example.lakeenterprises;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.view.View;

public class NotifSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_settings);

        //Get the widgets reference from XML layout
        NumberPicker np = findViewById(R.id.picker);
//
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);
//
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(false);
//
//        //Set a value change listener for NumberPicker
//        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
//                //Display the newly selected number from picker
//                notify.setText(getString(R.string.npdistance));
//            }
//        });
    }

            public void menu(View v){
                Intent intent=new Intent(this, MenuActivity.class);
                startActivity(intent);
            }

}

