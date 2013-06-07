/**
 * the data package used to store the data messages gotten from the IMU client
 */
package data;

import java.util.LinkedList;
import java.util.List;

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
	 * @param filt current filtre
	 */
	public Data(TypeCalibration calibration, Filter filt) {
		this.calib = calibration;
		this.filtre = filt;
	}
/**
 * method used to store vectors
 * @param xRaw
 * @param yRaw
 * @param zRaw
 */
	public void store(double xRaw, double yRaw, double zRaw) {
		Vecteur vec = new Vecteur(xRaw, yRaw, zRaw);
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
