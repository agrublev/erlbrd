package earlybird.angel.eric;

import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;

public class TestDataReader extends DataReader {
	
	public TestDataReader(Context context) {
		super(context);
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
  }
	
	private void update(int sensor, float[] values) {
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
	
	 public void startCalibration() {
		 mSensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, mSensorManager.SENSOR_DELAY_GAME);
	 }
	 public void stopCalibration() {
	     mSensorManager.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
	 }
}
