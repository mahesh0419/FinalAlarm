package com.example.finalalarm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;






import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ResultActivity extends Activity {

	static MediaPlayer mPlayer;
	EditText alarmText;
	private static final String AUDIO_FOLDER_NAME="audioCheck";
	AppStatus  app=new AppStatus();
	String temp;
	String saveUrl;
	String playPath; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		
		Intent i=getIntent();
		String text=i.getStringExtra("text");
		String song=i.getStringExtra("song");
		 temp=i.getStringExtra("temp");
        playPath=song;
		alarmText=(EditText) findViewById(R.id.alarm_text);
		alarmText.setText(text);
		
		if(searchSongInSdcard(getFilePath(),temp))
		{
			play(song);
		}
		else
		{
			if(connected())

			{
				 
				saveUrl=app.findUrl(temp);
				 System.out.println("save : "+saveUrl);
				alertToDownload(saveUrl,temp);
				
			}
			else
			{
				
				Toast.makeText(getApplicationContext(), "please connect the interent for download", Toast.LENGTH_SHORT).show();
				alertCheckInternet();
			}
		}
		
		
		System.out.println("final text \n"+text );
		System.out.println("song path \n"+song );
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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

	public void play(String path)
	{
		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			
			mPlayer.setDataSource(path);
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
		} catch (SecurityException e) {
			Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
		} catch (IOException e) {




			//wnloadfilesongAsyncTask.execute(streamSongUrl);
			Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
		}
		mPlayer.start();
	}

	public void stop()
	{
		if(mPlayer!=null && mPlayer.isPlaying()){
			mPlayer.stop();
		}
	}

	public void pause()
	{
		if(mPlayer!=null && mPlayer.isPlaying()){
			mPlayer.pause();
		}
	}
	
	
	
	public boolean searchSongInSdcard(String path,String searchKey)
	{
		boolean flag=false;
		String key=searchKey;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				if(listOfFiles[i].getName().toString().equals(key))
				{

					System.out.println("inside if above flag "+listOfFiles[i].getAbsolutePath());
					//flag=listOfFiles[i].getAbsolutePath();
					flag=true;
					break;
				}
				else
				{
					System.out.println(key+" k "+listOfFiles[i].getName().toString().equals(key));
				}
			} 
		}
		return flag;
	}
	
	public String  getFilePath()
	{
		File SDCardRoot =new File(Environment.getExternalStorageDirectory()+"/"+AUDIO_FOLDER_NAME+"/");

		return SDCardRoot.toString();
	}
	public boolean  connected()
	{
		Boolean FLAG = false;
		if (AppStatus.getInstance(this).isOnline()) {

			FLAG=true;

		} else {

			FLAG=false; 
		}

		return FLAG;

	}
	
	public void alertToDownload(String url,String fileName)
	{
		
		System.out.println("UR: "+url+" filename :"+fileName);
		final	String Lurl=url;
		final	String LfileName=fileName;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Download Duaa......");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want download this?");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.fislamic);

		// Setting Positive "Yes" Buttonif(!connected())
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {

				// Write your code here to invoke YES event
				app.Download(Lurl,LfileName);
				Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
				play(playPath);
			}
		});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to invoke NO event
				Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});
		
		alertDialog.setNeutralButton("Only TEXT", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to invoke NO event
				Toast.makeText(getApplicationContext(), "You clicked on Only TEXT", Toast.LENGTH_SHORT).show();
				//dialog.cancel();
			}
		});
		alertDialog.show();
}
	
	
	
	
	
	protected void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub

		if (mPlayer != null) {
		mPlayer.release();
		mPlayer = null;
		}
		}
	
	
	public void alertCheckInternet()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Turn on WI_FI data......");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want download this?");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.fislamic);

		// Setting Positive "Yes" Buttonif(!connected())
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {

				// Write your code here to invoke YES event
				//Download(saveUrl,audioFileName);
				Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));

			}
		});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to invoke NO event
				Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();


	}
	
	
	}
