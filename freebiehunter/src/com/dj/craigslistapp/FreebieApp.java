package com.dj.craigslistapp;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import android.app.Application;

public class FreebieApp extends Application {  
	  
    //Variable we want to share to All Activities in Appliction
  private static final String NAME = "MyApplication"; 
        private String name;
        private String state;
        private String city = "";
		private Source source = null;
        private String[] states = new String[50];
        private List<Element> state_list;
        private String locationURL = "";
        private String[] citiesWithSubLocations = {"phoenix" , "los angeles","san diego","san francisco bay area","District of Columbia","fort lauderdale","ft myers / SW florida",
        		"south florida","tampa bay area","atlanta","hawaii","chicago","boston","detroit metro","minneapolis / st paul","new york city","portland",
        		"dallas / fort worth","seattle-tacoma"};
        public int test = 0;
		public boolean findStuffSource = false;
		private String listingURL;
		private boolean pic = false;

        
        public boolean isPic() {
			return pic;
		}
		public void setPic(boolean pic) {
			this.pic = pic;
		}
		public String getListingURL() {
			return listingURL;
		}
		public void setListingURL(String listingURL) {
			this.listingURL = listingURL;
		}
		public String[] getCitiesWithSubLocations() {
			return citiesWithSubLocations;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}      
        public String getLocationURL() {
			return locationURL;
		}
		public void setLocationURL(String locationURL) {
			this.locationURL = locationURL;
		}
		public List<Element> getState_list() {
			return state_list;
		}
		public void setState_list(List<Element> state_list) {
			this.state_list = state_list;
		}
		public Source getSource() {
			return source;
		}
		public void setSource(Source source) {
			this.source = source;
		}
		public String[] getStates() {
			return states;
		}
		public void setStates(String[] states) {
			this.states = states;
		}
		@Override
        public void onCreate() {  
                super.onCreate();  
                name = NAME; //Initialize global variables 
        }  
        //Getter Method
        public String getState() {    
                return state;  
        }  
        //Setter Method
        public void setState(String state) {  
                this.state = state;  
        }

} 
