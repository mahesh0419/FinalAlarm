package com.example.finalalarm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DialogActivity extends Activity {

	//Context mContext;

	//private static final String c
	static MediaPlayer mPlayer;
	File SDCardRoot;
	String filePath;
	String titleText="/jsondata/JsonFile.txt";
	String subTitleText="/jsondata/subJsonFile.txt";
	String childTitleText="/jsondata/childJsonFile.txt";
	StringBuilder mText;
	private  String OBJECT_TITLE;
	private String  OBJECT_ID;
	private String  TITLE_TEXT="title_text";
	private String mFileId;
	String duaId;
	String AbPath;
	String songFile;
	String tempFile;
	private static final String AUDIO_FOLDER_NAME="audioCheck";
	private static final String JSON_FOLDER_NAME="jsondata";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);

		Intent i=getIntent();
		songFile=i.getStringExtra("songFile");
		tempFile=songFile;
		int  rowId=i.getIntExtra("rowId", 0);
		System.out.println("rowId:"+rowId+" songFile: "+songFile+" Dialog activity");
		System.out.println("Dialog activity" +songFile+" row ID: "+rowId);
		duaId=regularExp(songFile);
		filePath=getFilePath();

		System.out.println("----TESTfilePath : "+filePath);

		alertDialog();


	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}*/

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

	public void alertDialog()
	{

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle(" Prayer Time ...");

		// Setting Dialog Message
		alertDialog.setMessage("Do you want to Start Prayer ?");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.fislamic);

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// User pressed YES button. Write Logic Here
				System.out.println("----TESTInsideAlertSongFile : "+songFile);
				new TextSearch().execute(songFile);
			}
		});

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// User pressed No button. Write Logic Here
				Toast.makeText(DialogActivity.this, "You clicked on NO", Toast.LENGTH_SHORT).show();
				
				finish();


			}
		});

		// Setting Netural "Cancel" Button
		/* alertDialog.setNeutralButton("LATER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // User pressed Cancel button. Write Logic Here
            Toast.makeText(DialogActivity.this, "You clicked on later",Toast.LENGTH_SHORT).show();
            }
        });*/

		// Showing Alert Message

		alertDialog.show();

	}

	

	public String  getFilePath()
	{
		File SDCardRoot =new File(Environment.getExternalStorageDirectory()+"/"+AUDIO_FOLDER_NAME+"/");

		return SDCardRoot.toString();
	}
	
	public String  getJsonFilePath()
	{
		File SDCardRoot =new File(Environment.getExternalStorageDirectory()+"/"+JSON_FOLDER_NAME+"/");

		return SDCardRoot.toString();
	}











	private class TextSearch extends AsyncTask<String, String, String>
	{
		String titleTexArabict;
		String mainFile;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String key=params[0];
			final char Title='t';
			final char subTitle='s';
			final char childTitle='c';

			//AbPath=search(filePath,songFile);
			AbPath=stringConcat(filePath,songFile);
			System.out.println("----TESTAbPath : "+AbPath);


			char startingLetter=key.charAt(0);




			switch(startingLetter)
			{
			case Title:  
				System.out.println("----TESTTitle : "+Title);
				OBJECT_TITLE="dua_title";
				OBJECT_ID="title_id";
				mText=readFile(titleText);	
				
				System.out.println("check path: "+mText);
				titleTexArabict=parseMyData(mText.toString());

				break;


			case subTitle:   
				
				System.out.println("----TESTSsTitle : "+subTitle);

				OBJECT_TITLE="dua_sub_title";
				OBJECT_ID="subtitle_id";
				//mText=readFile(subTitleText);	
				mText=readFile(subTitleText);	
				titleTexArabict=parseMyData(mText.toString());

				break;

			case childTitle:
				System.out.println("----TESTCcTitle : "+childTitle);
				OBJECT_TITLE="dua_child_title";
				OBJECT_ID="childtitle_id";
				mText=readFile(childTitleText);
				titleTexArabict=parseMyData(mText.toString());


				break;

			default:
				System.out.println(" Default block guruuuu");
			}
			return null;
		}


		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Intent intent=new Intent(DialogActivity.this,ResultActivity.class);
			intent.putExtra("song", AbPath);
			intent.putExtra("text", titleTexArabict);
			intent.putExtra("temp", tempFile);
			startActivity(intent);
		}

	}


	public StringBuilder readFile(String localJsonFilePath)
	{
		File dir = Environment.getExternalStorageDirectory();
		File file = new File(dir,localJsonFilePath);
		System.out.println("file name: "+file);
		StringBuilder text=null;

		if(file.exists())   // check if file exist
		{
			//Read text from file
			text = new StringBuilder();

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;

				while ((line = br.readLine()) != null) {
					text.append(line);

				}
			}
			catch (IOException e) {
				//You'll need to add proper error handling here
			}
			//Set the text
			System.out.println(text);
		}
		else
		{
			System.out.println("File illa guru");
		}

		return text;
	}


	public String regularExp(String Vkey)
	{
		String firstNumber = Vkey.replaceFirst(".*?(\\d+).*", "$1");

		return firstNumber;
	}















	public String parseMyData(String parseString)
	{

		String subFileData=parseString;
		JSONArray mafithAlJinanSubArray = null;
		String titleText=null;

		if(subFileData!=null)
		{
			String temp=subFileData.toString();
			try {
				JSONObject  offJson=new JSONObject(temp);

				mafithAlJinanSubArray=offJson.getJSONArray(OBJECT_TITLE);
				int mafithLength=mafithAlJinanSubArray.length();

				for(int i=0;i<mafithLength;i++)
				{
					JSONObject jObject1=mafithAlJinanSubArray.getJSONObject(i);

					String titleId=jObject1.getString(OBJECT_ID);

					System.out.println("detail MAIN "+titleId);


					if(duaId.equals(titleId))
					{
						Log.d("equalllllll", "equal");

						titleText=jObject1.getString(TITLE_TEXT);


					}
					else
					{
						Log.d("NOT equalllllll", "NOT equal");
					}
				}
			}


			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		return titleText;
	}
	
	public String stringConcat(String path,String key)
	{
		
		
		return path.concat("/").concat(key);
	}
}
