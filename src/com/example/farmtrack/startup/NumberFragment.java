package com.example.farmtrack.startup;

import com.example.farmtrack.MainActivity;
import com.example.farmtrack.NewStartActivity;
import com.example.farmtrack.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NumberFragment  extends Fragment implements OnClickListener{


	EditText num;
	Button ok;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_number, container, false);
		
	
		num = (EditText) rootView.findViewById(R.id.editText1);
		ok = (Button) rootView.findViewById(R.id.button1);
		ok.setOnClickListener(this);

		return rootView;
		
	}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		String str = ""+num.getText();
		if(str.equals("")||str.length()!=10)
			Toast.makeText(this.getActivity(),"Number Invalid",Toast.LENGTH_SHORT).show();
		else{
		saveData(str);
		num.setText("");
		num.clearFocus();
//		Toast.makeText(this.getActivity(),"Number Registered successfully !",Toast.LENGTH_SHORT).show();

		NewStartActivity.viewPager.setCurrentItem(4);
		}
	
		
	
	}
		
		
	private void saveData(String str) {		
	      SharedPreferences sp = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
	      SharedPreferences.Editor editor = sp.edit();                                                         			
	      editor.putString("ph_no", str);
	      editor.putBoolean("status", false);
	      editor.commit();
	   }
	
	


}
