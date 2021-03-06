package com.dj.craigslistapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FreebiehunterActivity extends Activity{
	
	private static final String TAG = "FreebieHunterActivity";
	Button locationButton;
	Button findStuffButton;
	TextView tv;
	private FreebieApp app;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      // Find Views
      app = (FreebieApp)getApplication();
      locationButton = (Button) findViewById(R.id.buttonLocation);
      findStuffButton = (Button) findViewById(R.id.buttonfindStuff);
      tv = (TextView) findViewById(R.id.mainText);
      
      
      String FILENAME = "location";
      byte [] loc = new byte[99];
      FileInputStream fos = null;
      if(app.getLocationURL().equals(""))
      {
    	  try {
    		  fos = openFileInput(FILENAME);
    		  fos.read(loc);
    		  fos.close();
    		  app.setLocationURL(new String(loc));
    	  } catch (FileNotFoundException e1) {
    		  e1.printStackTrace();
    	  }
    	  catch (IOException e) {
    		  e.printStackTrace();
    	  }
      }

      // Set Attributes
      locationButton.setOnClickListener(  new OnClickListener() { 	  
      // called when button is clicked
    	  public void onClick(View v){
      	Log.d(TAG,"onClicked");
      	// send to location activity to retrieve location. 
      	Bundle b = new Bundle();
      	b.putString("message","Location Activity Started");
      	Intent i = new Intent(FreebiehunterActivity.this,Location.class);
      	i.putExtras(b);
      	startActivity(i);
        }
      });
      
      findStuffButton.setOnClickListener(  new OnClickListener() { 	  
          // called when button is clicked
        		  public void onClick(View v){
          	Log.d(TAG,"onClicked");
          	// send to location activity to retrieve location. 
          	Bundle b = new Bundle();
          	b.putString("message","FindStuff Activity Started");
          	Intent i = new Intent(FreebiehunterActivity.this,FindStuff.class);
          	i.putExtras(b);
          	startActivity(i);
            }
          });
      tv.setText(app.getCity() + "\n" + app.getLocationURL() + "\n");

  }
    
    

  
}