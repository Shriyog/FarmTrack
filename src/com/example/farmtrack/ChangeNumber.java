package com.example.farmtrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract.Root;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.farmtrack.Contact;
import com.example.farmtrack.DatabaseHandler;

public class ChangeNumber extends Fragment implements OnClickListener{
	
	public ChangeNumber(){}
	TextView no;
	Button bt;
	EditText num;
	Button buttonPickContact; 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView= inflater.inflate(R.layout.fragment_change_number, container, false);
        no = (TextView) rootView.findViewById(R.id.textView3); 
        bt = (Button) rootView.findViewById(R.id.button1);
        bt.setOnClickListener(this);
        num = (EditText) rootView.findViewById(R.id.editText1);
        loadData();
        
        buttonPickContact = (Button)rootView.findViewById(R.id.button2);
		buttonPickContact.setOnClickListener(new Button.OnClickListener(){

			   @Override
			    public void onClick(View arg0) {
			   // TODO Auto-generated method stub


				   Intent pickContactIntent = new Intent( Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI );
				   pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				   startActivityForResult(pickContactIntent, 11);


			    }});
        
        return rootView;
    }

		private void saveData(String str) {		
	      SharedPreferences sp =this.getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
	      SharedPreferences.Editor editor = sp.edit();                                                         			
	      editor.putString("ph_no", str);
	      editor.commit();
	   }
	
	   private void loadData() {		
	      SharedPreferences sp = this.getActivity().getSharedPreferences("MyPrefs",
	         Context.MODE_PRIVATE);		
	      String str = sp.getString("ph_no", "Please enter no.");
	      no.setText("Current Number : "+str);
	   }
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1){
		String str = ""+num.getText();
		if(str.equals("")||str.length()!=10)
			Toast.makeText(this.getActivity(),"Number Invalid",Toast.LENGTH_SHORT).show();
		else{
		saveData(str);
		loadData();
		num.setText("");
		num.clearFocus();
		Toast.makeText(this.getActivity(),"Number Updated successfully !",Toast.LENGTH_SHORT).show();
		}
		}
	}
	
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	
	    String phoneNo = "";
	    if (resultCode == Activity.RESULT_OK) {
	    Uri uri = data.getData();
	    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
	    cursor.moveToFirst();

	    int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
	    phoneNo = cursor.getString(phoneIndex);
	    num.setText(""+phoneNo);
	    }
	}

	

}
