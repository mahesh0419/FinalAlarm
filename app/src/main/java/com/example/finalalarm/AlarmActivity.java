package com.example.finalalarm;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AlarmActivity extends Activity {

	String[] mDate;
	String[] mMinute;
	String[] mHour;
	String[] mSongFile;
	SimpleDateFormat format;
	private AlarmManager alarmManager ;
	
	ArrayList<PendingIntent>  pending;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		 alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		 pending=new ArrayList<PendingIntent>();
		 
		 
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		 if(!prefs.getBoolean("firstTime", false)) {
		     // run your one time code
			 insertDateTime();
			 this.startService(new Intent(this, MyAlarmService.class));
		     SharedPreferences.Editor editor = prefs.edit();
		     editor.putBoolean("firstTime", true);
		     editor.commit();
		 }
		 
		 
		
		 
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void insertDateTime()
	{
		AlarmDb db=new AlarmDb(this);
		List<AlarmData> list=new ArrayList<AlarmData>();

		mDate=getResources().getStringArray(R.array.date);
		mHour=getResources().getStringArray(R.array.hour);
		mMinute=getResources().getStringArray(R.array.minute);
		mSongFile=getResources().getStringArray(R.array.songFile);

		int size=mDate.length;
		for(int i=0;i<size;i++)
		{
			long flag=db.insertAlarm(new AlarmData(1,mDate[i],mHour[i],mMinute[i],mSongFile[i]));
			if(flag!=0)
			{
				System.out.println("sucess");
			}
			else
			{
				System.out.println("Fail");
			}

		}

		
	}
	
	
	
	
}
