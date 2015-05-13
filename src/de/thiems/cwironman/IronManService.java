package de.thiems.cwironman;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class IronManService extends NotificationListenerService {

	private String TAG = this.getClass().getSimpleName();

	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		if (sbn.getPackageName().equals("com.whatsapp")) {
			Log.i(TAG, "**********  onNotificationPosted");
			Log.i(TAG,
					"ID :" + sbn.getId() + "\t"
							+ sbn.getNotification().tickerText + "\t"
							+ sbn.getPackageName());
			Intent i = new Intent("de.thiems.cwironman.IronManService");
			i.putExtra("notification_event",
					"onNotificationPosted :" + sbn.getPackageName() + "\n");
			sendBroadcast(i);
		}
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {
		if (sbn.getPackageName().equals("com.whatsapp")) {
			Log.i(TAG, "********** onNOtificationRemoved");
			Log.i(TAG,
					"ID :" + sbn.getId() + "\t"
							+ sbn.getNotification().tickerText + "\t"
							+ sbn.getPackageName());
			Intent i = new Intent("de.thiems.cwironman.IronManService");
			i.putExtra("notification_event",
					"onNotificationRemoved :" + sbn.getPackageName() + "\n");
			sendBroadcast(i);
		}
	}
}
