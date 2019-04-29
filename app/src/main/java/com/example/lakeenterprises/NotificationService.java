package com.example.lakeenterprises;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends Service {
    private final String TAG = "NotificationService";
    private double distance;
    private DatabaseReference mDatabase;
    private int min;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "outofrange")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Out of Range")
                .setContentText("Warning, user has exceeded a safe distance from their walker")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Warning, user has exceeded a safe distance from their walker"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        createNotificationChannel();
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        min=pref.getInt("user", 0);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        // Read from the database
        mDatabase.child("pi").child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                distance = Double.valueOf(dataSnapshot.getValue().toString());
                Log.d(TAG, "Value is: " + distance);
                if (distance < min) {
                    notificationManager.notify(1, builder.build());

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Walker Notification";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyid", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;

    }

}
