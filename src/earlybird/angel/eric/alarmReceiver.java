package earlybird.angel.eric;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

public class alarmReceiver extends BroadcastReceiver {
	private soundmanager mSoundManager;
	private DataReader dataReader;
	
    private static Boolean alarmHasGoneOff = false;
    private Context ctx;
	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		dataReader = new DataReader(context);
		mSoundManager = new soundmanager();
		mSoundManager.initSounds(context);
		mSoundManager.addSound(1, R.raw.alarm_01);
		mSoundManager.playSound(1);
		Bundle bundle = intent.getExtras();
		String message = bundle.getString("alarm_message");
		try {
			runAlarm();
		} catch (Exception e) {
			Toast
					.makeText(
							context,
							"There was an error somewhere, but we still received an alarm",
							Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}
	/*
	public void alarmWindow(int c) {
	    // set the timeout    
	    // this will stop this function in 30 minutes
	    long in30Minutes = c * 60 * 1000;
	    Timer timer = new Timer();
	    timer.schedule( new TimerTask(){
	          public void run() {
	        	  if (alarmHasGoneOff == false) {
	     	    	 runAlarm();
	     	     }
	           }
	     },  in30Minutes );
	     runAlarm();
	     // do the work... 
	}
	*/
	public void runAlarm() {
		Toast.makeText(ctx, "Alarm has gone off!", Toast.LENGTH_LONG).show();
		}

}