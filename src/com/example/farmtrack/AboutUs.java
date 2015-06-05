package com.example.farmtrack;

import com.example.farmtrack.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutUs extends Fragment {
	
	public AboutUs(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.about_us, container, false);
         
        return rootView;
    }
}
