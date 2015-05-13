package de.thiems.cwironman;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class IronManService extends NotificationListenerService {

	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		if (sbn.getPackageName().equals("com.whatsapp")) {
			Intent i = new Intent("de.thiems.cwironman.IronManService");
			i.putExtra("notification_event",
					"onNotificationPosted :" + sbn.getPackageName() + "\n");
			sendBroadcast(i);
		}
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {
		if (sbn.getPackageName().equals("com.whatsapp")) {
			Intent i = new Intent("de.thiems.cwironman.IronManService");
			i.putExtra("notification_event",
					"onNotificationRemoved :" + sbn.getPackageName() + "\n");
			sendBroadcast(i);
		}
	}
}
