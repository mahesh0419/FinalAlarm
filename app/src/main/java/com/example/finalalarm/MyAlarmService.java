package com.example.finalalarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.IBinder;

import android.util.Log;
import android.widget.Toast;



public class MyAlarmService extends Service {


	Context mContext;
	String[] mDate;
	String[] mMinute;
	String[] mHour;
	String[] mSongFile;
	SimpleDateFormat format;
	private AlarmManager alarmManager ;

	ArrayList<PendingIntent>  pending;

	@Override

	public void onCreate() {

		// TODO Auto-generated method stub

		Toast.makeText(this, " service MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();



	}



	@Override

	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub

		Toast.makeText(this, "service MyAlarmService.onBind()", Toast.LENGTH_LONG).show();

		return null;

	}



	@Override

	public void onDestroy() {

		// TODO Auto-generated method stub

		super.onDestroy();

		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

	}



	@Override

	public void onStart(Intent intent, int startId) {

		// TODO Auto-generated method stub


		super.onStart(intent, startId);
		Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
		//alertDialog();
		Toast.makeText(this, "Dead Alarm  SERVICE in on Start",Toast.LENGTH_LONG).show();
		format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		pending=new ArrayList<PendingIntent>();

		//insertDateTime();
		retriveAlarm();
	}



	@Override

	public boolean onUnbind(Intent intent) {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();

		return super.onUnbind(intent);

	}

	


	



	public void setReminder(int reminderId, long when , String songFile) {

		long oldAlarm=getCurrentTimeStamp();

		if(when>oldAlarm)
		{
			Toast.makeText(this, "Dead Alarm  SERVICE when",Toast.LENGTH_LONG).show();

			Intent i = new Intent(this, NotificationAlarmReceiver.class);
			i.putExtra("songFile", songFile); 
			i.putExtra("rowId", reminderId);
			PendingIntent pi = PendingIntent.getBroadcast(this,reminderId, i, PendingIntent.FLAG_UPDATE_CURRENT); 
			alarmManager.set(AlarmManager.RTC_WAKEUP, when, pi);
			System.out.println("rowId:"+reminderId+" songFile: "+songFile+" Alar Activity inside method");
			System.out.println("pi  "+pi);
			pending.add(pi);
		}
		else
		{
			Toast.makeText(this, "Dead Alarm  SERVICE out ",Toast.LENGTH_LONG).show();

			System.out.println("Alarm out dated");
		}
	}


	public  long getCurrentTimeStamp() {
		Toast.makeText(this, "Dead Alarm  SERVICE SDF",Toast.LENGTH_LONG).show();
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		long millis=0;
		try {
			millis = sdfDate.parse(strDate).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return millis;
	}

	public void retriveAlarm()
	{
		AlarmDb db=new AlarmDb(this);
		List<AlarmData> list=new ArrayList<AlarmData>();
		list=db.retriveAlarm();
		int requestCode=0;
		
		for (AlarmData cn : list) 
		{

			String log = "Id: "+cn.getId()+" ,DATE: " + cn.getDate() + " ,HOUR: " + cn.getHour()+" , minute: "+cn.getMinute()+" , songFile: "+cn.getSongFile();
			// Writing Contacts to log

			String time=cn.getDate()+" "+cn.getHour()+":"+cn.getMinute();
			Log.d("Alarm Name: ", log);
			Log.d("Alarm time: ", time);
			try {
				long millis = format.parse(time).getTime();
				long rowId=Long.valueOf(cn.getId());
				int tempId=cn.getId();
				String songFile=cn.getSongFile();
				System.out.println("rowId:"+rowId+" songFile: "+songFile+" Alarm Activity");
				System.out.println(" ID: "+tempId);
				setReminder(tempId,millis,songFile);
				requestCode=requestCode+1;

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Exception in parsing date int miliseconds");
				e.printStackTrace();
			}

		}

	}

}