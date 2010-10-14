package earlybird.angel.eric;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import elements.IntentButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class WakeUp extends Activity implements SensorEventListener, OnClickListener{
	
	private String[] soundFile;
	private int sound;
	private MediaPlayer mp;
	private SensorManager sensorManager;
	private List<Sensor> sensors;
	private Sensor sensor;
	private long now;
	private PowerManager.WakeLock wl;
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
	private boolean alarmHasGoneOff = false;
	private int winPos;
	private String timeWindow;
	@Override
	public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.wakeup);
	       
	       IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
	        backButton.intent = new Intent(this, main.class);
	        backButton.setOnClickListener(this);
	        
	       PowerManager pm = (PowerManager) getSystemService(AlarmClock.POWER_SERVICE);
	        wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "FullScreen");
	        //This will start the screen and not let you turn it off!
//	        Calendar nowTime = Calendar.getInstance();
//	        
//	        pm.userActivity(nowTime.getTimeInMillis(), false);
	        wl.acquire();
	        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
	        try{
	        	winPos = sharedPrefs.getInt("alarm_window", 0);
	        }catch(Exception e){
	        	winPos = 30;
	        }
	        String[] timeWindows = getResources().getStringArray(R.array.time_windows_values);
	        timeWindow = timeWindows[winPos];
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
	       		alarmWindow(Integer.parseInt(timeWindow));
	       		
	       		

	       		
	}
	
	 
    public void onClick(View v) {
    	IntentButton b = (IntentButton) v;
    	startActivity(b.intent);
    }
    
	
	public void alarmWindow(int c) {
		    // set the timeout    
		    // this will stop this function in 30 minutes
		    long in30Minutes = c * 60 * 1000;
		    Timer timer = new Timer();
		    timer.schedule( new TimerTask(){
		          public void run() {
		               if( alarmHasGoneOff == false ) { 
		            	   mp.start();
		                }
		           }
		     },  in30Minutes );
		    /*if( alarmHasGoneOff == false ) { 
		    	mp.start();
		    }*/
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


	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}


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
			if(aveCount > 20){
				if(thisSecond.total > (aveVal/20)){
					alarmHasGoneOff = true;
					mp.start();
				}
			}else{
				aveVal = aveVal + thisSecond.total;
			}
		}
		
		timeValView.setText("Time: " + time);
		
	}
}
