package imu;

import javax.swing.SwingUtilities;

import fr.dgac.ivy.*;
import common.TypeCalibration;
import data.Data;

/**
 * This is the class that links the calibrating program to the IVY bus in order
 * to get the RAW_DATA messages.
 * 
 * @author Florent GERVAIS
 */

public class IMU implements IvyMessageListener {

	private Ivy bus;

	/**
	 * allows to get back the right RAW_DATA messages
	 * 
	 * @throws IvyException
	 */
	public IMU(TypeCalibration calibration, int IdDrone, final Data data) {
		System.out.println("Debut IMU");
		try {
			/*
			 * if (TypeCalibration.ACCELEROMETER.equals(calibration)) { //
			 * initialization, name and ready message bus = new Ivy("IMU",
			 * "IMU Ready", null); // get back of the RAW_DATA messages needed
			 * depending on the // calibration bus.bindMsg(
			 * "^[A-Za-z0-9]+ IMU_ACCEL_RAW ([/-]*[0-9]+) ([/-]*[0-9]+) ([/-]*[0-9]+)"
			 * , new IvyMessageListener() { public void receive(IvyClient arg0,
			 * String[] args) { System.out.println(args[0] + " " + args[1] + " "
			 * + args[2]); data.store(Integer.valueOf(args[0]),
			 * Integer.valueOf(args[1]), Integer.valueOf(args[2])); } } ); }
			 * else{ if (TypeCalibration.MAGNETOMETER.equals(calibration)) { //
			 * initialization, name and ready message bus = new Ivy("IMU",
			 * "IMU Ready", null); // get back of the RAW_DATA messages needed
			 * depending on the // calibration bus.bindMsg(
			 * "^[A-Za-z0-9]+ IMU_MAG_RAW ([/-]*[0-9]+) ([/-]*[0-9]+) ([/-]*[0-9]+)"
			 * , new IvyMessageListener(){ public void receive(IvyClient arg0,
			 * String[] args) { System.out.println("Magnet IMU" + args[0] + " "
			 * + args[1] + " " + args[2]); data.store(Integer.valueOf(args[0]),
			 * Integer.valueOf(args[1]), Integer.valueOf(args[2])); } } ); } }
			 */
			// starts the bus on the default domain
			bus = new Ivy("IMU", "IMU Ready", null);
			// build the regexp according to parameters
			StringBuffer regexp = new StringBuffer("^");
			regexp.append(IdDrone);
			regexp.append(TypeCalibration.MAGNETOMETER.equals(calibration) ? " IMU_MAG_RAW"
					: " IMU_ACCEL_RAW");

			regexp.append(" ([\\-]*[0-9]+)");
			regexp.append(" ([\\-]*[0-9]+)");
			regexp.append(" ([\\-]*[0-9]+)");
			String test = regexp.toString();
			System.out.println(test);
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0,final String args[]) {
					 //System.out.println("IMU : " + "x:" + args[0] + " y:" +
					 //args[1] + " z:" + args[2]);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							data.store(Integer.valueOf(args[0]),
								Integer.valueOf(args[1]), Integer.valueOf(args[2]));
						}
					});
				}
			});
			bus.start(null);
		} catch (Exception e) {
			System.out.println("Erreur d'initialisation d'IMU");
			e.printStackTrace();
		}
	}

	public void arret() {
		bus.stop();
	}

	@Override
	public void receive(IvyClient arg0, String[] args) {
		System.out.println(args[0] + " " + args[1] + " " + args[2]);
	}
}
