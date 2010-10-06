package earlybird.angel.eric;

import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;

public class TestDataReader extends DataReader {
	
	private Context ctx;
	
	public TestDataReader(Context context) {
		super(context);
		ctx = context;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

  }

	public void update(int sensor, float[] values) {
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
				if (d1 > max) { /*mSoundManager.playSound(1); */}
			} else if (s2 == 0) {
				s2 = c;
				d2 = Math.abs(s1 - s2);
				if (d2 > max) { /*mSoundManager.playSound(1); */}
			} else if (s3 == 0) {
				s3 = c;
				d3 = Math.abs(s2 - s3);
				if (d3 > max) { /*mSoundManager.playSound(1); */}
			} else if (s4 == 0) {
				s4 = c;
				d4 = Math.abs(s3 - s4);
				if (d4 > max) { /*mSoundManager.playSound(1); */}
			} else if (s5 == 0) {
				s5 = c;
				d4 = Math.abs(s4 - s5);
				if (d1 > max) { /*mSoundManager.playSound(1); */}
			} else {
				td = (d1 + d2 + d3 + d4 + d5) / 5;
				if (max < td) {
					max = td;
			
				}
				if (min == 0) {
					min = td;
					//mSoundManager.playSound(1);
				} else if (min > td) {
					min = td;
			
				}
			
				
				s = s1 = s2 = s3 = s4 = s5 = 0;
			}
		}
		

}

}
