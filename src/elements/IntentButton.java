package elements;


import android.app.*;
import android.content.*;
import android.util.*;
import android.widget.*;

public class IntentButton extends Button{
	
	public Intent intent;
	
	public IntentButton(Context context){
		super(context);
	}
	
	public IntentButton(Context context, AttributeSet attrs){
		super(context, attrs);
		}
	
	public void setIntent(Activity a, Class c){
		intent = new Intent(a, c);
	}
	

}
