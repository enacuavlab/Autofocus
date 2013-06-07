/** 
 * the data package used to store the data messages gotten from the IMU client
 */
package data; 

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import common.TypeCalibration;

import filtre.VecteurFiltrable;

/**
 * Class to store the data given by IMU
 * 
 * @author Alinoé
 * 
 */
public class Vecteur extends Vector3D implements VecteurFiltrable<Double> {

	private static final long serialVersionUID = 1L;

	/**
	 * State of the vector after the filter
	 */
	private boolean state = false;

	/**
	 * Type of the calibration
	 */
	private TypeCalibration type;

	/**
	 * Give the fields the values given by the IMU
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vecteur(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Give the state of the correctness of the vector
	 * 
	 * @return state
	 */
	@Deprecated
	public boolean getState() {
		return state;
	}

	/**
	 * Set the type of the calibration
	 * 
	 * @param t
	 */
	public void setType(TypeCalibration t) {
		this.type = t;
	}

	/**
	 * Give the type of the calibration
	 * 
	 * @return type
	 */
	public TypeCalibration getType() {
		return type;
	}

	/**
	 * Give the state of the correctness of the vector
	 * @return state
	 */
	public boolean isCorrect() {
		return state;
	}
	
	/**
	 * Set the state of the correctness of the vector false
	 */
	public void setFalse() {
		this.state = false;

	}

	/**
	 * Set the state of the correctness of the vector false
	 */
	public void setTrue() {
		this.state = true;
	}
	
	/**
	 * Convert to string
	 * @return String
	 */
	public String toString() {

		return super.toString() + "sate" + this.state
				+ System.getProperty("line.separator");// "" + "state"+ " type"
														// +"");

	}
	/**
	 * Convert vector to array 
	 * @return the array containing the values of the vector
	 */
	public Collection<Double> setArray() {
		ArrayList<Double> a = new ArrayList<Double>();
		double[] b = super.toArray();
		for (int i = 0; i < 3; i++) {
			a.add(b[i]);
		}
		return a;
	}
	/**
	 * Test of equality between two vectors
	 * @param test
	 * @return
	 */
	public boolean equals(VecteurFiltrable<Double> test) {
		return super.equals(test);
	}

}
