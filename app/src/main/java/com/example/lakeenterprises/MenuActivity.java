package com.example.lakeenterprises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * allows user to access home, recordings, and settings pages
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /**
     * takes user to recordings page
     * @param v GUI component that connects to the recordings button
     */
    public void recordings(View v){
        Intent intent = new Intent(this, RecordingsActivity.class);
        startActivity(intent);
    }

    /**
     * takes user to home page
     * @param v GUI component that connects to the home button
     */
    public void home(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * takes user to settings page
     * @param v GUI component that connects to the settings button
     */
    public void settings(View v){
        Intent intent = new Intent(this, NotifSettings.class);
        startActivity(intent);
    }

}
