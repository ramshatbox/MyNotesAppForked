package com.example.api.ramsha.mynotes.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.api.ramsha.mynotes.R;

public class Notification {

    private NotificationManager notificationManager;
    private String channelId;

    public void createNotificationChannel(Context context, String channelId, String channelName) {
        this.channelId=channelId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(Context context, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, this.channelId)
                    .setSmallIcon(R.drawable.baseline_edit_24)
                    .setContentTitle("MyNotes")
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManager.notify(0, builder.build());
        Toast.makeText(context, "build", Toast.LENGTH_SHORT).show();
    }
}
