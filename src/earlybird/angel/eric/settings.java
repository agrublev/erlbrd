package earlybird.angel.eric;
 
 
import java.util.*;

import elements.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.*;
import android.media.MediaPlayer;
import android.net.*;
import android.os.Bundle;
import android.preference.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
 
public class settings extends Activity implements OnClickListener, TextWatcher, OnItemSelectedListener {
 
    private Spinner z;
	private SharedPreferences sharedPrefs;
	private Editor editor;
	private EditText zipCodeInput;
	private int sound;
	private boolean spinnerInitialized = false;
	private MediaPlayer mp;
	private String[] soundFile;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); 
        editor = sharedPrefs.edit();
        
        z = (Spinner) findViewById(R.id.pickAlarm);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.alarm_sounds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        z.setAdapter(adapter);
        z.setSelection(sharedPrefs.getInt("alarm_sound", 0));
        z.setOnItemSelectedListener(this);
        
        zipCodeInput = (EditText) findViewById(R.id.zipcodeInput);
        String zipCode = sharedPrefs.getString("zip_code", "");
        zipCodeInput.setText(zipCode);
        zipCodeInput.addTextChangedListener(this);
   
        
        soundFile = getResources().getStringArray(R.array.alarm_sound_values);
        sound = getResources().getIdentifier(soundFile[sharedPrefs.getInt("alarm_sound", 0)], "raw","earlybird.angel.eric");
        mp = MediaPlayer.create(settings.this, sound);
        
        Button backButton = (Button) findViewById(R.id.backButton);
        ButtonClicked backButtonListener = new ButtonClicked();
        backButtonListener.setToBackButton();
        backButton.setOnClickListener(backButtonListener);
    }
     
    public void onClick(View v) {
        IntentButton b = (IntentButton) v;
        startActivity(b.intent);
    }
    
    public class ButtonClicked implements OnClickListener{
    	private Boolean isBackButton = false;
    	
		@Override
		public void onClick(View context) {
			if(isBackButton){
				onBackPressed();
			}
			
		}
		
		public void setToBackButton(){
			isBackButton = true;
		}
    	
    }

    // Zip Code Listener
	@Override
	public void afterTextChanged(Editable arg0) {
		Editable zip = zipCodeInput.getText();
        editor.putString("zip_code", zip.toString());
        editor.commit();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {	
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}	
	
	// Alarm Listener
	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int pos, long id) {
		if(spinnerInitialized){  // Prevent sound from playing onCreate
	            String[] soundFile = getResources().getStringArray(R.array.alarm_sound_values);
	            sound = getResources().getIdentifier(soundFile[pos], "raw","earlybird.angel.eric");
	            
	            mp.reset();
	            mp = MediaPlayer.create(settings.this, sound);
	            mp.start();
	            
	            editor.putInt("alarm_sound", pos);
	            editor.commit();
            }else{
            	spinnerInitialized = true;
            }
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed(){
		if(mp.isPlaying())
			mp.release();
		this.finish();
	}
	
    /* To call this activity from the Menu butotn
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
    }*/
}