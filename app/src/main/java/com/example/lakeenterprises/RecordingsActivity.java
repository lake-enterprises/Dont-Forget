package com.example.lakeenterprises;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RecordingsActivity extends AppCompatActivity {
    Button btnRecord, btnStopRecord, btnPlay, btnStop, btnSave;
    String pathSave="";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private final String TAG = "RecordingsActivity";
    SharedPreferences pref;
    private String group;

    final int REQUEST_PERMISSION_CODE= 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        group=pref.getString("GroupName", "");

        //Request runtime permissions
        if(!checkPermissionFromDevice())
            requestPermission();

        //Init View
//        btnPlay= (Button)findViewById(R.id.play);
//        btnRecord= (Button)findViewById(R.id.record);
        btnSave=(Button)findViewById(R.id.save);
        mStorage= FirebaseStorage.getInstance().getReference();
        mProgress=new ProgressDialog(this);

        ToggleButton toggleRecord = (ToggleButton) findViewById(R.id.record);
        toggleRecord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    if(checkPermissionFromDevice()){

                        pathSave= Environment.getExternalStorageDirectory().getPath()+"/"+ UUID.randomUUID().toString()+"_audio_record.3gp";
                        setupMediaRecorder();
                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(RecordingsActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        requestPermission();
                    }
                } else {
                    // The toggle is disabled
                    mediaRecorder.stop();
                }
            }
        });

        ToggleButton togglePlay = (ToggleButton) findViewById(R.id.play);
        togglePlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    mediaPlayer=new MediaPlayer();
                    try{
                        mediaPlayer.setDataSource(pathSave);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    Toast.makeText(RecordingsActivity.this, "Playing...", Toast.LENGTH_SHORT).show();
                } else {
                    // The toggle is disabled
                    if (mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setupMediaRecorder();
                    }
                }
            }
        });
//            btnRecord.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(checkPermissionFromDevice()){
//
//                    pathSave= Environment.getExternalStorageDirectory().getPath()+"/"+ UUID.randomUUID().toString()+"_audio_record.3gp";
//                    setupMediaRecorder();
//                    try {
//                        mediaRecorder.prepare();
//                        mediaRecorder.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    btnStopRecord.setEnabled(true);
//                    btnPlay.setEnabled(false);
//                    btnStop.setEnabled(false);
//                    btnSave.setEnabled(false);
//                    btnRecord.setEnabled(false);
//
//                    Toast.makeText(RecordingsActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        requestPermission();
//                    }
//                }
//            });
//            btnStopRecord.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mediaRecorder.stop();
//                    btnStopRecord.setEnabled(false);
//                    btnPlay.setEnabled(true);
//                    btnRecord.setEnabled(true);
//                    btnStop.setEnabled(false);
//                    btnSave.setEnabled(true);
//                }
//            });
//            btnPlay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btnStop.setEnabled(true);
//                    btnStopRecord.setEnabled(false);
//                    btnRecord.setEnabled(false);
//                    btnSave.setEnabled(true);
//
//                    mediaPlayer=new MediaPlayer();
//                    try{
//                        mediaPlayer.setDataSource(pathSave);
//                        mediaPlayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    Toast.makeText(RecordingsActivity.this, "Playing...", Toast.LENGTH_SHORT).show();
//                }
//            });
//        btnStop.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btnRecord.setEnabled(true);
//                    btnStopRecord.setEnabled(false);
//                    btnStop.setEnabled(false);
//                    btnPlay.setEnabled(true);
//                    btnSave.setEnabled(true);
//
//                    if (mediaPlayer != null){
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        setupMediaRecorder();
//                    }
//
//                }
//            });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setMessage("Uploading Audio...");
                mProgress.show();
                Log.d(TAG, "In progress bar is displayed");
                StorageReference filepath=mStorage.child("Audio").child(group).child(pathSave);
                Log.d(TAG, "StorageRef created");
                Uri uri=Uri.fromFile(new File(pathSave));
                Log.d(TAG, "URI and file created");
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "file added successfully");
                        mProgress.dismiss();
                    }
                });
            }
        });


    }

    private void setupMediaRecorder() {
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:{
                if (grantResults.length> 0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice() {
        int write__external_storage_result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_results= ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write__external_storage_result== PackageManager.PERMISSION_GRANTED &&
                record_audio_results ==PackageManager.PERMISSION_GRANTED;

    }

    public void newRecording(){
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        MediaPlayer mediaPlayer=new MediaPlayer();


    }
    public void menu(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
