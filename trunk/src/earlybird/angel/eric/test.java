package earlybird.angel.eric;

import android.app.Activity;
import android.content.*;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import elements.IntentButton;

public class test extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static final String TAG = "Sensors";

	private Button startCalibration;
	private Button stopCalibration;

	private TestDataReader dataReader;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.calibration); 
        
        startCalibration = (Button) findViewById(R.id.startCalibration);
        startCalibration.setOnClickListener(this);
        stopCalibration = (Button) findViewById(R.id.stopCalibration);
        stopCalibration.setOnClickListener(this);
        
        IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
        backButton.intent = new Intent(this, main.class);
        backButton.setOnClickListener(this);
        
        dataReader = new TestDataReader(this);
    }
    
    public void onClick(View v) {
    	if (v == startCalibration) {
    		//dataReader.startCalibration();
    	} else if (v == stopCalibration) {
    		//dataReader.stopCalibration();
    	} else {
	    	IntentButton b = (IntentButton) v;
	    	startActivity(b.intent);
    	}
    }
	
	 /* @Override
	    protected void onResume() {
	    super.onResume();
	    // Register this class as a listener for the accelerometer sensor
	    
	    
	  }*/

}