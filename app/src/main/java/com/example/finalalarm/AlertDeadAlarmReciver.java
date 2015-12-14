package com.example.finalalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlertDeadAlarmReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Toast.makeText(context, "Dead Alarm  Reciver",Toast.LENGTH_LONG).show();
		
		context.startService(new Intent(context, MyAlarmService.class));
		
		
	}

}
