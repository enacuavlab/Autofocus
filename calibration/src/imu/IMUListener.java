package imu;

import java.util.EventListener;

import data.Aircraft;

public interface IMUListener extends EventListener {
	void aircraftModChanged(Aircraft ac) ;
	void aircraftExited(Aircraft ac);
	void aircraftConnected(Aircraft ac);
	void aircraftRawOn(Aircraft ac);
	void aircraftRawOff(Aircraft ac);
	void aircraftCurrentExited(Aircraft ac);
}
