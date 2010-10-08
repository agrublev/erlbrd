package earlybird.angel.eric;

import elements.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main extends Activity implements OnClickListener {

	private Button alarmButton;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        
        IntentButton alarmNavButton = (IntentButton) findViewById(R.id.alarmNavButton);
        alarmNavButton.intent = new Intent(this, alarm.class);
        alarmNavButton.setOnClickListener(this);
        
        IntentButton settingsNavButton = (IntentButton) findViewById(R.id.settingsNavButton);
        settingsNavButton.intent = new Intent(this, EditPreferencesActivity.class);
        settingsNavButton.setOnClickListener(this);
        
        IntentButton calendarNavButton = (IntentButton) findViewById(R.id.calendarNavButton);
        calendarNavButton.intent = new Intent(this, calendar.class);
        calendarNavButton.setOnClickListener(this);
        
        IntentButton testNavButton = (IntentButton) findViewById(R.id.testNavButton);
        testNavButton.intent = new Intent(this, test.class);
        testNavButton.setOnClickListener(this);
        
        IntentButton tipsNavButton = (IntentButton) findViewById(R.id.tipsNavButton);
        tipsNavButton.intent = new Intent(this, tips.class);
        tipsNavButton.setOnClickListener(this);
        
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      //setContentView(R.layout.myLayout);
    }

    public void onClick(View v) {
    	IntentButton b = (IntentButton) v;
    	startActivity(b.intent);
    }
    
}