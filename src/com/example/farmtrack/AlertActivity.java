package com.example.farmtrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Text;

import com.example.farmtrack.Contact;
import com.example.farmtrack.DatabaseHandler;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlertActivity extends Activity implements OnInitListener, OnClickListener {

	TextView textViewInfo;
	GifView gifView;
	TextToSpeech tts;
	Ringtone r;
	Button notify;
	String msg="Hey there ! Some intrusional activity has taken place in my farm. Can you please take a look at it.";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
		wakeLock.acquire();
		KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE); 
		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
		keyguardLock.disableKeyguard();
		
		
		setContentView(R.layout.activity_alert);
		notify = (Button) findViewById(R.id.button1);
		notify.setOnClickListener(this);
		
		final int interval = 2000; // 1 Second
		Handler handler = new Handler();
		Runnable runnable = new Runnable(){
		    public void run() {
		        Toast.makeText(AlertActivity.this, "Alarm start", Toast.LENGTH_SHORT).show();
		    	Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
				r = RingtoneManager.getRingtone(getApplicationContext(), notification);
				r.play();
			  }
		};
		handler.postAtTime(runnable, System.currentTimeMillis()+interval);
		handler.postDelayed(runnable, interval);

		gifView = (GifView)findViewById(R.id.gif_view);
		tts = new TextToSpeech(this, this);

		speakOut();
		
		addRecord();
	}

	
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			 
            int result = tts.setLanguage(Locale.UK);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
	}
	
	 private void speakOut() {
		 
	        String text = "Intrusion Alert";
	        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

	    }
	 
	 @Override
	    public void onDestroy() {
	        // Don't forget to shutdown tts!
	        if (tts != null) {
	            tts.stop();
	            tts.shutdown();
	        }
	    	if(r != null)
	            r.stop();
	  
	        super.onDestroy();
	    }



	@Override
	public void onClick(View v) {
		  SharedPreferences sp = this.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);		
	      String str = sp.getString("contacts", "none");
	      if(str.equals("none")||str.length()<2){
	    	  Toast.makeText(this, "Please Add contacts", Toast.LENGTH_SHORT).show();
	          return;
	      }
	      String []values = str.split("  ");
	      
	        for(int i = 0 ; i<values.length ; i++)
	        {
	        	String [] tmp;
	        	tmp = values[i].split(",");
	        	SmsManager sm=SmsManager.getDefault();
	   			sm.sendTextMessage(tmp[1],null,msg,null,null);
	   			Toast.makeText(this, "Notified Neighbours", Toast.LENGTH_SHORT).show();
	        }
   
			
	}

	 
	void addRecord()
	{
        DatabaseHandler db = new DatabaseHandler(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
        String formattedDate = df.format(c.getTime());
        String [] arr = formattedDate.split(" "); 
        db.addContact(new Contact(arr[0], arr[1]+" "+arr[2]));		
        
	}

	
}
