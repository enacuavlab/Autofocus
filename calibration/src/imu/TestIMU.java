package imu;

import common.TypeCalibration;
import data.Data;

import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		
		TypeCalibration t = TypeCalibration.ACCELEROMETER;
		Data data = new Data(t);
		IMU imu =new IMU(t,data);
		Thread.sleep(10000);
		imu.stop();
		Thread.sleep(3000);
		System.out.println(data.toString()); 
		
	}
}
