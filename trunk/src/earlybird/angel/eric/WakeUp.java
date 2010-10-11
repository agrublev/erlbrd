package earlybird.angel.eric;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.preference.*;

public class WakeUp extends Activity {
	
	private String[] soundFile;
	private int sound;
	private MediaPlayer mp;

	@Override
	public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       
	        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
	       	soundFile = getResources().getStringArray(
	       			R.array.alarm_sound_values);
	       			sound = getResources().getIdentifier(
	       					soundFile[sharedPrefs.getInt("alarm_sound", 0)], "raw",
	       					"earlybird.angel.eric");
	       			mp = MediaPlayer.create(this, sound);
	       			mp.start();
	}
	@Override
	protected void onPause() {
		if(mp.isPlaying())
			mp.release();
		this.finish();
		super.onPause();
	}
}
