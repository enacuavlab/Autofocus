package calibTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateVectorFunction;

import filtre.VecteurFiltrable;

public class MyFunction implements MultivariateVectorFunction {

    private List<Double> x;
    private List<Double> y;
    private List<Double> z;
    
	public MyFunction() {
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();
	}
	

    public void add(VecteurFiltrable<Double> v) {
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
    	//Tout les points sont � 1 donc on ne s'embete pas
    	return calculateTarget();
    }

    private int getMinX() {
    	int index = 0;
    	int i = 1;
    	for(double v : x){
    		if(x.get(index) > v) {
    			index = i;
    		}
    		i++;
    	}
    	return index;
    }
    
    private int getMaxX() {
    	int index = 0;
    	int i = 1;
    	for(double v : x){
    		if(x.get(index) < v) {
    			index = i;
    		}
    		i++;
    	}
    	return index;
    }
    
    private int getMinY() {
    	int index = 0;
    	int i = 1;
    	for(double v : y){
    		if(y.get(index) > v) {
    			index = i;
    		}
    		i++;
    	}
    	return index;
    }
    
    private int getMaxY() {
    	int index = 0;
    	int i = 1;
    	for(double v : y){
    		if(y.get(index) < v) {
    			index = i;
    		}
    		i++;
    	}
    	return index;
    }
    
    private int getMinZ() {
    	int index = 0;
    	int i = 1;
    	for(double v : z){
    		if(z.get(index) > v) {
    			index = i;
    		}
    		i++;
    	}
    	return index;
    }
    
    private int getMaxZ() {
    	int index = 0;
    	int i = 1;
    	for(double v : z){
    		if(z.get(index) < v) {
    			index = i;
    		}
    		i++;
    	}
    	return index;
    }
    
    
    public double[] getInitialGuess() {
    	double[] res = new double[6]; 
    	res[0] = (getMaxX() + getMinX())/2;
    	res[1] = (getMaxY() + getMinY())/2;
    	res[2] = (getMaxZ() + getMinZ())/2;
    	res[3] = (getMaxX() - getMinX())/2;
    	res[4] = (getMaxY() - getMinY())/2;
    	res[5] = (getMaxZ() - getMinZ())/2;
    	System.out.println("Initial guess " + res[0] + " " + res[1] + " " + res[2] + 
    			":"
    			+ res[3] + " " + res[4] + " " + res[5]);
    	return res;
    }
    
	public double[] value(double[] variables) {
        double[] values = new double[x.size()];
        double smx, smy, smz;
        for (int i = 0; i < values.length; ++i) {
        	smx = (x.get(i) - variables[0])*variables[3];
        	smy = (y.get(i) - variables[1])*variables[4];
        	smz = (z.get(i) - variables[2])*variables[5];
            values[i] = (smx*smx + smy*smy + smz*smz);
        }
        return values;
	}// end of value function

}// end of class myFunction

