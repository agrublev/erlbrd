package earlybird.angel.eric;

import android.content.*;
import android.os.*;
import android.preference.*;
import android.preference.Preference.*;

/**
 * EditPreferencesActivity allows the user to change the apps preferences.
 * 
 * @author sstrenn
 * 
 */
public class EditPreferencesActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	@Override
	/**
	 * Adds the app's preferences to the PreferenceActivity UI.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Adds the app's preferences to the PreferenceActivity UI.
		addPreferencesFromResource(R.xml.preferences);

		String[] prefNames = new String[] { "alarm_sound" , "zip_code"};

		Preference p = null;

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		// Set this object to be the listener for changes to any preference. Also set the preference summary values
		for (String prefName : prefNames) {
			p = findPreference(prefName);
			p.setOnPreferenceChangeListener(this);
			String s = prefs.getString(prefName, "");
			setPreferenceSummary(p, s);
		}
	}

	@Override
	/**
	 * Changes the summary property of the given preference.
	 */
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		setPreferenceSummary(preference, newValue.toString());
		return true;
	}

	private void setPreferenceSummary(Preference preference, String newSummary) {
		if (preference.getKey().equalsIgnoreCase("alarm_sound")) {
			String[] alarms = getResources().getStringArray(R.array.alarm_sounds);
			newSummary = alarms[Integer.parseInt(newSummary)];
		}

		preference.setSummary(newSummary);
	}

}
