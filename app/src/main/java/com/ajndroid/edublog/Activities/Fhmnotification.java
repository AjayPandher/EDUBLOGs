package com.ajndroid.edublog.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ajndroid.edublog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Fhmnotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fhmnotification);
   if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
       NotificationChannel channel =
               new NotificationChannel("myNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
       NotificationManager manager = getSystemService(NotificationManager.class);
       manager.createNotificationChannel(channel);
   }
        FirebaseMessaging.getInstance().subscribeToTopic("General")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "succesfull";
                        if (!task.isSuccessful()) {
                            msg = "unsuccesfull";
                        }
                        Toast.makeText(Fhmnotification.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
