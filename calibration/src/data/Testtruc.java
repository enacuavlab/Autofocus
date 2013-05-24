package data;

import java.util.*;
import common.TypeCalibration;
import java.lang.*;
import javax.vecmath.*;


public class Testtruc {
	
	public static void main(String [] args){
		Random generator = new Random();
		Data dt = new Data(common.TypeCalibration.ACCELEROMETER);
		
		
		
		for(int i =0; i<10; i++){
		double x = generator.nextDouble();
		double y = generator.nextDouble();
		double z = generator.nextDouble();
		//System.out.println(x);
		dt.store(x,y,z);
		
		dt.montre();
		
		
		}
		
	}
}
