package data;

import java.util.*;
import common.TypeCalibration;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import filtre.VecteurFiltrable;

public class Vecteur extends Vector3D implements VecteurFiltrable<Double> {

	
	private static final long serialVersionUID = 1L;

	// state: etat du vecteur apres filtrage
	private boolean state = false;

	// type de calibration(ACCELEROMETER, MAGNETOMETER)

	private TypeCalibration type;

	public Vecteur(double x, double y, double z) {
		super(x, y, z);
	}

	public boolean getState() {
		return state;
	}

	public void setType(TypeCalibration t) {
		this.type = t;
	}

	public TypeCalibration getType() {
		return type;
	}

	public boolean isCorrect() {
		return true;

	}

	public void setFalse() {
		this.state = false;

	}

	public void setTrue() {
		this.state = true;
	}

	public String toString() {

		return super.toString() + "sate" + this.state
				+ System.getProperty("line.separator");// "" + "state"+ " type"
														// +"");

	}

	public Collection<Double> setArray() {
		ArrayList<Double> a = new ArrayList<Double>();
		double[] b=super.toArray();
		for (int i=0;i<3;i++){
			a.add(b[i]);
		}
		return a;	
	}


	public boolean equals(VecteurFiltrable<Double> test) {
		return super.equals(test);
	}

}
