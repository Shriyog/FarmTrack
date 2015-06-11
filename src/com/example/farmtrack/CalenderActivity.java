package com.example.farmtrack;

import com.example.farmtrack.R;
import com.example.farmtrack.cal.ExtendedCalendarView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CalenderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calender);
		ExtendedCalendarView calendar = (ExtendedCalendarView)findViewById(R.id.calendar);

	}
}
