package com.example.finalalarm;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDb extends SQLiteOpenHelper {

	private static final String ALARM_DATABASE="alarmDatabase";
	private static final String ALARM_TABLE="alarmTable";
	
	private static final String COL1_ALARM_ID="id";
	private static final String COL2_DATE="date";
	private static final String COL3_HOUR="hour";
	private static final String COL4_MINUTE="minute";
	private static final String COL5_SONG_FILE="songFileName";
	
	
	private static final int DATABASE_VERSION=2;
	
	private static final String CREATE_TABLE="create table "+ALARM_TABLE+"("+COL1_ALARM_ID+" INTEGER PRIMARY KEY , "+COL2_DATE+" TEXT ,"+COL3_HOUR+" TEXT ,"+COL4_MINUTE+" TEXT ,"+COL5_SONG_FILE+" TEXT "+")" ;
	
	public AlarmDb(Context context) {
		super(context, ALARM_DATABASE, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL(CREATE_TABLE);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXIST "+ALARM_TABLE);
		onCreate(db);
	}
	
	public long insertAlarm(AlarmData alarm)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(COL2_DATE, alarm.getDate());
		values.put(COL3_HOUR, alarm.getHour());
		values.put(COL4_MINUTE, alarm.getMinute());
		values.put(COL5_SONG_FILE, alarm.getSongFile());
		
		long dbb=db.insert(ALARM_TABLE, null, values);
		db.close();
		
		return dbb;
	}
	
	public List<AlarmData> retriveAlarm()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		List<AlarmData>  alarmList=new ArrayList<AlarmData>();
		
		String alarmData="select * from "+ALARM_TABLE;
		
		Cursor cursor=db.rawQuery(alarmData, null);
		if(cursor.moveToFirst())
		{
			do
			{
				AlarmData alarm=new AlarmData();
				alarm.setId(Integer.parseInt(cursor.getString(0))); // 0,1,2,3,4
				alarm.setDate(cursor.getString(1));
				alarm.setHour(cursor.getString(2));
				alarm.setMinute(cursor.getString(3));
				alarm.setSongFile(cursor.getString(4));
				alarmList.add(alarm);
				
			}while(cursor.moveToNext());
		}
		
		return alarmList;
	}

}
