package common;

import java.util.prefs.PreferenceChangeEvent;

import javax.swing.JPanel;

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

	public StartUp(TypeCalibration t, JPanel panelDessin,int id,IMU imu){
		if (t == TypeCalibration.MAGNETOMETER) {
			System.out.println("type");
			Sphere sp = new Sphere(5, 5);
			FilterSphere filtre = new FilterSphere(sp,10,t);
			System.out.println("filtre");
			Data data = new Data(t, filtre);
			System.out.println("data");
			PrintLog prlog = new PrintLog();
			//GUIHelper.showOnFrame(sp.getAffichage(), "test");
			
			//(sp.getAffichage()).setBounds(125, 0, 775, 425);
			panelDessin.add(sp.getAffichage());
			panelDessin.validate();
			//Sender s = new Sender(
				//	"/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
			try {
				Sender s = new Sender("/home/gui/paparazzi/var/logs/13_05_29__10_15_23.data");
				System.out.println("sender");
				imu.setId(17);
				imu.ListenIMU(data, t,prlog);
				s.start();
				s.join();
				s.arret();
				System.out.println("fin");
				System.out.println(data.toString());
			} catch (IvyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public StartUp(TypeCalibration t , JPanel panelDessin ,int id , JPanel panelInst, JPanel panelBar, IMU imu){
		if (t == TypeCalibration.ACCELEROMETER) {
			System.out.println("Accelero");
			AffichAccel affAccel = new AffichAccel();
			FilterAccel filtre = new FilterAccel(40,t,200,15,affAccel);
			System.out.println("filtre");
			Data data = new Data(t, filtre);
			PrintLog prlog = new PrintLog();
			System.out.println("data");
			panelDessin.add(affAccel.getSphere().getAffichage());
			panelDessin.validate();
			panelInst.add(affAccel.getLabel());
			panelInst.validate();
			panelBar.add(affAccel.getProgressBar());
			panelBar.validate();
			
			imu.ListenIMU(data, t,prlog);
			try {
				Thread.sleep(150000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imu.stopListenImu(t);
			System.out.println("fin");
			
		}
	}

	public static void main(String args[]) throws IvyException, InterruptedException {
		
	}
}
