package com.dj.craigslistapp;

import java.net.URL;
import java.util.ArrayList;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FindStuff extends Activity {
	private static final String TAG = "FindStuffActivity";
	private FreebieApp app;
	TextView tv;
	ListView lv;
    ArrayList<String> listingsTitles = new ArrayList<String>();
    ArrayList<String> listingsURLS = new ArrayList<String>();
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			setContentView(R.layout.findstuff);
			
			// for getting a message if necessary 
//			String message = (String) getIntent().getSerializableExtra("message");
		    
			// Find Views 
			app = (FreebieApp)getApplication();
			tv = (TextView) findViewById(R.id.findstufftext);
			lv = (ListView) findViewById(R.id.stufflist);
			
	        
	        // Set properties 
//			tv.setMovementMethod(new ScrollingMovementMethod());
			String loc = app.getLocationURL();
	        tv.setText(loc);

	        if(loc.trim().isEmpty())
	        {
	        	tv.append("fail...");
	        	Log.d(TAG,"no location found");
	        	// send to location activity to retrieve location. 
	        	Bundle b = new Bundle();
	        	b.putString("message","Please Select a Location ");
	        	Intent i = new Intent(FindStuff.this,Location.class);
	        	i.putExtras(b);
	        	startActivity(i);
	        }
	        else
	        {

		        Source source = getSource();


		        int i = 0;
		        List<Element> listings = getListings(source);

	// Code to create List of clicakble states. Once clicked, we will show the list of cities to choosefrom.	       
	        

		        Iterator<Element> iterator = listings.iterator();
		        while (iterator.hasNext()) {
		        	Segment curr = iterator.next().getContent();
		        	String post = curr.getFirstElement("a").toString();
		        	String location = app.getCity();
		        	if(!curr.getAllElementsByClass("itempn").get(0).isEmpty())
		        		location = curr.getAllElementsByClass("itempn").get(0).getChildElements().get(0).getContent().toString();
		        	
		        	if(curr.getAllElementsByClass("itempx").get(0).isEmpty())
		        		listingsTitles.add(post.substring(post.indexOf("\">")+2, post.length()-4) + location);
		        	else
		        		listingsTitles.add(post.substring(post.indexOf("\">")+2, post.length()-4) + location + "  (PIC) ");

		        	listingsURLS.add(curr.getFirstStartTag("a").getAttributeValue("href"));
		        	i++;
		        }

//		     // First paramenter - Context
//		     // Second parameter - Layout for the row
//		     // Third parameter - ID of the View to which the data is written
//		     // Forth - the Array of data

		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        		android.R.layout.simple_list_item_2, android.R.id.text2, listingsTitles);

		        if(adapter == null)
		        	tv.append("fail...");
		        
//		        // Assign adapter to ListView
		        lv.setAdapter(adapter);
		        lv.setOnItemClickListener(new OnItemClickListener() {
		        	public void onItemClick(AdapterView<?> parent, View view,
		        		int position, long id) {
		            	Log.d(TAG,"onClicked");
		            	// send to listings actual page
		            	if(listingsTitles.get(position).contains("PIC"))
		            		app.setPic(true);
		            	else
		            		app.setPic(false);
		            	app.setListingURL(listingsURLS.get(position));
		            	Intent i = new Intent(FindStuff.this,ListingContent.class);
		            	startActivity(i);
		        	}
		        });
		        
	        }
        
		}
	    
		private Source getSource() {
//			if(!app.findStuffSource)
//			{
				String craigsListURL = app.getLocationURL().concat("zip/");
				tv.append(craigsListURL);
				Source source = null;
				try {
					source = new Source(new URL(craigsListURL));
				} catch (Exception e){
					tv.append("\n" + e.getClass().getName());
					e.printStackTrace();
					tv.append("\nfail in getting website");
				}
				source.fullSequentialParse();
				app.setSource(source);
				app.findStuffSource=true;
				return source;
//			}
//			else return app.getSource();

		}

		private static List<Element> getListings(Source source){
			
			Segment list = new Segment(source,0,source.length());
			List<Element> listings = list.getAllElementsByClass("row");
			
			return listings;		
		}
	
}
