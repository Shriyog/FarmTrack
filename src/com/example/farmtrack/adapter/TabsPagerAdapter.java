package com.example.farmtrack.adapter;

import com.example.farmtrack.startup.MapDirFragment;
import com.example.farmtrack.startup.NumberFragment;
import com.example.farmtrack.startup.Screen_3_Fragment;
import com.example.farmtrack.startup.Screen_1_Fragment;
import com.example.farmtrack.startup.Screen_2_Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new Screen_1_Fragment();
		case 1:
			// Games fragment activity
			return new Screen_2_Fragment();
		case 2:
			// Movies fragment activity
			return new Screen_3_Fragment();
		case 3:
			// Movies fragment activity
			return new NumberFragment();
		case 4:
			// Movies fragment activity
			return new MapDirFragment();			
		
		
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
