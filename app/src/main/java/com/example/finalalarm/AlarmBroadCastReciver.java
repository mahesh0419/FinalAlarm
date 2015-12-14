package com.example.finalalarm;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.sax.StartElementListener;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlarmBroadCastReciver extends BroadcastReceiver {

	Context mContext;
	
	private static final int MY_NOTIFICATION_ID=1;
	 NotificationManager notificationManager;
	 Notification myNotification;
	 int notifi=1;
	 ArrayList<String> songFileName;
	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.mContext=context;
		Toast.makeText(context, "broadcast reciver", Toast.LENGTH_LONG).show();
		//context.startService(new Intent(context, MyAlarmService.class));
		String temp=intent.getStringExtra("songFile");
		System.out.println("Song file:  "+temp);
				
		 Intent myIntent = new Intent(context,DialogActivity.class);
        
		// PendingIntent pendingIntent = PendingIntent.getActivity(context, 3, myIntent, 0);

		      
		      myNotification = new Notification.Builder(context)
		        .setContentTitle("Its time to prayer!")
		        .setContentText("Alla calling")
		        .setTicker("Prayer Reminder!")
		       // .setContentIntent(pendingIntent)
		        .setDefaults(Notification.DEFAULT_SOUND)
		        .setAutoCancel(true)
		        .setSmallIcon(R.drawable.fislamic)
		        .build();
		      
		      
		      notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		      notificationManager.notify(notifi, myNotification);
	       
	}
	
	
	
}
