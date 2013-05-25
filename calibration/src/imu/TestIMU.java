package imu;

import common.TypeCalibration;
import data.Data;
import data.Vecteur;

import filtre.Filter;
import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		
		TypeCalibration t = TypeCalibration.ACCELEROMETER;
		Filter<Vecteur> filtre = new Filter<Vecteur>();
		Data data = new Data(t,filtre);
		IMU imu =new IMU(t,data);
		Thread.sleep(150000);
		imu.stop();
		System.out.println(data.toString()); 
		
	}
}
