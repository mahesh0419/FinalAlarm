package com.example.finalalarm;

public class AlarmData {
	
	int id;
	String date;
	String hour;
	String minute;
	String songFile;
	AlarmData()
	{
		
	}
	
	AlarmData(int id,String date,String hour,String minute,String songFile)
	{
		this.id=id;
		this.date=date;
		this.hour=hour;
		this.minute=minute;
		this.songFile=songFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getSongFile() {
		return songFile;
	}

	public void setSongFile(String songFile) {
		this.songFile = songFile;
	}
	

}
