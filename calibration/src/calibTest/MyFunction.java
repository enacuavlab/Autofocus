package calibTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateVectorFunction;

public class MyFunction implements MultivariateVectorFunction {

    private List<Double> x;
    private List<Double> y;

	public MyFunction() {
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
	}
	

    public void addPoint(double x, double y) {
        this.x.add(x);
        this.y.add(y);
    }
    
    public double[] calculateTarget() {
        double[] target = new double[y.size()];
        for (int i = 0; i < y.size(); i++) {
                target[i] = y.get(i).doubleValue();
        }
        return target;
    }



	public double[] value(double[] variables) {
        double[] values = new double[x.size()];
        for (int i = 0; i < values.length; ++i) {
            values[i] = (variables[0] * x.get(i) + variables[1]) * x.get(i) + variables[2];
        }
        return values;
	}// end of value function

}// end of class myFunction

