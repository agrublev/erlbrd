package earlybird.angel.eric;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;

public class alarmReceiver extends BroadcastReceiver {

 @Override
 public void onReceive(Context context, Intent intent) {
   try {
     Bundle bundle = intent.getExtras();
     String message = bundle.getString("alarm_message");
     Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
     Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
     e.printStackTrace();

    }
 }

}