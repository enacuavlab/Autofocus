package imu;

import testData.Sender;
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
		Filter filtre = new Filter(10,TypeCalibration.MAGNETOMETER);
		Data data = new Data(t,filtre);
		IMU imu =new IMU(t,data);
		Sender s = new Sender(args[0]);
		s.sendRawMessage();
		System.out.println(data.toString()); 
		
	}
}
