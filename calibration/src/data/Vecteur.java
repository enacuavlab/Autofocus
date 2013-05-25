package data;

import java.util.*;
import common.TypeCalibration;
import java.lang.*;
import javax.vecmath.*;
import filtre.Filtrable;



public class Vecteur extends Vector3d implements Filtrable<Vector3d> {
	
	
	//state: etat du vecteur apres filtrage
private boolean state = false;

//type de calibration(ACCELEROMETER, MAGNETOMETER)

private TypeCalibration type;


public Vecteur(double x, double y, double z){
	super(x,y,z);
}	

public boolean getState(){
	return state;
}

public void setType(TypeCalibration t){
	this.type = t;
}

public TypeCalibration getType(){
	return type;
}


public Vector3d getObject(){
	return new Vector3d(x,y,z);
}
public boolean equals(Filtrable<Vector3d> test){
	return super.equals(test.getObject());
}
public float away(Filtrable<Vector3d> test){
	return 0;
		
}
public boolean isCorrect(){
	return true;
	
}
public void setFalse(){
	this.state = false;
	
}
public void setTrue(){
	this.state = true;
}
public String toString(){
	return super.toString()+"sate"+this.state+System.getProperty("line.separator");//"" + "state"+ " type" +"");
	
}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}
