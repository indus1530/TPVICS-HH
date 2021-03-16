package edu.aku.hassannaqvi.tpvics_hh.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import edu.aku.hassannaqvi.tpvics_hh.R;

public final class NotificationUtils {

    private final int NOTIFICATION_ID;
    private static NotificationManager notificationManager;
    private final Context mContext;

    public NotificationUtils(Context context, int id) {
        NOTIFICATION_ID = id;
        mContext = context;
        if (notificationManager != null) return;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(context.getPackageName(), context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displayNotification(String title, String task) {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext, "scrlog")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.tpvics_logo);

        /*int curProgress = 0;
        notification.setProgress(length, curProgress, false);*/

        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }

}
