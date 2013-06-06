package calibTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateVectorFunction;

import data.Vecteur;

public class MyFunction implements MultivariateVectorFunction {

    private List<Double> x;
    private List<Double> y;
    private List<Double> z;
    
	public MyFunction() {
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();
	}
	

    public void add(Vecteur v) {
    	this.x.add(v.getX());
    	this.y.add(v.getY());
    	this.z.add(v.getZ());
    }
    
    public double[] calculateTarget() {
        double[] target = new double[y.size()];
        for (int i = 0; i < y.size(); i++) {
                target[i] = 1;
        }
        return target;
    }
    
    public double[] calculateWeight() {
    	//Tout les points sont à 1 donc on ne s'embete pas
    	return calculateTarget();
    }

	public double[] value(double[] variables) {
        double[] values = new double[x.size()];
        double smx, smy, smz;
        for (int i = 0; i < values.length; ++i) {
        	smx = (x.get(i) - variables[0])*variables[3];
        	smy = (y.get(i) - variables[1])*variables[4];
        	smz = (z.get(i) - variables[2])*variables[5];
            values[i] = smx*smx + smy*smy + smz*smz;
        }
        return values;
	}// end of value function

}// end of class myFunction

