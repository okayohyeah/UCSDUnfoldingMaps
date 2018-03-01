package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Katherine Oh
 * Date: February 28, 2018
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes - RSS FEED
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			// replaced new Google.GoogleMapProvider() with new Microsoft.HybridProvider() when status code 403
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.HybridProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    // makes map interactive
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    // PointFeatures have a getLocation method
	    // use parseEarthquake() method from ParseFeed class 
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //**for each PointFeature eq in earthquakes List** 
	    	// calls CreateMaker() helper method passing in each eq in earthquakes List 
	        // creates a new SimplePointMarker for each PointFeature in earthquakes List
	        // adds each new SimplePointMarker to the List markers 
	    for (PointFeature eq : earthquakes) {
	    	// call createMarker
	        markers.add(createMarker(eq));
	    }
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* helper method createMarker() that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake */
	
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// Prints all of the features in a PointFeature in console
		System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// style markers (color and radius size) according to magnitude size 
		int blue = color(66, 92, 244);
	    int yellow = color(244, 235, 66);
	    int red = color(244, 66, 92);

	    int small = 4;
	    int medium = 8;
	    int large = 12;
	    
	    // earthquakes less than magnitude 4
	   	if ((float) mag < THRESHOLD_LIGHT) {
	   		marker.setColor(blue);
	   		marker.setRadius(small);
	   	} else if ((float) mag >= THRESHOLD_LIGHT && (float) mag < THRESHOLD_MODERATE ) {
	   		// earthquakes between 4.0-4.9
    		marker.setColor(yellow);
    		marker.setRadius(medium);
	    } else if ((float) mag >= THRESHOLD_MODERATE) {
	   		// earthquakes greater than or equal to 5.0
	   		marker.setColor(red);
	   		marker.setRadius(large);
	   	} 

	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
	
	}
}
