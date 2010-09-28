package earlybird.angel.eric;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import elements.IntentButton;

public class test extends Activity implements OnClickListener, SensorListener {
	/** Called when the activity is first created. */
	private static final String TAG = "Sensors";
    private static SensorManager mSensorManager;
    /*	
     * s1-s5 are the values for 5 different readings, d1-d5 are the diviations or deltas, and c is the combined, td is total delta avarage of 5
     * max and min are the highest and lowest deltas observed for calibration purposes
     * */
    float x, y, z, s1, s2, s3, s4, s5, s, d1, d2, d3, d4, d5, c, td, max, min = 0;
    private soundmanager mSoundManager;
	private Button startCalibration;
	private Button stopCalibration;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        setContentView(R.layout.calibration); 
        
        mSoundManager = new soundmanager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.move);
        
        startCalibration = (Button) findViewById(R.id.startCalibration);
        startCalibration.setOnClickListener(this);
        stopCalibration = (Button) findViewById(R.id.stopCalibration);
        stopCalibration.setOnClickListener(this);
        
        IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
        backButton.intent = new Intent(this, main.class);
        backButton.setOnClickListener(this);
    }
    
    public void onClick(View v) {
    	if (v == startCalibration) {
    		startCalibration();
    	} else if (v == stopCalibration) {
    		stopCalibration();
    	} else {
	    	IntentButton b = (IntentButton) v;
	    	startActivity(b.intent);
    	}
    }

	public void onAccuracyChanged(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}/*
	public void onSensorChanged(SensorEvent event) {
        
        ((TextView) findViewById(R.id.x_label)).setText(Float.toString(event.values[0]));
        ((TextView) findViewById(R.id.y_label)).setText(Float.toString(event.values[1]));
        ((TextView) findViewById(R.id.z_label)).setText(Float.toString(event.values[2]));

	}*/
	
	public void onSensorChanged(int sensor, float[] values) {
		synchronized (this) {
			x = Math.abs(values[0]);
			y = Math.abs(values[1]);
			z = Math.abs(values[2]);
			c = (float) (Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
			c = (float) Math.sqrt(c);
			if (s == 0) {
				s = c;
			} else {
				if (s1 == 0) {
					s1 = c;
					d1 = Math.abs(s - s1);
					if (d1 > max) { mSoundManager.playSound(1); }
				} else if (s2 == 0) {
					s2 = c;
					d2 = Math.abs(s1 - s2);
					if (d2 > max) { mSoundManager.playSound(1); }
				} else if (s3 == 0) {
					s3 = c;
					d3 = Math.abs(s2 - s3);
					if (d3 > max) { mSoundManager.playSound(1); }
				} else if (s4 == 0) {
					s4 = c;
					d4 = Math.abs(s3 - s4);
					if (d4 > max) { mSoundManager.playSound(1); }
				} else if (s5 == 0) {
					s5 = c;
					d4 = Math.abs(s4 - s5);
					if (d1 > max) { mSoundManager.playSound(1); }
				} else {
					td = (d1 + d2 + d3 + d4 + d5) / 5;
					if (max < td) {
						max = td;
						((TextView) findViewById(R.id.max_label)).setText(Float.toString(max));
					}
					if (min == 0) {
						min = td;
						mSoundManager.playSound(1);
					} else if (min > td) {
						min = td;
						((TextView) findViewById(R.id.min_label)).setText(Float.toString(min));
					}
					((TextView) findViewById(R.id.d_label)).setText(Float.toString(td));
					
					s = s1 = s2 = s3 = s4 = s5 = 0;
				}
			}
			
			
	        ((TextView) findViewById(R.id.x_label)).setText(Float.toString(values[0]));
	        ((TextView) findViewById(R.id.y_label)).setText(Float.toString(values[1]));
	        ((TextView) findViewById(R.id.z_label)).setText(Float.toString(values[2]));
		}
	} 
	 /* @Override
	    protected void onResume() {
	    super.onResume();
	    // Register this class as a listener for the accelerometer sensor
	    
	    
	  }*/
	 public void startCalibration() {
		 mSensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, mSensorManager.SENSOR_DELAY_GAME);
	 }
	 public void stopCalibration() {
	        mSensorManager.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
	 }

}