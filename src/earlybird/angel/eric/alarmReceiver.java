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

public class alarmReceiver extends BroadcastReceiver implements SensorListener {
	private soundmanager mSoundManager;
	float x, y, z, s1, s2, s3, s4, s5, s, d1, d2, d3, d4, d5, c, td, max,
			min = 0;
	private static final String TAG = "Sensors";
    private static SensorManager mSensorManager;
    private static Boolean alarmHasGoneOff = false;
    private Vibrator vibrator;
    private Context ctx;
	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		try {
			mSoundManager = new soundmanager();
			vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			mSoundManager.initSounds(context);
			mSoundManager.addSound(1, R.raw.alarm_01);
			mSoundManager.addSound(2, R.raw.move);
			Bundle bundle = intent.getExtras();
			String message = bundle.getString("alarm_message");
			Boolean test = true;
			mSensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, mSensorManager.SENSOR_DELAY_GAME);
			alarmWindow(30);

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
	               //run alarm
	           }
	     },  in30Minutes );
	     if (alarmHasGoneOff == false) {
	    	 runAlarm();
	     }
	     // do the work... 
	}
	
	public void runAlarm() {
		mSoundManager.playSound(1);
		vibrator.vibrate(2000);
		Toast.makeText(ctx, "Alarm has gone off!", Toast.LENGTH_LONG).show();
		}
	
	public void onSensorChanged(int sensor, float[] values) {
		synchronized (this) {
			x = values[0];
			y = values[1];
			z = values[2];
			c = (float) (Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
			c = (float) Math.sqrt(c);
			if (s == 0) {
				s = c;
			} else {
				if (s1 == 0) {
					s1 = c;
					d1 = Math.abs(s - s1);
					if (d1 > max) { mSoundManager.playSound(2); }
				} else if (s2 == 0) {
					s2 = c;
					d2 = Math.abs(s1 - s2);
					if (d2 > max) { mSoundManager.playSound(2); }
				} else if (s3 == 0) {
					s3 = c;
					d3 = Math.abs(s2 - s3);
					if (d3 > max) { mSoundManager.playSound(2); }
				} else if (s4 == 0) {
					s4 = c;
					d4 = Math.abs(s3 - s4);
					if (d4 > max) { mSoundManager.playSound(2); }
				} else if (s5 == 0) {
					s5 = c;
					d4 = Math.abs(s4 - s5);
					if (d1 > max) { mSoundManager.playSound(2); }
				} else {
					td = (d1 + d2 + d3 + d4 + d5) / 5;
					if (max < td) {
						max = td;
					}
					if (min == 0) {
						min = td;
						mSoundManager.playSound(2);
					} else if (min > td) {
						min = td;
					}
					
					s = s1 = s2 = s3 = s4 = s5 = 0;
				}
			}
		}
	}
	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}