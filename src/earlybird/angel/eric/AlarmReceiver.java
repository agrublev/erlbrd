package earlybird.angel.eric;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private Context ctx;
	

	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;

		new DataReader(context);

		try {
			runAlarm();
		} catch (Exception e) {
			Toast
					.makeText(
							context,
							"There was an error somewhere, but we still received an alarm",
							Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	
	public void runAlarm() {
		//Toast.makeText(ctx, "Alarm has gone off!", Toast.LENGTH_LONG).show();
	    Intent i = new Intent(ctx, WakeUp.class); 
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    ctx.startActivity(i); 
	}


}