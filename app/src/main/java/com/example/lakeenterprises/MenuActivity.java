package com.example.lakeenterprises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void recordings(View v){
        Intent intent = new Intent(this, RecordingsActivity.class);
        startActivity(intent);
    }
    public void home(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void settings(View v){
        Intent intent = new Intent(this, NotifSettings.class);
        startActivity(intent);
    }

}
