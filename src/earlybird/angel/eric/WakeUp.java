package earlybird.angel.eric;

import java.util.*;


import android.app.*;
import android.content.*;
import android.hardware.*;
import android.media.*;
import android.os.*;
import android.preference.*;

public class WakeUp extends Activity implements SensorEventListener{
	
	private String[] soundFile;
	private int sound;
	private MediaPlayer mp;
	private SensorManager sensorManager;
	private List<Sensor> sensors;
	private Sensor sensor;

	@Override
	public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.wakeUp);
	       
	        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
	       	soundFile = getResources().getStringArray(
	       			R.array.alarm_sound_values);
	       			sound = getResources().getIdentifier(
	       					soundFile[sharedPrefs.getInt("alarm_sound", 0)], "raw",
	       					"earlybird.angel.eric");
	       			mp = MediaPlayer.create(this, sound);
	       			mp.start();
	       	
	       		

	       		xView = (TextView) findViewById(R.id.xView);
	       		yView = (TextView) findViewById(R.id.yView);
	       		zView = (TextView) findViewById(R.id.zView);
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
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
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

		float ax = event.values[SensorManager.DATA_X];
		if (ax < aMins[SensorManager.DATA_X])
			aMins[SensorManager.DATA_X] = ax;
		if (ax > aMaxs[SensorManager.DATA_X])
			aMaxs[SensorManager.DATA_X] = ax;
		
		float ay = event.values[SensorManager.DATA_Y];
		if (ay < aMins[SensorManager.DATA_Y])
			aMins[SensorManager.DATA_Y] = ay;
		if (ay > aMaxs[SensorManager.DATA_Y])
			aMaxs[SensorManager.DATA_Y] = ay;

		float az = event.values[SensorManager.DATA_Z];
		if (az < aMins[SensorManager.DATA_Z])
			aMins[SensorManager.DATA_Z] = az;
		if (az > aMaxs[SensorManager.DATA_Z])
			aMaxs[SensorManager.DATA_Z] = az;

		xView.setText("Ax = " + String.format("%.2f", ax) + "     Range = " +  String.format("%.2f", aMins[SensorManager.DATA_X]) + " to " +  String.format("%.2f", aMaxs[SensorManager.DATA_X]));
		yView.setText("Ay = " +  String.format("%.2f", ay) + "     Range = " +  String.format("%.2f", aMins[SensorManager.DATA_Y]) + " to " +  String.format("%.2f", aMaxs[SensorManager.DATA_Y]));
		zView.setText("Az = " +  String.format("%.2f", az) + "     Range = " +  String.format("%.2f", aMins[SensorManager.DATA_Z]) + " to " +  String.format("%.2f", aMaxs[SensorManager.DATA_Z]));
	}
}
