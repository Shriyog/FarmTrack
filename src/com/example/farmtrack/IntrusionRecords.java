package com.example.farmtrack;

import java.util.List;

import com.example.farmtrack.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class IntrusionRecords extends Fragment implements OnClickListener{
	
	public IntrusionRecords(){}
	ListView listView;
	Button clear;
	DatabaseHandler db;
	View rootView;
	int cnt = 0;
	Activity tmp ;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        db = new DatabaseHandler(this.getActivity());
        rootView = inflater.inflate(R.layout.fragment_intrusion_records, container, false);

        showRecords();
        return rootView;
    }
	
	public void showRecords()
	{
        // Get ListView object from xml
        listView = (ListView) rootView.findViewById(R.id.listView1);
        clear = (Button) rootView.findViewById(R.id.button1);
        clear.setOnClickListener(this);
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();       
 
        int n=contacts.size(),i=0;
        cnt = n;
        // Defined Array values to show in ListView
        String[] values = new String[n];
        for (Contact cn : contacts) {
        	values[i] = "  "+(i+1)+"        " + cn.getName() + "       " + cn.getPhoneNumber();
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
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1)
		{
			tmp =this.getActivity();
			alertMessage();
		
		}
	}
	
	
	public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                     switch (which) {
                     case DialogInterface.BUTTON_POSITIVE:
                            // Yes button clicked
                    	 	db.deleteAll();
            				Toast.makeText(tmp,cnt+" Records deleted",Toast.LENGTH_SHORT).show();
            				showRecords();
            				break;

                     case DialogInterface.BUTTON_NEGATIVE:
                            // No button clicked
                            // do nothing
                            break;
                     }
               }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Are you sure?")
                     .setPositiveButton("Yes", dialogClickListener)
                     .setTitle("Delete Records")
                     .setNegativeButton("No", dialogClickListener).show();
 }


}
