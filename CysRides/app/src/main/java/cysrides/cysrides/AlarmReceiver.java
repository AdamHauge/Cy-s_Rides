package cysrides.cysrides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import service.NotificationService;
import service.NotificationServiceImpl;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        notificationService.showRideNotification(context);

    }
}