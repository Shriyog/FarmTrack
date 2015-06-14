package com.example.farmtrack.startup;

import java.util.Timer;

import com.example.farmtrack.MainActivity;
import com.example.farmtrack.NewStartActivity;
import com.example.farmtrack.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MapDirFragment  extends Fragment{
	
	View rootView;
    private static final int SELECT_PICTURE = 1;
    
    private String selectedImagePath;
    private ImageView img;
    public static ImageView arrow_left;
    private ImageView arrow_right;
    private ImageView arrow_top;
    private ImageView arrow_down;
    Timer timer;
    Animation flash;
    Context tmp;
    Button b1,b2;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
		        rootView = inflater.inflate(R.layout.fragment_dir_map, container, false);
		        tmp = getActivity().getApplicationContext();
		        img = (ImageView)rootView.findViewById(R.id.ImageView3);
				b1 = (Button) rootView.findViewById(R.id.button1);
		        
		        b1.setOnClickListener(new OnClickListener() {
		                    public void onClick(View arg0) {

		                        Intent intent = new Intent();
		                        intent.setType("image/*");
		                        intent.setAction(Intent.ACTION_GET_CONTENT);
		                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
		                    }
		            });
		        
				b2 = (Button) rootView.findViewById(R.id.button2);
		        
		        b2.setOnClickListener(new OnClickListener() {
		                    public void onClick(View arg0) {
		                    	
		                    	SharedPreferences sp = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
		                    	
		                    	if(sp.getString("ph_no", "none").equals("none"))
		                    	{
		                    		NewStartActivity.viewPager.setCurrentItem(3);
		                    		Toast.makeText(getActivity(), "Enter number !", Toast.LENGTH_SHORT).show();
		                    	}
		                    	else
		                    	{
		                    		if(sp.getString("image", "none").equals("none"))
			                    		Toast.makeText(getActivity(), "Select map screenshot !", Toast.LENGTH_SHORT).show();		                    		
		                    		else
			                    	{
			            			Intent launchIntent = new Intent(getActivity(), MainActivity.class);
			            		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
			            		    launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
			            		    startActivity(launchIntent);
			                    	}
		                    	}
		                    }
		            });
		        
	return rootView;
	}
	

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
                
//                img.setVisibility(View.INVISIBLE);
                
                //store image path
        		      SharedPreferences sp =this.getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        		      SharedPreferences.Editor editor = sp.edit();      
        		      editor.putString("image", selectedImagePath);
        		      editor.commit();
        		   
            }
        }
    }
 
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

	
	
}

