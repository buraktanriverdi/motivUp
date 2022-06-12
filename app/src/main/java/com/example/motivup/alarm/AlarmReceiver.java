package com.example.motivup.alarm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.motivup.MainActivity;
import com.example.motivup.R;

public class AlarmReceiver extends BroadcastReceiver {

    //public static String NOTIFICATION_ID = "notification-id" ;
    //public static String NOTIFICATION = "hele hele" ;
    @Override
    public void onReceive(Context context, Intent intent) {

         /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
              NotificationChannel channel =  new NotificationChannel("10001","Motiv Up",NotificationManager.IMPORTANCE_DEFAULT);
              NotificationManager manager = context.getSystemService(NotificationManager.class);
              manager.createNotificationChannel(channel) ;
         }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "10001");
        builder.setContentTitle("MotivUP");
        builder.setContentText("Günlük hedeflerinizi kontrol edelim");
        builder.setSmallIcon(R.drawable.ic_baseline_check_checked);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build() );*/

    }
}
