
public class ArrayLocation {
	private double coords[];   // arrays aren't primitives but objects
	
	// constructor
	public ArrayLocation(double[] coords) {
		this.coords = coords;
	}
	
	// main method
	public static void main (String[] args) {
		double[] coords = {5.0, 0.0};
		ArrayLocation accra = new ArrayLocation(coords);
		coords[0] = 32.9;
		coords[1] = -117.2;
		System.out.println(accra.coords[0]);
	}
}
