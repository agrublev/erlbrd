package earlybird.angel.eric;

import java.util.Timer;
import java.util.TimerTask;

import android.content.*;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.*;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.*;
import android.widget.Toast;

public class alarmReceiver extends BroadcastReceiver {
	private soundmanager mSoundManager;
	private DataReader dataReader;
	
    private static Boolean alarmHasGoneOff = false;
    private Context ctx;
	private String[] soundFile;
	private int sound;
	public MediaPlayer mp;
	private SharedPreferences sharedPrefs;
	

	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
	    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context); 
	      
		dataReader = new DataReader(context);
        soundFile = context.getResources().getStringArray(R.array.alarm_sound_values);
        sound = context.getResources().getIdentifier(soundFile[sharedPrefs.getInt("alarm_sound", 0)], "raw","earlybird.angel.eric");
        mp = MediaPlayer.create(context, sound);
        
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
		mp.start();
		}

}