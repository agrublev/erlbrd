package earlybird.angel.eric;

import java.net.URL;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import android.app.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;
import android.preference.*;

public class fullscreen extends Activity {
	String weatherServiceUrl = "http://www.google.com/ig/api?weather=";
	private TextView currentConditionsTextView;
	private String zipCode;
    /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    String s = prefs.getString("zip_code", "");*/
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen); 
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        String timeStamp = sharedPrefs.getString("alarm_time", "");
        zipCode = sharedPrefs.getString("zip_code", "");
        
        currentConditionsTextView = (TextView) findViewById(R.id.currentConditionsTextView);
         
     // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add 5 minutes to the calendar object
        cal.add(Calendar.SECOND, 10);
        Intent intent = new Intent(this, alarmReceiver.class);
        intent.putExtra("alarm_message", timeStamp);
        // In reality, you would want to have a static variable for the request code instead of 192837
        PendingIntent sender = PendingIntent.getBroadcast(this, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
        Toast.makeText(this, "alarm set for 10 seconds", Toast.LENGTH_LONG).show();
        
        try {
			updateView();
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void updateView() throws Exception {
		new UpdateViewAsyncTask().execute(this);
	}
	
	private class UpdateViewAsyncTask extends AsyncTask<Object, Integer, Exception> {

		private String condition;
		


		@Override
		protected void onPreExecute() {
			currentConditionsTextView.setText("Updating weather...");
			super.onPreExecute();
		}


		@Override
		protected Exception doInBackground(Object... arg0) {
			try {
				String final_url = weatherServiceUrl + zipCode;
				URL url = new URL(final_url);
				SAXReader reader = new SAXReader();
				Document document = reader.read(url.openStream());
				condition = "";
				condition += document.selectSingleNode("/xml_api_reply/weather/current_conditions/condition/@data").getStringValue();
				condition += " and " + document.selectSingleNode("/xml_api_reply/weather/current_conditions/temp_f/@data").getStringValue()
						+ " degrees.";
				condition += "\nToday's Range: "
						+ document.selectSingleNode("/xml_api_reply/weather/forecast_conditions[1]/low/@data").getStringValue() + " to "
						+ document.selectSingleNode("/xml_api_reply/weather/forecast_conditions[1]/high/@data").getStringValue()
						+ " degrees";


				return null;
			} catch (Exception e) {
				return e;
			}
		}


		@Override
		protected void onPostExecute(Exception result) {
			if (result == null) {
				currentConditionsTextView.setText(condition);
			} else
				Toast.makeText(fullscreen.this, result.getMessage(), Toast.LENGTH_LONG).show();
		}

	}
	
}
