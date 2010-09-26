package earlybird.angel.eric;

import elements.*;
import android.app.Activity;
import android.content.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class settings extends Activity implements OnClickListener {

	private Spinner z;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings); 
        
        z = (Spinner) findViewById(R.id.pickAlarm);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.alarms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        z.setAdapter(adapter);
        z.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
        backButton.intent = new Intent(this, settings.class);
        backButton.setOnClickListener(this);
    }
    
    public void onClick(View v) {
    	IntentButton b = (IntentButton) v;
    	startActivity(b.intent);
    }

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	parent.getContext();
	      Toast.makeText(parent.getContext(), "The planet is " +
	          parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	      if (parent.getItemAtPosition(pos).toString() == "Alarm 1") {
	    	  MediaPlayer mp = MediaPlayer.create(settings.this, R.raw.alarm_01);
	    	   mp.start();
	      }
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
}