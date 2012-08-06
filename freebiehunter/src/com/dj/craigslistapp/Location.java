package com.dj.craigslistapp;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Location extends Activity {
	private static final String TAG = "LocationActivity";
	private FreebieApp app;
	TextView tv;
	ListView lv;
	private String state = "";
    String[] states = new String[50];
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			setContentView(R.layout.location);
			
			// for getting a message if necessary 
			String message = (String) getIntent().getSerializableExtra("message");
		    
			// Find Views 
			app = (FreebieApp)getApplication();
			tv = (TextView) findViewById(R.id.locationtext);
			lv = (ListView) findViewById(R.id.statelist);
			
	        
	        // Set properties 
	        tv.setText(message);

	        Source source = null;
	        String craigsListURL = "http://www.craigslist.org/about/sites";

	        try {
	        	source = new Source(new URL(craigsListURL));
	        } catch (Exception e){
	        	tv.append("\n" + e.getClass().getName());
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        	tv.append("\nfail in getting website");
	        	Intent i = new Intent();
	        	i.setAction(Intent.ACTION_MAIN);
	        	i.addCategory(Intent.CATEGORY_HOME);
	        	Location.this.startActivity(i); 
	        	finish();  
	        }

	        source.fullSequentialParse();
	        app.setSource(source);

	        int i = 0;
	        List<Element> state_list = getStateNames(source);
	        app.setState_list(state_list);

// Code to create List of clicakble states. Once clicked, we will show the list of cities to choosefrom.	       
        
	        Iterator<Element> iterator = state_list.iterator();
	        while (iterator.hasNext()) {
	        	if(i >= 50)
	        		break;
	        	states[i] = iterator.next().getContent().toString() + "\n";
	        	i++;
	        }
	        
	        app.setStates(states);
	     // First paramenter - Context
	     // Second parameter - Layout for the row
	     // Third parameter - ID of the View to which the data is written
	     // Forth - the Array of data
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_list_item_1, android.R.id.text1, states);

	        // Assign adapter to ListView
	        lv.setAdapter(adapter);
	        lv.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View view,
	        		int position, long id) {
	        		state = states[position];
	        		app.setState(state.trim());
	            	Log.d(TAG,"onClicked");
	            	Log.d(TAG,state);
	            	// send to location activity to retreive location. 
//	            	Intent i = new Intent("com.dj.craigslistapp.CityList");
	            	Intent i = new Intent(Location.this,CityList.class);
	            	startActivity(i);
	        	}
	        });
	        
	        
		}
	    
		private static List<Element> getStateNames(Source source){
			
			Segment states = new Segment(source,0,source.length());
			List<Element> state_list = states.getAllElementsByClass("state_delimiter");
			
			return state_list;		
		}
	
}
