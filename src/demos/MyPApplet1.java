/* 
 * Week 3
 * author: Katherine Oh
 * */
package demos;

import processing.core.*;  // find PApplet from processing library

public class MyPApplet1 extends PApplet {
	
	private String URL = "https://atmedia.imgix.net/07ad9ca88955bfb366244d0ab32f3db4e02dcdfc?w=800&fit=max";
	private PImage backgroundImg;
	
	// executed one - configure canvas
	public void setup() {
		size(200, 200); // canvas size
		backgroundImg = loadImage(URL, "jpg"); // image loaded into memory in a PImage using loadImage() method
	}
	
	// loops often - display content
	public void draw() {
		
		// resize image using resize() method 
		// make image proportional - take width of image and Java will calculate the height in proportion
		backgroundImg.resize(width, 0);
		
		// display image using image() method
		// display using image() method and the coordinates of top left corner of image
		image(backgroundImg, 0, 0);
		
		// calculate color for face - custom color
		int[] color = sunColorSec(second()); // takes in built-in method second() from System clock
		// fill large ellipse with custom color
		fill(color[0], color[1], color[2]); 
		// draw ellipse shape using ellipse() method dynamically
		// use relative coordinates
		// (x of center of shape, y of center of shape, width of shape, height of shape)
		ellipse(width/4, height/5, width/5, height/5); 
		
		fill(0,0,0); //fill with black
		ellipse(width/5, height/6, width/30, height/30);

		fill(0,0,0); //fill with black
		ellipse(width/4, height/6, width/30, height/30);
		
		noFill();
		arc(width/4, height/5, width/10, height/10, 0, PI);
		
	}
	
	public int[] sunColorSec(float seconds) {
		// create integer array that holds 3 integers
		int[] rgb = new int[3];
		
		// Scale brightness of yellow based on the seconds
		// 30 seconds is black. 0 seconds is bright yellow
		float diffFrom30 = Math.abs(30-seconds); // calculate absolute value of that number
		
		// create ratio to calculate how far away we are from the minute
		float ratio = diffFrom30/30; // will be a number between 0 and 1
		
		// use ratio to scale r, g, b values
		rgb[0] = (int)(255*ratio);  // casting them into integers since array can only hold ints
		rgb[1] = (int)(255*ratio);
		rgb[2] = 0;
		
		return rgb;
	}
	
	public static void main (String[] args) {
		
	}

}
