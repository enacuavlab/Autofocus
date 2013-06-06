package calibTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;

import filtre.VecteurFiltrable;

public class MyJacobian implements MultivariateMatrixFunction {

    private List<Double> x;
    private List<Double> y;
    private List<Double> z;
    
	public MyJacobian() {
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();
	}
	
    public void add(VecteurFiltrable<Double> v) {
    	this.x.add(v.getX());
    	this.y.add(v.getY());
    	this.z.add(v.getZ());
    }

	public double[][] value(double[] variables) {
		double[][] jacobian = new double[x.size()][6];
		for (int i = 0; i < jacobian.length; ++i) {
			jacobian[i][0] =  2*(variables[0]-x.get(i))*variables[3]*variables[3];
			jacobian[i][1] =  2*(variables[1]-y.get(i))*variables[4]*variables[4];
			jacobian[i][2] =  2*(variables[2]-z.get(i))*variables[5]*variables[5];
			jacobian[i][3] =  2*(x.get(i) - variables[0])*(x.get(i) - variables[0])*variables[3];
			jacobian[i][4] =  2*(y.get(i) - variables[1])*(y.get(i) - variables[1])*variables[4];
			jacobian[i][5] =  2*(z.get(i) - variables[2])*(z.get(i) - variables[2])*variables[5];
		}
		return jacobian;
	}
}
