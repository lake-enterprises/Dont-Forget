package com.example.lakeenterprises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RecordingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);
    }
    public void newRecording(){

    }
    public void menu(View v){
        Intent intent=new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
