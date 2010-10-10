package earlybird.angel.eric;

import java.util.*;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.*;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.preference.*;
import android.text.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.*;

public class AlarmSettings extends Activity implements OnClickListener, OnItemSelectedListener {
	private TextView mTimeDisplay;
    private Button mPickTime;

    private int mHour;
    private int mMinute;
	private Button startAlarm;
	private TextView alarmTitle;
	private SharedPreferences sharedPrefs;
	private Editor editor;
	private Spinner s;
	private String alarmSoundName;

    static final int TIME_DIALOG_ID = 0;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm); 
        
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        editor = sharedPrefs.edit();
        
        String[] alarmNames = getResources().getStringArray(R.array.alarm_sounds);
        alarmSoundName = alarmNames[sharedPrefs.getInt("alarm_sound", 0)];
        TextView currentSong = (TextView) findViewById(R.id.currentSong);
        currentSong.setText(alarmSoundName);
        
        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mPickTime = (Button) findViewById(R.id.pickTime);
        startAlarm = (Button) findViewById(R.id.startAlarm);
        startAlarm.setOnClickListener(this);

        s = (Spinner) findViewById(R.id.Spinner01);
        s.setOnItemSelectedListener(new MyOnItemSelectedListener());
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.TimeFrames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        String w = sharedPrefs.getString("alarm_window", "0");
        try{
        	s.setSelection(Integer.parseInt(w));
        }catch (Exception e){
        	s.setSelection(0);
        }
        
        // add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        // get the current time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // display the current date
        updateDisplay();

    }
    public void onClick(View v) {
    	if (v == startAlarm) {
    		
    		/* The code to display what time they chose */
    		Toast.makeText(this, mTimeDisplay.getText(), Toast.LENGTH_LONG).show();
    	    
    	    startActivity(new Intent(this, AlarmClock.class));
    	}
    }
 // updates the time we display in the TextView
    private void updateDisplay() {
        mTimeDisplay.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
        
        editor.putString("alarm_time", (String) mTimeDisplay.getText());
        editor.commit();
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
 // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay();
            }
    };
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Toast.makeText(this, s.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	      Toast.makeText(parent.getContext(), "The window is " +
	         pos, Toast.LENGTH_LONG).show();
	        editor.putString("alarm_window", (String) mTimeDisplay.getText());
	        editor.commit();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}

}