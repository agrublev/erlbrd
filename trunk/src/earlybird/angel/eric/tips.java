package earlybird.angel.eric;

import elements.*;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;

public class tips extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips); 
        
        IntentButton settingsNavButton = (IntentButton) findViewById(R.id.settingsNavButton);
        settingsNavButton.intent = new Intent(this, settings.class);
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
    
    public void onClick(View v) {
    	IntentButton b = (IntentButton) v;
    	startActivity(b.intent);
    }
    
}