/**
 * the data package used to store the data messages gotten from the IMU client
 */
package data;

import java.util.logging.Logger;

import filtre.Filter;

/**
 * class that permit to store the data sent by the IMU
 * 
 * @author florent
 * 
 */
public class Data {

	private static Logger logger = Logger.getLogger(Data.class.getName());

	/**
	 * the filter to filter the noise
	 */
	private Filter filtre;

	/**
	 * Constructor for data
	 * 
	 * @param filt
	 *            current filtre
	 * @param calibration
	 *            current typecalibration
	 */
	public Data(Filter filt) {
		this.filtre = filt;
	}

	/**
	 * method used to store vectors
	 * 
	 * @param xRaw
	 * @param yRaw
	 * @param zRaw
	 */
	public void store(double xRaw, double yRaw, double zRaw) {
		Vector vec = new Vector(xRaw, yRaw, zRaw);
		filtre.add(vec);
	}

}
