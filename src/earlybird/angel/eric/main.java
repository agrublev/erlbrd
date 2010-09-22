package earlybird.angel.eric;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main extends Activity implements OnClickListener {
    private Button tipsButton;
	private Button settingsButton;
	private View calendarButton;
	private View testButton;
	private Button alarmButton;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        alarmButton = (Button) findViewById(R.id.startAlarm);
        alarmButton.setOnClickListener(this);
        settingsButton = (Button) findViewById(R.id.viewSettings);
        settingsButton.setOnClickListener(this);
        calendarButton = (Button) findViewById(R.id.viewCalendar);
        calendarButton.setOnClickListener(this);
        testButton = (Button) findViewById(R.id.viewTestAlarm);
        testButton.setOnClickListener(this);
        tipsButton = (Button) findViewById(R.id.viewTipsAndTricks);
        tipsButton.setOnClickListener(this);
    
    }
    public void onClick(View v) {
    	if (v == tipsButton) {
            startActivity(new Intent(this, tips.class));
    	} else if (v == settingsButton) {
    		startActivity(new Intent(this, settings.class));
    	} else if (v == calendarButton) {
    		startActivity(new Intent(this, calendar.class));
    	} else if (v == testButton) {
    		startActivity(new Intent(this, test.class));
    	} else if (v == alarmButton) {
    		startActivity(new Intent(this, alarm.class));
    	}
    }
    
}