package data;

import java.util.*;
import common.TypeCalibration;
import java.lang.*;
import javax.vecmath.*;





public class Data {
	
	private TypeCalibration calib;
	
	HashMap hm = new HashMap();
	
	private Vecteur vec;
	

public Data(TypeCalibration calibration){	
	this.calib = calibration;
	}


public void store(double x_raw, double y_raw, double z_raw){
	
	this.vec = new Vecteur(x_raw,y_raw, z_raw);
	vec.setType(calib);
	hm.put(vec.hashCode(), vec);
	
	}




public void montre(){
	Set set = hm.entrySet();
	// Get an iterator
	Iterator i = set.iterator();
	// Display elements
	while(i.hasNext()) {
	Map.Entry me = (Map.Entry)i.next();
	System.out.print(me.getKey() + ": ");
	System.out.println(me.getValue());
	} 
}



}

