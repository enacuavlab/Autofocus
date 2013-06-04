package common;

import javax.swing.JPanel;

import ellipsoide.Sphere;
import filtre.FilterSphere;
import filtre.GUIHelper;
import fr.dgac.ivy.IvyException;
import imu.IMU;
import testData.Sender;
import data.Data;

public class StartUp {
	private Sphere sp;
	private FilterSphere filtre;
	private TypeCalibration t;

	public StartUp(TypeCalibration t, JPanel panel_dessin) throws IvyException, InterruptedException {
		this.t = t;
		if (t == TypeCalibration.MAGNETOMETER) {
			System.out.println("type");
			sp = new Sphere(20, 10);
			filtre = new FilterSphere(sp,10,TypeCalibration.MAGNETOMETER);
			System.out.println("filtre");
			System.out.println("data");
			GUIHelper.showOnFrame(sp.getAffichage(), "test");
			panel_dessin.add(sp.getAffichage());

			Data data = new Data(t, filtre);
			
			Sender s = new Sender(
					"/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
			System.out.println("sender");
			IMU imu = new IMU(t, 17, data);
			s.start();
			s.join();
			s.arret();
			imu.arret();
			System.out.println("fin");
			System.out.println(data.toString());
		}
	}

	public static void main(String args[]) throws IvyException,
			InterruptedException {
		TypeCalibration t = TypeCalibration.MAGNETOMETER;

		System.out.println("type");
		Sphere sp = new Sphere(20, 10);
		FilterSphere filtre = new FilterSphere(sp, 10,
				TypeCalibration.MAGNETOMETER);
		System.out.println("filtre");
		Data data = new Data(t, filtre);
		System.out.println("data");
		GUIHelper.showOnFrame(sp.getAffichage(), "test");
		// Sender s = new
		// Sender("/home/paparazzi/var/logs/13_05_29__10_15_23.data");
		Sender s = new Sender(
				"/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
		System.out.println("sender");
		IMU imu = new IMU(t, 17, data);
		s.start();
		s.join();
		s.arret();
		imu.arret();
		// System.out.println(data.toString());

	}
}
