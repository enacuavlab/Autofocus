package data;

import java.util.*;
import common.TypeCalibration;
import java.lang.*;
import javax.vecmath.*;





public class Data {
	

	
	private Vector3d vec;
	private int key;
	private Object val;
	private TypeCalibration calib;
	
	static HashMap hm = new HashMap();
	

public Data(TypeCalibration calibration){
	
	calib = calibration;

	//switch (Typecalibration)
	//case ACCELEROMETER:
	//case MAGNETOMETER:
		
}
public void setVector3d(double x_raw, double y_raw, double z_raw){
	//Vecteur.set(x_raw, y_raw, z_raw);
	
}

//public int getState(){
//	state = f
//	return state;
//}



public void store(double x_raw, double y_raw, double z_raw){
	
	Vector3d v1 = new Vector3d(x_raw,y_raw, z_raw);
	//Vecteur.setType(calib);
	hm.put(v1.hashCode(), v1);
	
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

