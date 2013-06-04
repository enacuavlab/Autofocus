package common;

import imu.IMU;

import javax.swing.JPanel;

import testData.Sender;

import data.Data;

import ellipsoide.Sphere;
import filtre.FilterSphere;
import filtre.GUIHelper;
import fr.dgac.ivy.IvyException;

public class StartUp {

	public StartUp(TypeCalibration t,JPanel panel_dessin) throws IvyException,InterruptedException {
		if (t==TypeCalibration.MAGNETOMETER){
			Sphere sp = new Sphere(10,10);
			//JPanel sphaff = sp.getAffichage();
			//panel_dessin.add(sphaff);
			
			FilterSphere filtre = new FilterSphere(sp,10,t);
			Data data = new Data(t,filtre);
			GUIHelper.showOnFrame(sp.getAffichage(), "test");
			Sender s = new Sender("/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
			IMU imu =new IMU(t,17,data);
			s.start();
			s.join();
			s.arret();
			imu.arret();
			
			
		}
	}
	public StartUp(TypeCalibration t, JPanel panel_dessin, JPanel panel_inst){
		
	}
}
