package android.reminder.controllers.new_action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.reminder.controllers.R;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String content = intent.getStringExtra("content");
        String channelId = intent.getStringExtra("channelId");
        int notificationId = intent.getIntExtra("notificationId", 0);
        Log.d("notificationId", Integer.toString(notificationId));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(notificationId, builder.build());
    }
}
