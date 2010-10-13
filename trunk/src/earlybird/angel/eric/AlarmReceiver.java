package earlybird.angel.eric;

import java.util.Timer;
import java.util.TimerTask;

import android.app.*;
import android.content.*;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.*;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.*;
import android.widget.Toast;
import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {
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

	
	public void runAlarm() {
		//Toast.makeText(ctx, "Alarm has gone off!", Toast.LENGTH_LONG).show();
	    Intent i = new Intent(ctx, WakeUp.class); 
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    ctx.startActivity(i); 
	}


}