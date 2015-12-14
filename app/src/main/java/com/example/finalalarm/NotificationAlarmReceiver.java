package com.example.finalalarm;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.PowerManager;
import android.widget.Toast;


public class NotificationAlarmReceiver extends BroadcastReceiver{
	Context mContext;	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		this.mContext=context;
		String songFile=intent.getStringExtra("songFile");
		int rowId=intent.getIntExtra("rowId", 0);
		String msg="Alla is Calling";
		
		System.out.println(songFile +" f "+ rowId);
		Intent i=new Intent(context,DialogActivity.class);
		i.putExtra("songFile", songFile);
		i.putExtra("rowId", rowId);
		 Notification n = new Notification(R.drawable.fislamic, msg, System.currentTimeMillis());
         PendingIntent pi = PendingIntent.getActivity(context, rowId, i, 0);
         System.out.println("rowId:"+rowId+" songFile: "+songFile+" Alarm reciver");
         
         n.setLatestEventInfo(context, "Remind Me", msg, pi);
         // TODO check user preferences
         n.defaults |= Notification.DEFAULT_VIBRATE;
         n.defaults |= Notification.DEFAULT_SOUND;       
         n.flags |= Notification.FLAG_AUTO_CANCEL;       
          
         NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
         nm.notify((int)rowId, n);
         
         
		//context.startService(new Intent(context, MyAlarmService.class));
	}









}




