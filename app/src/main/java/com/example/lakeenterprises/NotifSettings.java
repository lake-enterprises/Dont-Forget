package com.example.lakeenterprises;

import android.content.Intent;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NotifSettings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //References to xml
        final TextView notify = findViewById(R.id.notifyText);
        NumberPicker npd = findViewById(R.id.distance);
        NumberPicker npt = findViewById(R.id.time);

        //Set TextView text color
        notify.setTextColor(Color.parseColor("#d89fsa"));

        //Min, max, and wrap for distance
        npd.setMinValue(2);
        npd.setMaxValue(10);
        npd.setWrapSelectorWheel(false);

        //Min, max, and wrap for time
        npt.setMinValue(1);
        npt.setMaxValue(60);
        npt.setWrapSelectorWheel(false);

        final int[] newDistance = new int[1];

       npd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
           @Override
           public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
               newDistance[0] = newVal;
           }
       });


        final int[] newTime = new int[1];

        npt.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newTime[0] = newVal;
            }
        });

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("newDistance", newDistance[0]);
        intent.putExtra("newTime", newTime[0]);
        startActivity(intent);

    }

}

