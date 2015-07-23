package com.example.farmtrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Text;

import com.example.farmtrack.Intrusions;
import com.example.farmtrack.DatabaseHandler;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AlertActivity extends Activity implements OnInitListener, OnClickListener {

	TextView textViewInfo,dir,alert;
	ProgressBar pb;
	GifView gifView;
	TextToSpeech tts;
	Ringtone r;
	Button notify;
	Handler handler1 , handler2;
	Runnable runnable1 , runnable2; 
	PowerManager pm;
	WakeLock wakeLock;
	KeyguardManager keyguardManager;
	KeyguardLock keyguardLock;
    Animation flash;
	
	String msg="Hey there ! Some intrusional activity has taken place in my farm. Can you please take a look at it.";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
		wakeLock.acquire();
		keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE); 
		keyguardLock = keyguardManager.newKeyguardLock("TAG");
		keyguardLock.disableKeyguard();
	    flash = AnimationUtils.loadAnimation(this, R.drawable.blink);
		final SharedPreferences sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);		

		  /** Hiding Title bar of this activity screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
 
        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		setContentView(R.layout.activity_alert);
		notify = (Button) findViewById(R.id.button1);
		dir = (TextView) findViewById(R.id.textView2);
		alert = (TextView) findViewById(R.id.textView1);
		pb = (ProgressBar) findViewById(R.id.progressBar1);	
		notify.setOnClickListener(this);
		alert.setAnimation(flash);
		
		final int interval = 3000; // 1 Second ringtone timer
		handler1 = new Handler();
		runnable1 = new Runnable(){
		    public void run() {
		    	Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
				r = RingtoneManager.getRingtone(getApplicationContext(), notification);
				r.play();
		    	dir.setText("Direction : "+sp.getString("direction", "Unidentified"));
		    	pb.setVisibility(View.INVISIBLE);
			  }
		};
		handler1.postAtTime(runnable1, System.currentTimeMillis()+interval);
		handler1.postDelayed(runnable1, interval);

		//notify timer
		handler2 = new Handler();
		runnable2 = new Runnable(){
		    public void run() {
		    	
		    	wakeLock.release();
//		    	keyguardLock.reenableKeyguard();
		    	wakeLock = null;
		    	keyguardLock = null;
				SharedPreferences sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
				if(sp.getBoolean("auto", false))
		        sendNotifications();
			  }
		};
		handler2.postAtTime(runnable2, System.currentTimeMillis()+20000);
		handler2.postDelayed(runnable2, 20000);
		
		
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
	 public void onBackPressed() {
	     // TODO Auto-generated method stub
	     if (r != null && r.isPlaying()) {
	         r.stop();

	     }
	     super.onBackPressed();
	 }
	 
	 @Override
	    public void onDestroy() {
	        // Don't forget to shutdown tts!
		  	if(r != null)
	            r.stop();	  
		 	if (tts != null) {
	            tts.stop();
	            tts.shutdown();
	        }
	    	handler1.removeCallbacks(runnable1);
	    	handler2.removeCallbacks(runnable2);
	    	if(wakeLock!=null){
	    	wakeLock.release();
	//    	keyguardLock.reenableKeyguard();
	    	}
	        super.onDestroy();
	    }



	@Override
	public void onClick(View v) {
		 sendNotifications(); 
		 if (r != null && r.isPlaying()) 
	         r.stop();
		 finish();
	}

	 
	void sendNotifications()
	{
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
	   			Toast.makeText(this, "Notifying Neighbours", Toast.LENGTH_SHORT).show();
	        }
		
		
	}
	void addRecord()
	{
        DatabaseHandler db = new DatabaseHandler(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
        String formattedDate = df.format(c.getTime());
        String [] arr = formattedDate.split(" "); 
        db.addContact(new Intrusions(arr[0], arr[1]+" "+arr[2]));		
        
        SharedPreferences sp1 = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();                                                         			
	      editor.putString("last_intrusion", "At "+arr[1]+" "+arr[2]+" on "+arr[0]);
	      editor.commit();
	}

	
}
