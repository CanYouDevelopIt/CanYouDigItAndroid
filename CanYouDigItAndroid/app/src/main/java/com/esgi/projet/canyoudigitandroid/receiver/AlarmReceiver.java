package com.esgi.projet.canyoudigitandroid.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.esgi.projet.canyoudigitandroid.MainActivity;
import com.esgi.projet.canyoudigitandroid.R;
import com.esgi.projet.canyoudigitandroid.fragment.TimePickerFragment;

/**
 * Created by Rémy on 01/07/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("onReceive", "ICI");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icone)
                        .setContentTitle(intent.getStringExtra("rappel"))
                        .setContentText(intent.getStringExtra("titre"))
                        .setContentIntent(contentIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }
}
