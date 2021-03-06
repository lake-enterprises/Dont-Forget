package com.example.lakeenterprises;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * GroupActivity allows user to join a group connected to a device or create a new one
 */
public class GroupActivity extends AppCompatActivity {
    Button okayButton;
    EditText newGroup;
    EditText joinGroup;
    Intent intent;
    SharedPreferences preferences;

    /**
     * creates the group page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        intent=new Intent(this, MainActivity.class);

        okayButton=(Button) findViewById(R.id.okayButton);
        newGroup=(EditText) findViewById(R.id.createNew);
        joinGroup=(EditText) findViewById(R.id.enterExisting);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);



        okayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                if (!newGroup.getText().toString().isEmpty()){
                    Log.d("GroupActivity", "groupname is"+newGroup.getText().toString().replaceAll("\\s", ""));
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("GroupName", newGroup.getText().toString().replaceAll("\\s", ""));
                    editor.apply();
                    Log.d("GroupActivity", "groupname is"+preferences.getString("GroupName", ""));
                    startActivity(intent);
                }
                else if (!joinGroup.getText().toString().isEmpty()){
                    Log.d("GroupActivity", "groupname is"+joinGroup.getText().toString().replaceAll("\\s", ""));
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("GroupName", joinGroup.getText().toString().replaceAll("\\s", ""));
                    editor.apply();
                    Log.d("GroupActivity", "groupname is"+preferences.getString("GroupName", ""));
                    startActivity(intent);
                }
            }
        });
    }
}
