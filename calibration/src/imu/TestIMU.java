package imu;

import java.util.List;

import testData.Sender;

import calibTest.FoncDeux;
import calibTest.FoncDog;
import calibTest.Fonction;
import calibTest.LevenbergMarquardt;
import common.TypeCalibration;

import org.ddogleg.optimization.FactoryOptimization;
import org.ddogleg.optimization.UnconstrainedLeastSquares;
import org.ddogleg.optimization.UtilOptimize;
import org.ddogleg.optimization.impl.LevenbergBase;
import org.ejml.data.*;

import data.Data;
import data.Vecteur;

import ellipsoide.Sphere;
import filtre.Filter;
import filtre.FilterSphere;
import filtre.GUIHelper;

import filtre.Filter;

import fr.dgac.ivy.IvyException;

public class TestIMU {
	public static void main(String args[]) throws IvyException, InterruptedException {
		TypeCalibration t = TypeCalibration.MAGNETOMETER;

		System.out.println("type");
		//Sphere sp = new Sphere(20,10);
		Filter filtre = new Filter(10,TypeCalibration.MAGNETOMETER);
		System.out.println("filtre");

		Data data = new Data(t,filtre);

		System.out.println("data");
		//GUIHelper.showOnFrame(sp.getAffichage(), "test");
		//Sender s = new Sender("/home/paparazzi/var/logs/13_05_29__10_15_23.data");
		Sender s = new Sender("/home/christophe/paparazzi/var/log/13_05_29__10_15_23.data");
		System.out.println("sender");

		IMU imu =new IMU(t,17,data);
		s.start();
		s.join();
		imu.arret();

	
		
		//System.out.println(data.toString());	

		System.out.println("fin");
		System.out.println(data.toString());
		

		List<Vecteur> list = data.getVecteur();
		
		
		DenseMatrix64F nombre = new DenseMatrix64F(list.size(),3);
		DenseMatrix64F output = new DenseMatrix64F(list.size(),1);
		DenseMatrix64F paramm = new DenseMatrix64F(6,1);
		double[][] points = new double[list.size()][3];
		
		int i = 0;
		for(Vecteur v: list){
			nombre.set(i, 0, v.getX());
			points[i][0] = v.getX();
			i++;
		}
		
		i = 0;
		for(Vecteur v: list){
			nombre.set(i, 1, v.getY());
			points[i][1] = v.getY();
			i++;
		}
		i = 0;
		for(Vecteur v: list){
			nombre.set(i, 2, v.getZ());
			points[i][2] = v.getZ();
			i++;
		}
		
		for (i = 0; i < list.size(); i++){
			output.set(i,0,1);
		}
		
		FoncDeux fff = new FoncDeux(nombre);
		
		
		//LevenbergMarquardt lm = new LevenbergMarquardt(fff);
		
		//System.out.println(lm.optimize(paramm, nombre, output));
		
		//System.out.println(lm.getInitialCost());
		//System.out.println(lm.getFinalCost());
		//System.out.println(lm.getParameters());
		
		
		
		FoncDog free = new FoncDog(points);
		
		UnconstrainedLeastSquares optimizer = FactoryOptimization.leastSquaresLM(1e-3, true);
		
		optimizer.setFunction(free,null);
		
		double [] initpar = free.initial();
		double [] initpar2 = {200 , 370 , 100,5,5,5};
		
		System.out.println(initpar[1]+" init "+initpar[4]);
		
		
		optimizer.initialize(initpar2,1e-12,1e-12);
		
				
		UtilOptimize.process(optimizer,500);
		
		System.out.println(free.getM()+" et "+free.getN());
		double found[] = optimizer.getParameters();
		
		
		System.out.println(optimizer.getFunctionValue());
		for (i = 0; i<found.length/2;i++){
		System.out.println(found[i]+ ":" + found[i+3]);
		}
		
		// if no jacobian is specified it will be computed numerically
		//optimizer.setFunction(free,null);

	
		// iterate 500 times or until it converges.
		// Manually iteration is possible too if more control over is required
		//UtilOptimize.process(optimizer,500);

		
		
		
	
	
		
	}
}
