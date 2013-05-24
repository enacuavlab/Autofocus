package imu;

import common.TypeCalibration;

import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException {
		TypeCalibration t = TypeCalibration.ACCELEROMETER;
		new IMU(t);
	}
}
