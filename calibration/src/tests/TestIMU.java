package tests;

import imu.IMU;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import obsolete.Optimize;

import calibrate.PrintLog;
import testData.Sender;

import common.TypeCalibration;
import data.Vecteur;
import filtre.VecteurFiltrable;

import data.Data;
import ellipsoide.AffichAccel;
import ellipsoide.Sphere;
import filtre.FilterAccel;
import filtre.FilterSphere;
import fr.dgac.ivy.IvyException;

public class TestIMU {
	
	public static void main(String args[]) throws IvyException, InterruptedException {
		TypeCalibration t = TypeCalibration.ACCELEROMETER;
		final Sphere sp = new Sphere(20,10);
		final AffichAccel affAccel = new AffichAccel(sp);
		FilterAccel filtre = new FilterAccel(40, t, 200, 15, affAccel);
		//System.out.println("filtre");
		Data data = new Data(t, filtre);
		PrintLog prlog = new PrintLog();
		//System.out.println("data");
		JLabel label = new JLabel();
		IMU imu = new IMU(label);
		final JPanel panelDessin = new JPanel();
		final JPanel panelInst = new JPanel();
		final JPanel panelBar = new JPanel();
		GUIHelper.showOnFrame(panelDessin, "test");
		GUIHelper.showOnFrame(panelInst, "test2");
		GUIHelper.showOnFrame(panelBar, "test3");
		imu.setId(3);
		imu.ListenIMU(data, t, prlog);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panelDessin.add(affAccel.getSphere().getAffichage());
				//System.out.println("apres add");
				panelDessin.validate();
				panelInst.add(affAccel.getLabel());
				panelInst.validate();
				panelBar.add(affAccel.getProgressBar());
				panelBar.validate();
			}
		});

		try {
			Sender s = new Sender(
					"/home/paparazzi/var/logs/13_04_03__13_49_35.data");
			//System.out.println("sender");
			s.start();
			s.join();
			s.arret();
		} catch (IvyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//System.out.println("fin");
	}
}
