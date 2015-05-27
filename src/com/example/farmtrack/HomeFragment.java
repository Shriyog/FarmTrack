package com.example.farmtrack;

import com.example.farmtrack.R;

import android.app.Fragment;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HomeFragment extends Fragment implements OnClickListener,OnCheckedChangeListener {
	
	//sms
	String num="9421315125";
	Switch onoff;
	EditText textF;
	TextView status;
	IntentFilter intentFilter;
	//end sms

	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        onoff = (Switch) rootView.findViewById(R.id.switch1);
        status = (TextView) rootView.findViewById(R.id.textView2);        
       // btnStat = (Button) rootView.findViewById(R.id.button1);
      

      //set the switch to ON 
        onoff.setChecked(true);
        //attach a listener to check for changes in state
        onoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

         @Override
         public void onCheckedChanged(CompoundButton buttonView,
           boolean isChecked) {

          if(isChecked){
           status.setText("System status : ON");
           String msg="on";
           SmsManager sm=SmsManager.getDefault();
   			sm.sendTextMessage(num,null, msg,null,null);
   			//sm.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent)
   		
   			Toast.makeText(getActivity(), "Turning ON the system", Toast.LENGTH_SHORT).show();

          }else{
           status.setText("System status : OFF");
		   String msg="off";
		   SmsManager sm=SmsManager.getDefault();
		   sm.sendTextMessage(num,null, msg,null,null);
			//sm.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent)
			
			Toast.makeText(getActivity(), "Turning OFF the system", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	//	String str = textF.getText();
		status.setText("on");
	}
}
