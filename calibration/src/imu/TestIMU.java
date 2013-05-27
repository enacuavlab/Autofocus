package imu;

import ihm.FilterPlot;
import ihm.Plotter;
import common.TypeCalibration;
import data.Data;

import filtre.Filter;
import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		
		TypeCalibration t = TypeCalibration.MAGNETOMETER;
		Plotter plot = new Plotter();
		Filter filtre = new FilterPlot(plot);
		Data data = new Data(t,filtre);
		IMU imu =new IMU(t,data);
		Thread.sleep(150000);
		imu.stop();
		System.out.println(data.toString()); 
		
	}
}
