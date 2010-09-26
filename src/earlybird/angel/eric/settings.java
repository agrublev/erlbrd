package earlybird.angel.eric;
 
 
import elements.*;
import android.app.Activity;
import android.content.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.*;
import android.view.*;
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
         
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
         
        z = (Spinner) findViewById(R.id.pickAlarm);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.alarms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        z.setAdapter(adapter);
        z.setOnItemSelectedListener(new MyOnItemSelectedListener());
         
        //IntentButton backButton = (IntentButton) findViewById(R.id.backButton);
        //backButton.intent = new Intent(this, settings.class);
        //backButton.setOnClickListener(this);
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
     
    /**
     * Build the options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b = false;
        try {
            new MenuInflater(this).inflate(R.menu.optionsmenu, menu);
            b = super.onCreateOptionsMenu(menu);
        } catch (Throwable t) {
            Toast.makeText(this, t.toString(), 2000).show();
        }
        return b;
    }
    /**
     * Start the edit preferences activity.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean b = false;
        try {
            switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, EditPreferencesActivity.class));
                break;
            }
            b = super.onOptionsItemSelected(item);
        } catch (Throwable t) {
            Toast.makeText(this, t.toString(), 10000).show();
        }
        return b;
    }
}