/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

/**
 * Store the number of points over the surface of a zone
 * 
 * @author Alino�
 * 
 */
public class Density {
	/**
	 * The total number of points over the surface of the sphere
	 */
	private int nbMax = 1000;
	/**
	 * in [0;255]
	 */
	private int colorParameter;
	/**
	 * Number of points over the zone
	 */
	private double nb;
	/**
	 * 255
	 */
	private final int nbMaxColor = 255;

	/**
	 * Initialise nb and colorParameter
	 * @param nbMaxPoints number of points on the surface of the sphere to get the right calibration
	 */
	public Density(int nbMaxPoints) {
		nb = 0;
		colorParameter = 0;
	}

	/**
	 * Calcul of the density thanks to the surface of the sphere and the one of
	 * zone
	 * 
	 * @param surfaceSphere
	 * @param surfaceZone
	 */
	public void updateDensity(double surfaceSphere, double surfaceZone) {
		nb += 1;
		//System.out.print("[" + surfaceSphere + surfaceZone + "]");
		double temp = (surfaceSphere * nb * nbMaxColor) / (surfaceZone * nbMax);
		if (temp > nbMaxColor) {
			colorParameter = nbMaxColor;
		} else {
			colorParameter = (int) temp;
		}
		//System.out.println(colorParameter);
	}

	/**
	 * reset the counter of the density
	 */
	public void reset() {
		nb = 0;
		colorParameter =0;
	}

	/**
	 * return the color of the zone
	 * 
	 * @return return the matching color of the density
	 */
	public int getColor() {
		return colorParameter;
	}
}
