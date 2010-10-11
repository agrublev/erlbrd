package earlybird.angel.eric;

import java.util.*;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.hardware.*;
import android.media.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;


public class WakeUp extends Activity implements SensorEventListener{
	
	private String[] soundFile;
	private int sound;
	private MediaPlayer mp;
	private SensorManager sensorManager;
	private List<Sensor> sensors;
	private Sensor sensor;
	private long now;
	
	private Calendar cal;
	
	private ArrayList<float[]> tempVals;
	private ArrayList<Second> seconds;
	
	// Collect Average Value of Seconds for Alarm
	//private long[] aveVals = new long[90];
	private float aveCount = 0;
	private float aveVal = 0;
	private TextView curValView;
	private TextView timeValView;
	private TextView aveValView;
	private TextView countView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.wakeup);
	       
	        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
	       	soundFile = getResources().getStringArray(
	       			R.array.alarm_sound_values);
	       			sound = getResources().getIdentifier(
	       					soundFile[sharedPrefs.getInt("alarm_sound", 0)], "raw",
	       					"earlybird.angel.eric");
	       			mp = MediaPlayer.create(this, sound);
	       			//mp.start();
	       	
	       		cal = Calendar.getInstance();
	       		long now = ((cal.getTimeInMillis() / 1000));

	    
	       		curValView = (TextView) findViewById(R.id.rView);
	       		timeValView = (TextView) findViewById(R.id.tView);
	       		aveValView = (TextView) findViewById(R.id.fView);
	       		countView = (TextView) findViewById(R.id.cView);
	       		
	       		tempVals = new ArrayList<float[]>();
	       		seconds = new ArrayList<Second>();
	}
	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this);
		if(mp.isPlaying())
			mp.release();
		this.finish();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
		}
		if (sensor != null) {
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
		}

		super.onResume();
	}


	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		if ((event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) || (event.values.length < 3))
			return;

		Calendar timeCal = Calendar.getInstance();
		long time = ((timeCal.getTimeInMillis() / 1000));
		
		if(time == now){
			float[] vals = {event.values[SensorManager.DATA_X], event.values[SensorManager.DATA_Y], event.values[SensorManager.DATA_Z]};
			tempVals.add(vals);
		}else{
			Second thisSecond = new Second(tempVals);
			seconds.add(thisSecond);
			tempVals.clear();
			now = time;
			aveCount ++;
			curValView.setText("Current Value: "+ thisSecond.total);
			countView.setText("Count: "+ aveCount);
			aveValView.setText("Ave Value: " + (aveVal/20));
			if(aveCount == 20){
				Toast.makeText(this, "90 Seconds", Toast.LENGTH_LONG);
			}
			if(aveCount > 20){
				if(thisSecond.total > (aveVal/20)){
					mp.start();
				}
			}else{
				aveVal = aveVal + thisSecond.total;
			}
		}
		
		timeValView.setText("Time: " + time);
		
	}
}
