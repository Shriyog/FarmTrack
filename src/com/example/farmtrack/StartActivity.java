package com.example.farmtrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity implements OnClickListener {

	Button ok;
	EditText num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		ok = (Button) findViewById(R.id.button1);
		ok.setOnClickListener(this);
		num = (EditText) findViewById(R.id.editText1);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String str = ""+num.getText();
		if(str.equals("")||str.length()!=10)
			Toast.makeText(this,"Number Invalid",Toast.LENGTH_SHORT).show();
		else{
		saveData(str);
		num.setText("");
		num.clearFocus();
		Toast.makeText(this,"Number Registered successfully !",Toast.LENGTH_SHORT).show();
	    Intent launchIntent = new Intent(this, MainActivity.class);
	    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
	    startActivity(launchIntent);
		}
	}

	private void saveData(String str) {		
	      SharedPreferences sp = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
	      SharedPreferences.Editor editor = sp.edit();                                                         			
	      editor.putString("ph_no", str);
	      editor.putBoolean("status", false);
	      editor.commit();
	   }
}
