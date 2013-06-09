/**
 * Package used or using all the classes of the application
 */
package common;

import imu.IMU;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import testData.Sender;
import calibrate.PrintLog;
import data.Data;
import ellipsoide.AffichAccel;
import ellipsoide.Sphere;
import filtre.FilterAccel;
import filtre.FilterSphere;
import fr.dgac.ivy.IvyException;
/**
 * To launch the view
 * @author Guillaume
 *
 */
public class StartUp {
	/**
	 * StartUp for Magnetometers
	 * @param t
	 * @param panelDessin
	 * @param id
	 * @param imu
	 */
	public StartUp(TypeCalibration t ,final JPanel panelDessin, int id, IMU imu) {
			final Sphere sp = new Sphere(5, 5);
			FilterSphere filtre = new FilterSphere(sp, 10, t);
			Data data = new Data(t, filtre);
			PrintLog prlog = new PrintLog();
			// GUIHelper.showOnFrame(sp.getAffichage(), "test");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					panelDessin.add(sp.getAffichage());
					panelDessin.validate();
				}
			});
			//imu.ListenIMU(data, t, prlog);
			Sender s;
			try {
				s = new Sender(System.getenv("HOME") +
						"/paparazzi/var/logs/13_05_29__10_15_23.data");
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
			

	}
	/**
	 * StartUp for Accelerometers
	 * @param t
	 * @param panel
	 * @param id
	 * @param imu
	 * @param Accl
	 */
	public StartUp(TypeCalibration t, final JPanel panel, int id, IMU imu,
			int Accl) {
		final Sphere sp = new Sphere(5, 5);
		final AffichAccel affAccel = new AffichAccel(sp);
		FilterAccel filtre = new FilterAccel(40, t, 300, 20, affAccel);
		Data data = new Data(t, filtre);
		PrintLog prlog = new PrintLog();
		// GUIHelper.showOnFrame(sp.getAffichage(), "test");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.add(affAccel,BorderLayout.CENTER);
				panel.validate();
			}
		});
		
		// For test
		Sender s;
		try {
			s = new Sender(System.getenv("HOME") + 
					"/paparazzi/var/logs/13_05_29__10_15_23.data");
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
		
		
		
		//imu.ListenIMU(data, t, prlog);
		

	}

}
