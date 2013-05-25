package imu;

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
	IMU(TypeCalibration calibration, final Data data) throws IvyException {
		if (TypeCalibration.ACCELEROMETER.equals(calibration)) {
			// initialization, name and ready message
			bus = new Ivy("IMU", "IMU Ready", null);
			// get back of the RAW_DATA messages needed depending on the
			// calibration
			bus.bindMsg(
					"^[A-Za-z0-9]+ IMU_ACCEL_RAW ([/-]*[0-9]+) ([/-]*[0-9]+) ([/-]*[0-9]+)",
					new IvyMessageListener() {
						public void receive(IvyClient arg0, String[] args) {
							System.out.println(args[0] + " " + args[1] + " "
									+ args[2]);
							data.store(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]));
						}
					});
			// starts the bus on the default domain
			bus.start(null);
		}
		if (TypeCalibration.MAGNETOMETER.equals(calibration)) {
			// initialization, name and ready message
			bus = new Ivy("IMU", "IMU Ready", null);
			// get back of the RAW_DATA messages needed depending on the
			// calibration
			bus.bindMsg(
					"^[A-Za-z0-9]+ IMU_MAG_RAW ([/-]*[0-9]+) ([/-]*[0-9]+) ([/-]*[0-9]+)",
					this);
			// starts the bus on the default domain
			bus.start(null);
		}
	}
	public void stop(){
		bus.stop();
	}

	@Override
	public void receive(IvyClient arg0, String[] args) {
		System.out.println(args[0] + " " + args[1] + " " + args[2]);
	}

}
