package ihm;

import imu.Aircraft;
import imu.IMUListener;

/** Abstract class to ease the use of the IMU class by providing listener interface
 * 
 * @author alinoe
 *
 */

public abstract class IMUAdaptater implements IMUListener {

	@Override
	public void aircraftModChanged(Aircraft ac) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aircraftExited(Aircraft ac) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void aircraftCurrentExited(Aircraft ac) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aircraftConnected(Aircraft ac) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aircraftRawOn(Aircraft ac) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aircraftRawOff(Aircraft ac) {
		// TODO Auto-generated method stub

	}

}
