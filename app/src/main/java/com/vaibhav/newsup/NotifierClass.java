package com.vaibhav.newsup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotifierClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context,Intent intent) {

        Uri alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent intent1 = new Intent(context,MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent intent2 = taskStackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("my_channel_01","hello", NotificationManager.IMPORTANCE_HIGH);
        }

        Notification notification = null;
       byte[] bytes= intent.getByteArrayExtra("image");
       if(bytes!=null&&bytes.length!=0) {
           Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

           notification = builder.setContentTitle("NewsUp")
                   .setContentText(intent.getStringExtra("title")).setAutoCancel(true)
                   .setSound(alarmsound).setSmallIcon(R.mipmap.ic_launcher_round)
                   .setContentIntent(intent2)
                   .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                   .setChannelId("my_channel_01")
                   //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                   .build();

           NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               notificationManager.createNotificationChannel(channel);
           }
           notificationManager.notify(100, notification);
       }

    }
}
