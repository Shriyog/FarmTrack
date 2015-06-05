package com.example.farmtrack;

import java.util.List;

import com.example.farmtrack.cal.ExtendedCalendarView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalenderFragment extends Fragment{
	
	public CalenderFragment(){}
	DatabaseHandler db;
	View rootView;
	int cnt = 0;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

    	@SuppressWarnings("unused")
		ExtendedCalendarView calendar = (ExtendedCalendarView)rootView.findViewById(R.id.calendar);
        return rootView;
    }
	

}
