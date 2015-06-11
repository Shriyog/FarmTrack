package com.example.farmtrack;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ManageNeighbours extends Fragment {
	
	TextView contactNumber;
	ListView listView;
	View rootView ;
	Button buttonPickContact;
	CheckBox auto;
	public ManageNeighbours(){

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_manage_neighbours, container, false);
		contactNumber = (TextView)rootView.findViewById(R.id.textView4);
		auto = (CheckBox)rootView.findViewById(R.id.checkBox1);
		SharedPreferences sp1 = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);		
        auto.setChecked(sp1.getBoolean("auto", false));	
		
		auto.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		
		      SharedPreferences sp =getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
		      SharedPreferences.Editor editor = sp.edit();      
		      editor.putBoolean("auto", arg1);
		      editor.commit();
	
		      Toast.makeText(rootView.getContext(),"Settings saved" , Toast.LENGTH_LONG).show();
		
		}
		} );
		
		
		buttonPickContact = (Button)rootView.findViewById(R.id.button1);
		buttonPickContact.setOnClickListener(new Button.OnClickListener(){

			   @Override
			    public void onClick(View arg0) {


				   Intent pickContactIntent = new Intent( Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI );
				   pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				   startActivityForResult(pickContactIntent, 11);


			    }});
		showRecords();
        return rootView;
    }

		private void saveData(String str) {		
	      SharedPreferences sp =this.getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
	      SharedPreferences.Editor editor = sp.edit();                                                         			
	      editor.putString("contacts", loadData()+str+"  ");
	      editor.putBoolean("auto", false);
	      editor.commit();
	   }
	
	   private String loadData() {		
	      SharedPreferences sp = this.getActivity().getSharedPreferences("MyPrefs",
	         Context.MODE_PRIVATE);		
	      String str = sp.getString("contacts", "");
	      return str;
	   }
	
	public void showRecords()
	{
        // Get ListView object from xml
        listView = (ListView) rootView.findViewById(R.id.listView1);
        String []values = loadData().split("  ");
        if(loadData().length()<2)
        	return;
        for(int i = 0 ; i<values.length ; i++)
        {
        	String [] tmp;
        	tmp = values[i].split(",");
        	values[i]=" "+(i+1)+".    "+ tmp[0];
        }
        if(values.length==5)
    		buttonPickContact.setEnabled(false);
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
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	
	    String phoneNo = "", name = "";
	    if (resultCode == Activity.RESULT_OK) {
	    Uri uri = data.getData();
	    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
	    cursor.moveToFirst();

	    int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
	    phoneNo = cursor.getString(phoneIndex);
	    phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
	    name = cursor.getString(phoneIndex);
	    saveData(name+","+phoneNo);
	    showRecords();
	    }
	}
	


}