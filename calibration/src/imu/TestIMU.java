package imu;

import testData.Sender;

import common.TypeCalibration;

import data.Data;

import ellipsoide.Sphere;
import filtre.Filter;
import filtre.FilterSphere;
import filtre.GUIHelper;

import filtre.Filter;

import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		TypeCalibration t = TypeCalibration.MAGNETOMETER;

		System.out.println("type");
		//Sphere sp = new Sphere(20,10);
		Filter filtre = new Filter(10,TypeCalibration.MAGNETOMETER);
		System.out.println("filtre");

		Data data = new Data(t,filtre);

		System.out.println("data");
		//GUIHelper.showOnFrame(sp.getAffichage(), "test");
		//Sender s = new Sender("/home/paparazzi/var/logs/13_05_29__10_15_23.data");
		Sender s = new Sender("home/christophe/paparazzi/var/log/13_05_29__10_15_23copy.txt");
		System.out.println("sender");

		IMU imu =new IMU(t,17,data);
		s.start();
		s.join();
		imu.arret();

	
		
		//System.out.println(data.toString());	

		System.out.println("fin");
		System.out.println(data.toString());	

	}
}
