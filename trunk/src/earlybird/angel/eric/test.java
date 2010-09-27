package earlybird.angel.eric;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.SensorEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import elements.IntentButton;

public class test extends Activity implements OnClickListener, SensorListener {
	/** Called when the activity is first created. */
	private static final String TAG = "Sensors";
    private SensorManager mSensorManager;
    float x, y, z = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        setContentView(R.layout.calibration); 
        
        
        
        IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
        backButton.intent = new Intent(this, main.class);
        backButton.setOnClickListener(this);
    }
    
    public void onClick(View v) {
    	IntentButton b = (IntentButton) v;
    	startActivity(b.intent);
    }

	public void onAccuracyChanged(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onSensorChanged(SensorEvent event) {
        
        ((TextView) findViewById(R.id.x_label)).setText(Float.toString(event.values[0]));
        ((TextView) findViewById(R.id.y_label)).setText(Float.toString(event.values[1]));
        ((TextView) findViewById(R.id.z_label)).setText(Float.toString(event.values[2]));

	}

	public void onSensorChanged(int sensor, float[] values) {
		synchronized (this) {
	        ((TextView) findViewById(R.id.x_label)).setText(Float.toString(values[0]));
	        ((TextView) findViewById(R.id.y_label)).setText(Float.toString(values[1]));
	        ((TextView) findViewById(R.id.z_label)).setText(Float.toString(values[2]));
		}
	}
	  @Override
	    protected void onResume() {
	    super.onResume();
	    // Register this class as a listener for the accelerometer sensor
	    mSensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER);
	  }

}