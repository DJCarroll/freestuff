package com.dj.craigslistapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListingContent  extends Activity {
	private static final String TAG = "ListingContentActivity";
	private FreebieApp app;
	TextView tv;
	Button callButton;
	Button emailButton;
	
    String[] cityNames = new String[50];
    String[] URLS = new String[50];
    private String userbody = "";
	private String returnEmail = "";
	private String returnPhone = "";
	private String homeAddress = "";
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			setContentView(R.layout.listingcontent);
			
			// for getting a message if necessary 
//			state = (String) getIntent().getSerializableExtra("message");
			
			// Find Views 
			app = (FreebieApp)getApplication();
			tv = (TextView) findViewById(R.id.listingcontenttext);
			callButton = (Button) findViewById(R.id.callButton);
			emailButton = (Button) findViewById(R.id.emailButton);
//			lv = (ListView) findViewById(R.id.subcitylist);
	        
	        // Set properties 
			tv.setMovementMethod(new ScrollingMovementMethod());
	        tv.setText(app.getState() + " - " + app.getCity() + "\n");

//	        ImageView Image01 = new ImageView(this);
//	        ImageView Image01 = (ImageView) findViewById(R.id.imageView1);
	        Source source = getSource();

	        userbody = source.getElementById("userbody").getContent().toString();
	        if(!userbody.equals("") || userbody != null)
	        {
	        	if(!app.isPic())
	        		tv.append(userbody.substring(0, userbody.indexOf("<!--")));		//userbody.substring(0,userbody.indexOf(string) ));
	        	else
	        	{
	        		tv.append(userbody.substring(0, userbody.indexOf("<script")));
	        		
	        		boolean pic1 = true;
	    	        List<StartTag> images =  null;
	    	        List<StartTag> subImages =  null;
//	S    	        ArrayList<ImageView> imagesArray = new ArrayList<ImageView>();
	    			try{
	    				images = source.getElementById("userbody").getAllElementsByClass("iw").get(0).getAllStartTags("img");
	    				subImages = source.getElementById("userbody").getAllElementsByClass("iw").get(0).getAllStartTags("a");
	    				Iterator<StartTag> it = subImages.iterator();
	    				int i = 0;
	    				while (it.hasNext() || pic1 == true) {
//	    					tv.append(it.next().toString() + "\n"); 
	    					String url;
	    					if(i==0)
	    					{
	    						url = images.get(0).getAttributeValue("src");
	    						pic1 = false;
	    					}					
	    					else
	    						url = it.next().getAttributeValue("href");
//	    					tv.append(url);
	    					Drawable image =ImageOperations(this,url);
	    					if( image == null)
	    						tv.append("this fucked up");
	    					
	    					switch(i){
	    					case 0:
		    					ImageView Image01 = (ImageView) findViewById(R.id.imageView1);
		    					Image01.setImageDrawable(image);
	    						break;
	    					case 1:
		    					ImageView Image02 = (ImageView) findViewById(R.id.imageView2);
		    					Image02.setImageDrawable(image);
	    						break;
	    					case 2:
		    					ImageView Image03 = (ImageView) findViewById(R.id.imageView3);
		    					Image03.setImageDrawable(image);
	    						break;
	    					case 3:
		    					ImageView Image04 = (ImageView) findViewById(R.id.imageView4);
		    					Image04.setImageDrawable(image);
	    						break;
	    					case 4:
		    					ImageView Image05 = (ImageView) findViewById(R.id.imageView5);
		    					Image05.setImageDrawable(image);
	    						break;
	    					case 5:
		    					ImageView Image06 = (ImageView) findViewById(R.id.imageView6);
		    					Image06.setImageDrawable(image);
	    						break;
	    					case 6:
		    					ImageView Image07 = (ImageView) findViewById(R.id.imageView7);
		    					Image07.setImageDrawable(image);
	    						break;
	    					case 7:
		    					ImageView Image08 = (ImageView) findViewById(R.id.imageView8);
		    					Image08.setImageDrawable(image);
	    						break;    					
	    					}
	    					
	    					ImageView Image01 = (ImageView) findViewById(R.id.imageView1);
	    					Image01.setImageDrawable(image);
	    					
//	    					imagesArray.add(new ImageView(this));
//	    					imagesArray.get(i).findViewById(R.id.imageView1);
//	    					imagesArray.get(i).setImageDrawable(image);
	    					i++;
	    				}
	    	        }
	    	        catch(Exception ex)
	    	        {
	    	            ex.printStackTrace();
	    	        }

//	    			int width = 300;
//	    			int height = 300;
//	    	        Image01.setMinimumWidth(width);
//	    	        Image01.setMinimumHeight(height);
//
//	    	        Image01.setMaxWidth(width);
//	    	        Image01.setMaxHeight(height);
	        	}
	        
	        }
	        else
	        	tv.append("error");
	        
	        returnPhone = "tel:" + getPhone();
	        Element email =  source.getFirstElementByClass("returnemail").getFirstElement("a");
	        if(email != null)
	        	returnEmail = email.getContent().toString();
	        else returnEmail = "none";


	        tv.append(" \n  \n "  + returnEmail  + "\n " + returnPhone + "\n " + homeAddress);

	        // call button clicked
	        callButton.setOnClickListener(  new OnClickListener() { 	  
	        	// called when button is clicked
	        	public void onClick(View v){
	        		if(!returnPhone.equals(""))
	        		{
	        			Log.d(TAG,"onClicked");
	        			// start call 
	        			try {
	        				Intent callIntent = new Intent(Intent.ACTION_CALL);
	        				callIntent.setData(Uri.parse(returnPhone));
	        				startActivity(callIntent);
	        			} catch (Exception e) {
	        				Log.e(TAG, "Call failed", e);
	        			}
	        		}
	        		else
	        			Toast.makeText(ListingContent.this, "cant make call as dialed", Toast.LENGTH_SHORT).show();
	        	}
	        });
	        
	        // call button clicked
	        emailButton.setOnClickListener(  new OnClickListener() { 	  
	        	// called when button is clicked
	        	public void onClick(View v){
	        		if(!returnEmail.equals("") && !returnEmail.equals("none"))
	        		{
	        			Log.d(TAG,"onClicked");
	        			// send Email
	        			Intent i = new Intent(Intent.ACTION_SEND);
	        			i.setType("plain/text");			//"message/rfc822");
	        			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{returnEmail});
	        			i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");			// pull subject ... 
	        			i.putExtra(Intent.EXTRA_TEXT   , "body of email"); 				 //  eventually ask user for default resonse email
	        			try {
	        			    startActivity(Intent.createChooser(i, "Send mail..."));
	        			} catch (android.content.ActivityNotFoundException ex) {
	        			    Toast.makeText(ListingContent.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	        			}			
	        		}
	        		else
	        			Toast.makeText(ListingContent.this, "cant create email to:  " + returnEmail, Toast.LENGTH_SHORT).show();

	        	}
	        });
	        
	        
	    }
	    
	    
		private String getPhone() {
        	Pattern p = Pattern.compile("\\d{3}.{0,4}\\d{3}.{0,4}\\d{4}");
        	Matcher m = p.matcher(userbody);
        	if(m.find())
        	{
        		return m.group().trim().replaceAll("\\D", "");
        	}
        	else return "";
		}

		private Source getSource() {

			Source source = null;
			try {
				source = new Source(new URL(app.getListingURL()));
			} catch (Exception e){
				tv.append("\n" + e.getClass().getName());
				// TODO Auto-generated catch block
				e.printStackTrace();
				tv.append("\nfail in getting website");
			}
			if(source == null)
			{
				Toast.makeText(ListingContent.this, "Error in getting content", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, FinActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
				
			source.fullSequentialParse();
			return source;

		}
		
		public Object fetch(String address) throws MalformedURLException,
	    IOException {
	        URL url = new URL(address);
	        Object content = url.getContent();
	        return content;
	    }  

	    private Drawable ImageOperations(Context ctx, String url) {
	        try {
	            InputStream is = (InputStream) this.fetch(url);
	            Drawable d = Drawable.createFromStream(is, "src");
	            return d;
	        } catch (MalformedURLException e) {
	            return null;
	        } catch (IOException e) {
	            return null;
	        }
	    }

}