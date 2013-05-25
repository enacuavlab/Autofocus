package data;

import java.util.*;

import common.TypeCalibration;

public class Data {

	private TypeCalibration calib;
	List<Vecteur> list = new LinkedList<Vecteur>();
	
	public Data(TypeCalibration calibration) {
		this.calib = calibration;
	}

	public void store(double x_raw, double y_raw, double z_raw) {

		Vecteur vec = new Vecteur(x_raw, y_raw, z_raw);
		vec.setType(calib);
		list.add(vec);
	}

	public String toString() {
		return list.toString();
	}

}
