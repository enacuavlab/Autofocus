package common;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.prefs.PreferenceChangeEvent;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import calibTest.PrintLog;

import ellipsoide.AffichAccel;
import ellipsoide.Sphere;
import filtre.FilterAccel;
import filtre.FilterSphere;
import filtre.GUIHelper;
import fr.dgac.ivy.IvyException;
import imu.IMU;
import testData.Sender;
import data.Data;

public class StartUp {

	public StartUp(TypeCalibration t, final JPanel panelDessin, int id, IMU imu) {
		if (t == TypeCalibration.MAGNETOMETER) {
			System.out.println("type");
			final Sphere sp = new Sphere(5, 5);
			FilterSphere filtre = new FilterSphere(sp, 10, t);
			System.out.println("filtre");
			Data data = new Data(t, filtre);
			System.out.println("data");
			PrintLog prlog = new PrintLog();
			// GUIHelper.showOnFrame(sp.getAffichage(), "test");
			// (sp.getAffichage()).setBounds(125, 0, 775, 425);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					panelDessin.add(sp.getAffichage());
					panelDessin.validate();
				}
			});
			Sender s;
			try {
				s = new Sender(
						"/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
				System.out.println("sender");
				imu.setId(17);
				imu.ListenIMU(data, t, prlog);
				s.start();
				s.join();
				s.arret();
			} catch (IvyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			System.out.println("fin");
			System.out.println(data.toString());

		}
	}

	public StartUp(TypeCalibration t, final JPanel panel, int id, IMU imu,
			int test) {
		System.out.println("type");
		final Sphere sp = new Sphere(5, 5);
		final AffichAccel affAccel = new AffichAccel(sp);
		FilterAccel filtre = new FilterAccel(40,t,300,20,affAccel);
		System.out.println("filtre");
		Data data = new Data(t, filtre);
		System.out.println("data");
		PrintLog prlog = new PrintLog();
		// GUIHelper.showOnFrame(sp.getAffichage(), "test");
		// (sp.getAffichage()).setBounds(125, 0, 775, 425);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.add(affAccel,BorderLayout.CENTER);
				panel.validate();
			}
		});
		Sender s;
		try {
			s = new Sender(
					"/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
			System.out.println("sender");
			imu.setId(17);
			imu.ListenIMU(data, t, prlog);
			s.start();
			s.join();
			s.arret();
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("fin");
		System.out.println(data.toString());

	}

	public static void main(String args[]) throws IvyException,
			InterruptedException {

	}
}
