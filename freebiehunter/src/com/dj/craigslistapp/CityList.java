package com.dj.craigslistapp;

import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Tag;
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

public class CityList extends Activity {
	private static final String TAG = "CityListActivity";
	private FreebieApp app;
	TextView tv;
	ListView lv;
	private String state = "";
    String[] cityNames = new String[50];
    String[] URLS = new String[50];
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			setContentView(R.layout.city);
			
			// for getting a message if necessary 
//			state = (String) getIntent().getSerializableExtra("message");
			
			// Find Views 
			app = (FreebieApp)getApplication();
			tv = (TextView) findViewById(R.id.citytext);
			lv = (ListView) findViewById(R.id.citylist);
			state = app.getState();
	        
	        // Set properties 
	        tv.setText(state + "\n");


	        int i = 0;
	        List<Element> city_list = null;
	        List<Element> state_list = app.getState_list();
	        
	        // pull list of cities for the passed in state.
	        i=0;
	        Iterator<Element> iterator = state_list.iterator();
	        iterator = state_list.iterator();
			Tag cities = null;
			while (iterator.hasNext()) {
				Element curr = iterator.next();
				if(curr.getContent().toString().equals(state))
				{
					// pull cities
					cities = curr.getEndTag().getNextTag();
					break;
				}
				if(i >= 50)
				{
					tv.append("error ... ");
					break;
				}
				i++;
			}

			i = 0;
			// list all cities on Screen 
			city_list = cities.getElement().getChildElements();
			iterator = city_list.iterator();
			Segment city = null; String city_name = "";
			while (iterator.hasNext()) {
				city = iterator.next().getFirstElement().getContent();

				city_name = city.subSequence(0, city.length()).toString();
				URLS[i] = city_name.substring(city_name.indexOf("http"),city_name.indexOf("\">"));
				cityNames[i] = city_name.substring((city_name.indexOf("\">") + 2),city_name.indexOf("</a>")); //
				i++;
			}
			
			String[] cityN = new String[cityNames.length];
			System.arraycopy(cityNames, 0, cityN, 0, cityNames.length);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_list_item_1, android.R.id.text1, cityN);
			
	        // Assign adapter to ListView
	        lv.setAdapter(adapter);
	        
			// Listener for city clicked...
	        lv.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View view,
	        		int position, long id) {
	        		app.setCity(cityNames[position]);
	        		app.setLocationURL(URLS[position]);
	            	Log.d(TAG,"onClicked");
	            	boolean big = false;
	            	
	        		String[] citiesWithSubLocations = app.getCitiesWithSubLocations();
	        		for(int i=0;i<citiesWithSubLocations.length;i++)
	        		{
	        			if(citiesWithSubLocations[i].equals(cityNames[position]))
	        			{
	    	            	Intent j = new Intent(CityList.this,SubCityList.class);
	    	            	startActivity(j);
	    	            	big = true;
	        			}
	        		}
	        		if(!big)
	        		{
//	        			 send to main screen if no further selection is required
		            	Intent a = new Intent(CityList.this,FreebiehunterActivity.class);
		            	startActivity(a);
	        		}

	        	}
	        });
			
	    }

}
