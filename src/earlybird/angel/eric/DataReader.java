package earlybird.angel.eric;

import elements.*;
import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;
import java.util.ArrayList;

public class DataReader extends Activity {
	public String message;
	protected int i = 0;
	protected float maxes[] = new float[500];
	protected float x;
	/*
	 * s1-s5 are the values for 5 different readings, d1-d5 are the diviations
	 * or deltas, and c is the combined, td is total delta avarage of 5 max and
	 * min are the highest and lowest deltas observed for calibration purposes
	 */
	public float y, z, s1, s2, s3, s4, s5, s, d1, d2, d3, d4, d5, c, td, max,
			min, mainMax;

	protected Context ctx;
	private ArrayList onSleepStateChangeListeners = new ArrayList();

	public DataReader(Context context) {
		ctx = context;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void update(int sensor, float[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
		c = (float) (Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		c = (float) Math.sqrt(c);
		// check if first reading exists, and if it doesnt populate it
		if (s == 0) {
			s = c;
		} else {
			// find the first reading of the series s
			if (s1 == 0) {
				s1 = c;
				d1 = Math.abs(s - s1);
				if (d1 > max) {
					maxes[i] = d1;
					i++;
					// mSoundManager.playSound(2);
				}
			} else if (s2 == 0) {
				s2 = c;
				d2 = Math.abs(s1 - s2);
				if (d2 > max) {
					maxes[i] = d2;
					i++;
					// mSoundManager.playSound(2);
				}
			} else if (s3 == 0) {
				s3 = c;
				d3 = Math.abs(s2 - s3);
				if (d3 > max) {
					maxes[i] = d3;
					i++;
					// mSoundManager.playSound(2);
				}
			} else if (s4 == 0) {
				s4 = c;
				d4 = Math.abs(s3 - s4);
				if (d4 > max) {
					maxes[i] = d4;
					i++;
					// mSoundManager.playSound(2);
				}
			} else if (s5 == 0) {
				s5 = c;
				d5 = Math.abs(s4 - s5);
				if (d5 > max) {
					maxes[i] = d5;
					i++;
					// mSoundManager.playSound(2);
				}
			} else {
				td = (d1 + d2 + d3 + d4 + d5) / 5;
				if (max < td) {
					max = td;
					// Check if our array is full, then read all values to find
					// the big changes :)
					if (i >= 500) {
						mainMax = maxes[0];
						for (int a = 0; a < 501; a++) {
							if (maxes[a] > mainMax) {
								mainMax = maxes[a];
							}
						}
						message = "The biggest change was"
								+ Float.toString(mainMax);
						Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
					}
				}
				if (min == 0) {
					min = td;
					// mSoundManager.playSound(2);
				} else if (min > td) {
					min = td;
				}

				s = s1 = s2 = s3 = s4 = s5 = 0;
			}
		}
	}

	public void addOnSleepStateChangeListener(
			OnSleepStateChangeListener listener) {
		onSleepStateChangeListeners.add(listener);
	}

	public void removeOnSleepStateChangeListener(
			OnSleepStateChangeListener listener) {
		onSleepStateChangeListeners.remove(listener);
	}

	private void fireOnSleepStateChange() {
		for (int i = 0; i < onSleepStateChangeListeners.size(); i++) {
			OnSleepStateChangeListener listener = (OnSleepStateChangeListener) onSleepStateChangeListeners
					.get(i);
			listener.onSleepStateChange(this);
		}
	}

	/*
	 * public void update(int sensor, float[] values) { Toast.makeText(ctx,
	 * "test", Toast.LENGTH_LONG).show();
	 * 
	 * }
	 */
}