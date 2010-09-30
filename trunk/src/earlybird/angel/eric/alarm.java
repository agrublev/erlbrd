package earlybird.angel.eric;

import java.util.*;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.*;
import android.content.SharedPreferences.*;
import android.os.Bundle;
import android.preference.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class alarm extends Activity implements OnClickListener {
	private TextView mTimeDisplay;
    private Button mPickTime;

    private int mHour;
    private int mMinute;
	private Button startAlarm;
	private TextView alarmTitle;
	private SharedPreferences sharedPrefs;

    static final int TIME_DIALOG_ID = 0;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm); 
        
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mPickTime = (Button) findViewById(R.id.pickTime);
        startAlarm = (Button) findViewById(R.id.startAlarm);
        startAlarm.setOnClickListener(this);

        Spinner s = (Spinner) findViewById(R.id.Spinner01);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.TimeFrames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        
        
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
    	    
    	    startActivity(new Intent(this, fullscreen.class));
    	}
    }
 // updates the time we display in the TextView
    private void updateDisplay() {
        mTimeDisplay.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
        
        Editor editor = sharedPrefs.edit();
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
}