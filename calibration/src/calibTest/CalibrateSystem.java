package calibTest;

import java.io.IOException;
import java.io.StringWriter;

import ellipsoide.Sphere;
import filtre.FilterSphere;
import filtre.GUIHelper;
import fr.dgac.ivy.IvyException;
import imu.IMU;
import testData.Sender;

import common.TypeCalibration;

import data.Data;

public class CalibrateSystem {

	public void calibrates(String logName) throws IOException {
		
		String[] tab_commandes = new String[2];
		tab_commandes[0] = "/home/paparazzi/sw/tools/calibration/calibrate.py " + logName;
		Process processus = Runtime.getRuntime().exec(tab_commandes);
		System.out.println("Processus: " + processus);
		System.out.println("processus.getInputStream(): "
				+ processus.getInputStream());
		StringWriter writer = new StringWriter();
		copy(processus.getInputStream(), writer, );
		String theString = writer.toString();
		System.out.println("Fin de l'appel de commandes UNIX en java");

	}

	public static void main(String args[]) throws IvyException,
			InterruptedException {
		TypeCalibration t = TypeCalibration.ACCELEROMETER;
		System.out.println("type");
		Sphere sp = new Sphere(20, 10);
		FilterSphere filtre = new FilterSphere(sp, 40, t);
		System.out.println("filtre");
		Data data = new Data(t, filtre);
		System.out.println("data");
		GUIHelper.showOnFrame(sp.getAffichage(), "test");
		Sender s = new Sender(
				"/home/paparazzi/var/logs/13_04_03__13_49_35.data");
		System.out.println("sender");
		IMU imu = new IMU(t, 3, data);
		s.start();
		s.join();
		s.arret();
		imu.arret();
		// System.out.println(data.toString());
		System.out.println("fin");
	}

}
