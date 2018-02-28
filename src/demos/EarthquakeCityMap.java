/* 
 * Week 3
 * author: Katherine Oh
 * code through tutorial
 * */

package demos;

// Java utilities libraries
import java.util.ArrayList;
import java.util.List;
// Processing libraries
import processing.core.*;
// Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class EarthquakeCityMap extends PApplet {
	// map object
	private UnfoldingMap map;
	
	public void setup() {
		size(950, 600, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
		// change to 1 from 2: all earthquakes visible when Applet opens
		map.zoomToLevel(1);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// create Location object with coordinates of Valdivia, Chile
		//Location valLoc = new Location(-38.14f, -73.03f);
		
		// create Feature object that points to PointFeature object with the 
		// single location of Valdivia 
		//Feature valdiviaEq = new PointFeature(valLoc);
		
		//**instead of above two objects line 32 & 36, combine into one statement
		PointFeature valdiviaEq = new PointFeature(new Location(-38.14f,-73.03f));
 		// add 4 properties to valdiviaEq PointFeature object
		valdiviaEq.addProperty("title", "Valdivia, Chile");
		valdiviaEq.addProperty("magnitude", "9.5");
		valdiviaEq.addProperty("date", "May 22, 1960");
		valdiviaEq.addProperty("year", 1960);
		
		// variable alaskaEq
		PointFeature alaskaEq = new PointFeature(new Location(61.02f,-147.65f));
		alaskaEq.addProperty("title", "1964 Great Alaska Earthquake");
		alaskaEq.addProperty("magnitude", "9.2");
		alaskaEq.addProperty("date", "March 11, 2011");
		alaskaEq.addProperty("year", 2011);
		
		// variable sumatraEq
		PointFeature sumatraEq = new PointFeature(new Location(3.30f,95.78f));
	    sumatraEq.addProperty("title", "Off the West Coast of Northern Sumatra");
	    sumatraEq.addProperty("magnitude", "9.1");
	    sumatraEq.addProperty("date", "February 26, 2004");
	    sumatraEq.addProperty("year", 2004);
	    
		// variable japanEq
		PointFeature japanEq = new PointFeature(new Location(38.322f,142.369f));
		japanEq.addProperty("title", "Near the East Coast of Honshu, Japan");
		japanEq.addProperty("magnitude", "9.0");
		japanEq.addProperty("date", "March 28, 1964");
		japanEq.addProperty("year", 1964);
		
		// variable kamchatkaEq
		PointFeature kamchatkaEq = new PointFeature(new Location(52.76f,160.06f));
	    kamchatkaEq.addProperty("title", "Kamchatka");
	    kamchatkaEq.addProperty("magnitude", "9.0");
	    kamchatkaEq.addProperty("date", "November 4, 1952");
	    kamchatkaEq.addProperty("year", 1952);
		
		// ***after adding line 39, comment out 50 & 52
		// create Marker interface data type that points to SimplePointMarker object
		// we feed into with Location valLoc object and 
		// all of the properties that we added with getProperties() method
		//Marker valMk = new SimplePointMarker(valLoc, valdiviaEq.getProperties());
		// addMarker() method to add Marker to map
		//map.addMarker(valMk);
		
		// create bigEarthquakes variable with type List that has PointFeature elements, 
		// points to a new object of ArrayList that has PointFeature elements
		List<PointFeature> bigEarthquakes = new ArrayList<PointFeature>();
		
		// add elements to the list
		bigEarthquakes.add(valdiviaEq);
		bigEarthquakes.add(alaskaEq);
	    bigEarthquakes.add(sumatraEq);
	    bigEarthquakes.add(japanEq);
	    bigEarthquakes.add(kamchatkaEq);
		
		// create markers variable with type List that has Marker elements,
		// points to new object ArrayList that has Marker elements
		List<Marker> markers = new ArrayList<Marker>();
		// for each PointFeature in this list of PointFeature loops
		// is the markers list, which will be populated with a single marker 
		// for each earthquake
		// **for each PointFeature eq in bigEarthquakes**
		for (PointFeature eq: bigEarthquakes) {
			markers.add(new SimplePointMarker(eq.getLocation(),
					    					  eq.getProperties()));
		}
		map.addMarkers(markers);
		
		// style markers according to year (before or after 2000)
	    int limeGreen = color(0, 255, 0);
	    int oliveGrey = color(153, 153, 102);
	    // **for each Marker mk in markers**
	    for (Marker mk :markers) {
	    	// earthquakes after 2000 are lime green
	    	if ( (int) mk.getProperty("year") > 2000 ) {
	    		mk.setColor(limeGreen);
	    	}
	    	// earthquakes equal to or before 2000 are olive grey
	    	else {
	    		mk.setColor(oliveGrey);
	    	}
	    }
		
	}
	
	public void draw() {
		background(0, 51, 102); // background set to dark blue color
		map.draw();
		// addKey();
	}
}
