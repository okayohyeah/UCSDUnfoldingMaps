/* 
 * Week 3
 * author: Katherine Oh
 * */

package demos;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.*;

public class EarthquakeCityMap extends PApplet {
	// map object
	private UnfoldingMap map;
	
	public void setup() {
		size(950, 600, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// create Location object with coordinates of Valdivia, Chile
		Location valLoc = new Location(-38.14f, -73.03f);
		
		// create Feature object that points to PointFeature object with the 
		// single location of Valdivia 
		Feature valdiviaEq = new PointFeature(valLoc);
		
 		// add 4 properties to Valdivia PointFeature object
		valdiviaEq.addProperty("title", "Valdivia, Chile");
		valdiviaEq.addProperty("magnitude", "9.5");
		valdiviaEq.addProperty("date", "May 22, 1960");
		valdiviaEq.addProperty("year", "1960");
		
		// create Marker interface data type that points to SimplePointMarker object
		// we feed into with Location valLoc object and 
		// all of the properties that we added with getProperties() method
		Marker valMk = new SimplePointMarker(valLoc, valdiviaEq.getProperties());
		// addMarker() method to add Marker to map
		map.addMarker(valMk);
		
		// create variable with type List that has PointFeature elements, 
		// points to a new object of ArrayList object that has PointFeature elements
		List<PointFeature> bigEarthquakes = new ArrayList<PointFeature>();
		
		// add elements to the list
		//bigEarthquakes.add(valdiviaEq);
	}
	
	public void draw() {
		background(0, 51, 102); // background set to dark blue color
		map.draw();
		// addKey();
	}
}
