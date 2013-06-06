package calibTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;

public class MyJacobian implements MultivariateMatrixFunction {

	private List<Double> x;
	private List<Double> y;

	public MyJacobian() {
		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
	}

	public void addPoint(double x, double y) {
		this.x.add(x);
		this.y.add(y);
	}

	public double[][] value(double[] variables) {
		double[][] jacobian = new double[x.size()][3];
		for (int i = 0; i < jacobian.length; ++i) {
			jacobian[i][0] = x.get(i) * x.get(i);
			jacobian[i][1] = x.get(i);
			jacobian[i][2] = 1.0;
		}
		return jacobian;
	}
}
