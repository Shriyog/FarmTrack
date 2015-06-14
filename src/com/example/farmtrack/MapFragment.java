package com.example.farmtrack;

import java.util.Timer;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MapFragment extends Fragment {

	
	View rootView;
    private static final int SELECT_PICTURE = 1;
    
    private String selectedImagePath;
    private ImageView img;
    public  ImageView arrow_west;
    private ImageView arrow_east;
    private ImageView arrow_north;
    private ImageView arrow_south;
    private ImageView alert;
    
    Timer timer;
    TextView nm;
    Animation flash;

    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
		        rootView = inflater.inflate(R.layout.fragment_map, container, false);
				SharedPreferences sp = this.getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);		
			    String file= sp.getString("image", "none");
			    nm = (TextView) rootView.findViewById(R.id.label);
			    String last = sp.getString("last_intrusion", "");
			    nm.setText(last);
			    
			    
		    img = (ImageView)rootView.findViewById(R.id.ImageView01);
		    Bitmap bmImg = BitmapFactory.decodeFile(file);
		    img.setImageBitmap(bmImg);
		    
		    arrow_west = (ImageView) rootView.findViewById(R.id.imageView1);
		    arrow_north = (ImageView) rootView.findViewById(R.id.imageView2);
		    arrow_east = (ImageView) rootView.findViewById(R.id.imageView3);
		    arrow_south = (ImageView) rootView.findViewById(R.id.imageView4);
		    alert =(ImageView) rootView.findViewById(R.id.imageView5);
		    
		    arrow_west.setVisibility(View.INVISIBLE);
		    arrow_north.setVisibility(View.INVISIBLE);
		    arrow_east.setVisibility(View.INVISIBLE);
		    arrow_south.setVisibility(View.INVISIBLE);

		    if(last.equals(""))
		    	alert.setVisibility(View.INVISIBLE);
		    flash = AnimationUtils.loadAnimation(this.getActivity(), R.drawable.blink);
		    
		    String dir = sp.getString("direction", null);

		    if(dir!=null){
		    	
		    if(dir.equals("West"))
		    arrow_west.startAnimation(flash);
		    if(dir.equals("North"))
		    arrow_north.startAnimation(flash);
		    if(dir.equals("East"))
		    arrow_east.startAnimation(flash);
		    if(dir.equals("South"))
		    arrow_south.startAnimation(flash);
		    }
	return rootView;
	}
		
}
