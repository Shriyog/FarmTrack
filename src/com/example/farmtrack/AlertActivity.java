package com.example.farmtrack;

import java.util.Locale;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AlertActivity extends Activity implements OnInitListener {

	TextView textViewInfo;
	GifView gifView;
	TextToSpeech tts;
	Ringtone r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert);
		final int interval = 3000; // 1 Second
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

}
