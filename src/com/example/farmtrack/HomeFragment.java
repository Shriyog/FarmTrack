package com.example.farmtrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import com.example.farmtrack.R;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener,OnCheckedChangeListener {
	
	String num="";
	Switch onoff;
	EditText textF;
	TextView status,freq;
	boolean stat ;
	IntentFilter intentFilter;
	DatabaseHandler db;
	ListView listView;
	View rootView;
	private  ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarbHandler = new Handler();
   	private long fileSize = 0;
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
		
		SharedPreferences sp = this.getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);		
	    num= sp.getString("ph_no", "none");
	    stat = sp.getBoolean("status", false);
	    db = new DatabaseHandler(this.getActivity());
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        showRecords();
        onoff = (Switch) rootView.findViewById(R.id.switch1);
        status = (TextView) rootView.findViewById(R.id.textView2);        
        freq = (TextView) rootView.findViewById(R.id.textView5);
                
        freq.setText(""+analyze());
      //set the switch to system state
        onoff.setChecked(stat);
        //attach a listener to check for changes in state
        onoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

         @Override
         public void onCheckedChanged(CompoundButton buttonView,
           boolean isChecked) {	  	 
        	 
        if(!num.equals("none"))
        {
        	if(isChecked){
           status.setText("System status : ON");
           String msg="on";
           updateStatus(true);
           SmsManager sm=SmsManager.getDefault();
   			sm.sendTextMessage(num,null, msg,null,null);
   			//sm.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent)
   		
   			Toast.makeText(getActivity(), "Turning the system ON", Toast.LENGTH_SHORT).show();

          }else{
           status.setText("System status : OFF");
		   String msg="off";
           updateStatus(false);
		   SmsManager sm=SmsManager.getDefault();
		   sm.sendTextMessage(num,null, msg,null,null);
			//sm.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent)
			
			Toast.makeText(getActivity(), "Turning the system OFF", Toast.LENGTH_SHORT).show();
          }

           progress();
        }
        else {
			Toast.makeText(getActivity(), "Please change the number", Toast.LENGTH_SHORT).show();
		}
         }
         
        });
        
        //check the current state before we display the screen
        if(onoff.isChecked()){
        	status.setText("System status : ON");
        }
        else {
        	status.setText("System status : OFF");
        	
        }

        return rootView;
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

	void updateStatus(boolean st)
	{
		  SharedPreferences sp = this.getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
	  	  SharedPreferences.Editor editor = sp.edit();                                                         			
  	      editor.putBoolean("status", st);
  	      editor.commit();
  
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	//	String str = textF.getText();
		status.setText("on");
	}
	
	
	public void showRecords()
	{
        // Get ListView object from xml
        listView = (ListView) rootView.findViewById(R.id.listView1);
      
        // Reading all contacts
        List<Contact> contacts = db.getAllContacts();       
 
        int n=contacts.size(),i=0;
        if(n>3)
        	n=3;
        // Defined Array values to show in ListView
        String[] values = new String[n];
        for (Contact cn : contacts) {
        	values[i] = "  "+(i+1)+"        " + cn.getName() + "       " + cn.getPhoneNumber();
        	if(i==2)
        		break;
        	i++;
        }

        
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);  	
	}

	public String analyze()
	{
		List<Contact> contacts = db.getAllContacts();       
		int [] array = new int[contacts.size()];
		
		if(contacts.size()>1){
			int x=0;
		for (Contact cn : contacts) {
			String str = ""+cn.getPhoneNumber();
			String tmp [] = str.split(":");
			array[x] = Integer.parseInt(""+tmp[0]);
			x++;
		}
		
		int count = 1, tempCount;
		  int popular = array[0];
		  int temp = 0;
		  for (int i = 0; i < (array.length - 1); i++)
		  {
		    temp = array[i];
		    tempCount = 0;
		    for (int j = 1; j < array.length; j++)
		    {
		      if (temp == array[j])
		        tempCount++;
		    }
		    if (tempCount > count)
		    {
		      popular = temp;
		      count = tempCount;
		    }
		  }
		if(popular>12){
			popular-=12;
			return "Frequent Intrusions at "+popular+" to "+(popular+1)+" PM";
		}
		else
		return "Frequent Intrusions at "+popular+" to "+(popular+1)+" AM";
		}
		return "Analysing data";
	}
	
	void progress()
	{
		progressBar = new ProgressDialog(rootView.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Processing ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        
        fileSize = 0;
        new Thread(new Runnable() {
           public void run() {
              while (progressBarStatus <= 5) {
                 progressBarStatus++;
                 
                 try {
                    Thread.sleep(1000);
                 }
                 
                 catch (InterruptedException e) {
                    e.printStackTrace();
                 }
                 
                 progressBarbHandler.post(new Runnable() {
                    public void run() {
                       progressBar.setProgress(progressBarStatus);
                    }
                 });
              }
              
              if (progressBarStatus >= 5) {
                
                 progressBar.dismiss();
              }
           }
        }).start();
     }

	
	
}
