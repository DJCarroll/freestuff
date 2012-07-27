package com.dj.craigslistapp;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
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

public class SubCityList extends Activity {
	private static final String TAG = "SubCityListActivity";
	private FreebieApp app;
	TextView tv;
	ListView lv;
    String[] cityNames = new String[50];
    String[] URLS = new String[50];
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			setContentView(R.layout.subcities);
			
			// for getting a message if necessary 
//			state = (String) getIntent().getSerializableExtra("message");
			
			// Find Views 
			app = (FreebieApp)getApplication();
			tv = (TextView) findViewById(R.id.subcitytext);
			lv = (ListView) findViewById(R.id.subcitylist);
	        
	        // Set properties 
	        tv.setText(app.getState() + " - " + app.getCity() + "\n");


	        Source source = null;

	        try {
	        	source = new Source(new URL(app.getLocationURL()));
	        } catch (Exception e){
	        	tv.append("\n" + e.getClass().getName());
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        	tv.append("\nfail in getting website");
	        }

	        Element topBan = source.getElementById("topban");
	        List<Element> areas = topBan.getAllElementsByClass("sublinks");
        
	        int i = 0;
	   
	        Iterator<Element> iterator = areas.iterator();
			List<StartTag> subCities = null;
			while (iterator.hasNext()) {
				Element curr = iterator.next();
//				tv.append("\n" + curr.getContent().toString());
				subCities = curr.getAllStartTags();
			}


			// list all cities on Screen 
			Iterator<StartTag> it = subCities.iterator();
			Segment city = null; String city_name = "";
			city = it.next();
			while (it.hasNext()) {
//				tv.append(it.next().toString() + "\n");        //getFirstElement().getContent();
				city = it.next();

				city_name = city.subSequence(0, city.length()).toString();
				if(city_name.equals("") || city_name == null)
				{
					continue;
				}
//				tv.append("\n" + city_name);
				URLS[i] = city_name.substring(9,14);
				cityNames[i] = city_name.substring(23,city_name.length()-2); 
//				tv.append("\n" + URLS[i] + "  " + cityNames[i] +  "\n");
				i++;
				if(i>=50)
					break;
			}
			
			String[] cityN = new String[i];
			System.arraycopy(cityNames, 0, cityN, 0, i);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_list_item_2, android.R.id.text2, cityN);
	        
	        // Assign adapter to ListView
	        lv.setAdapter(adapter);
	        
			// Listener for city clicked...
	        lv.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent, View view,
	        		int position, long id) {
	        		app.setCity(app.getCity().concat(" " + cityNames[position]));
	        		app.setLocationURL(app.getLocationURL().concat(URLS[position]));
	            	Log.d(TAG,"onClicked");
	            	Intent i = new Intent(SubCityList.this,FreebiehunterActivity.class);
	            	startActivity(i);
	        	}
	        });
			
	    }

}