package imu;

import calibTest.Optimize;
import testData.Sender;

import common.TypeCalibration;
import data.Vecteur;
import filtre.VecteurFiltrable;

import data.Data;
import ellipsoide.Sphere;
import filtre.FilterSphere;
import filtre.GUIHelper;
import fr.dgac.ivy.IvyException;

public class TestIMU {
	
	public static void main(String args[]) throws IvyException, InterruptedException {
		TypeCalibration t = TypeCalibration.ACCELEROMETER;
		System.out.println("type");
		Sphere sp = new Sphere(20,10);
		FilterSphere filtre = new FilterSphere(sp,40,t);
		System.out.println("filtre");
		Data data = new Data(t,filtre);
		System.out.println("data");
		GUIHelper.showOnFrame(sp.getAffichage(), "test");
		Sender s = new Sender("/home/paparazzi/var/logs/13_04_03__13_49_35.data");
		System.out.println("sender");
		IMU imu =new IMU(t,data);
		s.start();
		s.join();
		s.arret();
		imu.arret();
		//System.out.println(data.toString());	
		System.out.println("fin");
	}
}
