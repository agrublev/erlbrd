package earlybird.angel.eric;

import android.app.Activity;
import android.content.*;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import elements.IntentButton;

public class test extends Activity implements OnClickListener, SensorListener, MaxIncreaseListener {
	/** Called when the activity is first created. */
	private static final String TAG = "Sensors";

	private Button startCalibration;
	private Button stopCalibration;

	private TestDataReader dataReader;
	protected SensorManager mSensorManager;

	private soundmanager mSoundManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        mSoundManager = new soundmanager(); 
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.alarm_01);
        mSoundManager.addSound(2, R.raw.move);
        
        setContentView(R.layout.calibration); 
        
        startCalibration = (Button) findViewById(R.id.startCalibration);
        startCalibration.setOnClickListener(this);
        stopCalibration = (Button) findViewById(R.id.stopCalibration);
        stopCalibration.setOnClickListener(this);
        
        IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
        backButton.intent = new Intent(this, main.class);
        backButton.setOnClickListener(this);
        
        dataReader = new TestDataReader(this);
        dataReader.addMaxIncreaseListener(this);
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

	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	
	 public void startCalibration() {
		 mSensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, mSensorManager.SENSOR_DELAY_GAME);
	 }
	 public void stopCalibration() {
	     mSensorManager.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
	 }
	
	public void onSensorChanged(int sensor, float[] values) {
		dataReader.update(sensor, values);
		((TextView) findViewById(R.id.d_label)).setText(Float.toString(dataReader.td));
	    ((TextView) findViewById(R.id.x_label)).setText(Float.toString(values[0]));
	    ((TextView) findViewById(R.id.y_label)).setText(Float.toString(values[1]));
	    ((TextView) findViewById(R.id.z_label)).setText(Float.toString(values[2]));
	}
	
	 /* @Override
	    protected void onResume() {
	    super.onResume();
	    // Register this class as a listener for the accelerometer sensor
	    
	    
	  }*/

	public void maxIncrease(TestDataReader sender) {
		((TextView) findViewById(R.id.max_label)).setText(Float.toString(dataReader.max));
		((TextView) findViewById(R.id.min_label)).setText(Float.toString(dataReader.min));
		mSoundManager.playSound(1);
	}

}