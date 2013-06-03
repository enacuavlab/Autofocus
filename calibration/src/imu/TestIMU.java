package imu;

import testData.Sender;
import ihm.GUIHelper;
import ihm.Plotter;
import common.TypeCalibration;
import data.Data;


import filtre.FilterPlot;
import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		TypeCalibration t = TypeCalibration.MAGNETOMETER;
		Plotter plot = new Plotter();
		FilterPlot filtre = new FilterPlot(plot,10,TypeCalibration.MAGNETOMETER);
		Data data = new Data(t,filtre);
		GUIHelper.showOnFrame(plot, "test");
		Sender s = new Sender("home/workspace/autofocus#/calibration/13_04_03__13_49_35.data");
		IMU imu =new IMU(t,17,data);
		s.start();
		s.join();
		imu.arret();
		//System.out.println(data.toString());	
	}
}
