package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PFont;
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

	// keeps eclipse from generating a warning.
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
	
	//feed with magnitude 2.5+ Earthquakes - RSS FEED: past 30 days
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_month.atom";
	
	public void setup() {
	
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_month.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
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
		
	/* HELPER METHOD createMarker() that takes in an earthquake 
	 * Feature and returns a SimplePointMarker for that earthquake */
	private SimplePointMarker createMarker(PointFeature feature) {  
		// all of the Features in a PointFeature
		// System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// style markers by color according to magnitude size 
		int blue = color(66, 92, 244);
		int yellow = color(244, 235, 66);
		int red = color(244, 66, 92);
		// style markers by radius size according to magnitude size 
		int small = 6;
		int medium = 12;
		int large = 18;
	    
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

	    // Return the marker
	    return marker;
	}
	
	public void draw() {
	    background(92,134,141); // teal
	    map.draw();
	    addKey();
	    addTitle();
	}

	// HELPER METHOD addKey() to draw key in GUI
	private void addKey() {	
		// Rectangle
		fill(255, 255, 255); // rectangle white
		stroke(200, 214, 202); // lt grey teal
		strokeWeight(3);
		rect(25, 50, 150, 250, 5);
		
		// Heading
		fill(0, 0, 0); // text black
		textSize(16);
		textAlign(CENTER);
		text("Earthquake Key", 100, 100); // align x where center of centered text would be
		stroke(0, 0, 0);
		strokeWeight(1);
		line(40, 105, 160, 105);
		
		// Other text
		fill(0, 0, 0); // text black
		textSize(12);
		textAlign(LEFT);
		text("5.0+ Magnitude", 70, 150);
		text("4.0+ Magnitude", 70, 200);
		text("Below 4.0", 70, 250);
		
		// Symbols
		fill(244, 66, 92); // red 
		stroke(100, 100, 100); // outline color med grey
		strokeWeight(1);
		ellipse(50, 145 , 18, 18);
		fill(244, 235, 66); // yellow
		ellipse(50, 195 , 12, 12);
		fill(66, 92, 244); // blue
		ellipse(50, 245 , 6, 6);		
	}
	
	// HELPER METHOD addTitle() to add Map Title in GUI
	private void addTitle() {	
		fill(255, 255, 255); // text white
		textSize(24);
		textAlign(CENTER);
		text("Earthquakes of Magnitude 2.5+ in Past 30 Days", 475, 35); // align x where center of centered text would be
	}
}
