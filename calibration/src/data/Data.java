/**
 * the data package used to store the data messages gotten from the IMU client
 */
package data;

import java.util.*;

import common.TypeCalibration;
import filtre.Filter;

/**
 * class that permit to store the data sent by the IMU
 * @author florent
 *
 */
public class Data {
	/**
	 * the current type of calibration
	 */
	private TypeCalibration calib;
	/**
	 * the filter to filter the noise
	 */
	private Filter filtre;
	/**
	 * the list of vector stored
	 */
	private List<Vecteur> list = new LinkedList<Vecteur>();
	/**
	 * Constructor for data
	 * @param calibration current typecalibration
	 * @param filtre current filtre
	 */
	public Data(TypeCalibration calibration,Filter filtre) {
		this.calib = calibration;
		this.filtre = filtre;
	}
/**
 * method used to store vectors
 * @param x_raw
 * @param y_raw
 * @param z_raw
 */
	public void store(double x_raw, double y_raw, double z_raw) {
		Vecteur vec = new Vecteur(x_raw, y_raw, z_raw);
		//System.out.println(vec);
		vec.setType(calib);
		list.add(vec);
		filtre.add(vec);
	
			}
	public List<Vecteur> getVecteur(){
		return list;
	}
	public String toString() {
		return list.toString();
	}
}
