/*
 * Week 3
 * author: Katherine Oh
 * date: February 28, 2018
 * note: code through support tutorial
 *  */

package demos;

// import Java utilities library
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// import Unfolding library
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
// import Processing library - PApplet
import processing.core.PApplet;

public class LifeExpectancyMap extends PApplet {
	
	// create map object
	UnfoldingMap map;
	
	// declare variables
	// Map data type structure that takes in String type for keys and Float type for values
	Map<String, Float> lifeExpectancyByCountry;
	// List data type structure of Feature elements
	List<Feature> countries;
	// List data type structure of Maker elements
	List<Marker> countryMarkers;
	
	public void setup() {
		// setup size of canvas
		size(800, 600, OPENGL);
		// create new map object
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		// using built-in method from Unfolding library - allows user interactivity
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// create object of type Map - load data from data file
		lifeExpectancyByCountry = loadLifeExpectancyFromCSV ("LifeExpectancyWorldBankModule3.csv");
		
		// create 1 Feature + 1 Marker per country - from list of countries
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		// add Markers to map
		map.addMarkers(countryMarkers);
		// shade countries on map - colors depends life expectancy
		shadeCountries();
		
	}
	
	// create private Helper Method that loads in data from data file
	private Map<String, Float> loadLifeExpectancyFromCSV(String filename) {
		// constructor - construct Map as a HashMap - declare lifeExpectancyMap
		Map<String, Float> lifeExpectancyMap = new HashMap<String, Float>();
		
		// populate lifeExpectancyMap from fields in file
		// take in one row at a time into an array of strings
		String[] rows = loadStrings(filename);
		
		// iterate through all of strings we read 
		// extract country and life expectancy value data
		//**for String each row in (array of Strings) rows**
		for (String row : rows) {
			// parse away relevant information (country and life expectancy value)
			// split it at commas to separate fields from row of strings into an array of Strings rows
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				// cast string into float type for life expectancy
				float value = Float.parseFloat(columns[5]);
				lifeExpectancyMap.put(columns[4], value);
			}
		}
		return lifeExpectancyMap;
	} 
	
	// create private Helper Method that
	private void shadeCountries() {
		// go through all markers that we created for each country
		for (Marker marker: countryMarkers) {
			String countryId = marker.getId();
			
			if (lifeExpectancyByCountry.containsKey(countryId)) {
				// color depends on life expectancy
				float lifeExp = lifeExpectancyByCountry.get(countryId);
				// take life expectancy number (float) and cast it into a integer for rgb color
				// map() method allows us to take in a number (lifeExp) that is in a range (40-90) 
				// and translate it into a new range (10-255) for our rgb number
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				// takes translated rgb number and sets color accordingly in a range
				 	// countries with low life expectancy will have bright red
					// countries with high life expectancy will have blue
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			} else {
				// when country id does not exist on file of list of life expectancy 
				// will shade those grey
				marker.setColor(color(150,150,150));
			}
		}
	}

	public void draw() {
		map.draw();
	}
}
