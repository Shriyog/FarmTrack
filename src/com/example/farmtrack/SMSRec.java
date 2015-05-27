package com.example.farmtrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

public class SMSRec extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) 
	{
		Bundle b=arg1.getExtras();
		SmsMessage []msg=null;
		String str="";
		if(b!=null)
		{
			Object[] pdus=(Object[])b.get("pdus");
			msg=new SmsMessage[pdus.length];
			for(int i=0;i<msg.length;i++)
			{
				msg[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
				str+="Messgae From "+msg[i].getOriginatingAddress();
				str+=" :";
				str+=msg[i].getMessageBody().toString();
				str+="\n";
			}

		    Intent launchIntent = new Intent(context, AlertActivity.class);
		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY );
			launchIntent.putExtra("sms", str);
		    context.startActivity(launchIntent);
		    
			Toast.makeText(context, str,Toast.LENGTH_SHORT).show();
			
			Intent broadcastIntent=new Intent();
			broadcastIntent.setAction("SMS_REC_ACTION");
			broadcastIntent.putExtra("sms", str);
			context.sendBroadcast(broadcastIntent);
		}
	}

}
