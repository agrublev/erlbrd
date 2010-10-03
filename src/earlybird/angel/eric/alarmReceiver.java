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
	
	private static final String TAG = "Sensors";
    private static SensorManager mSensorManager;
    private static Boolean alarmHasGoneOff = false;
    private Vibrator vibrator;
    private Context ctx;
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			ctx = context;
			dataReader = new DataReader(context);
			mSoundManager = new soundmanager();
			vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			mSoundManager.initSounds(context);
			mSoundManager.addSound(1, R.raw.alarm_01);
			mSoundManager.addSound(2, R.raw.move);
			Bundle bundle = intent.getExtras();
			String message = bundle.getString("alarm_message");
			Boolean test = true;
		} catch (Exception e) {
			Toast
					.makeText(
							context,
							"There was an error somewhere, but we still received an alarm",
							Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}
	
	public void alarmWindow(int c) {
	    // set the timeout    
	    // this will stop this function in 30 minutes
	    long in30Minutes = 30 * 60 * 1000;
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
	
	public void runAlarm() {
		mSoundManager.playSound(1);
		vibrator.vibrate(2000);
		Toast.makeText(ctx, "Alarm has gone off!", Toast.LENGTH_LONG).show();
		}

}