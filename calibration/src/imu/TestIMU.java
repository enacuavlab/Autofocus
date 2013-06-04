package imu;

import testData.Sender;
import common.TypeCalibration;
import data.Data;
import ellipsoide.Sphere;
import filtre.FilterSphere;
import filtre.GUIHelper;
import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		TypeCalibration t = TypeCalibration.MAGNETOMETER;
		Sphere sp = new Sphere(20,10);
		FilterSphere filtre = new FilterSphere(sp,10,TypeCalibration.MAGNETOMETER);
		Data data = new Data(t,filtre);
		GUIHelper.showOnFrame(sp.getAffichage(), "test");
		Sender s = new Sender("/home/paparazzi/var/logs/13_05_29__10_15_23.data");
		//Sender s = new Sender("C:\\Users\\Alino�\\Desktop\\13_05_29__10_15_23.data");
		IMU imu =new IMU(t,17,data);
		s.start();
		s.join();
		imu.arret();
		//System.out.println(data.toString());	
	}
}
