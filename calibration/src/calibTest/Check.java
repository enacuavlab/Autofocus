package calibTest;

import org.apache.commons.math3.optim.ConvergenceChecker;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.PointVectorValuePair;

public class Check implements ConvergenceChecker<PointVectorValuePair> {

	public boolean converged(int arg0, PointValuePair arg1,
			PointValuePair arg2) {
		double[] temp1 = arg2.getPoint();
		double temp2 = arg2.getValue();
		double[] temp3 = arg1.getPoint();
		double res = 0;
		double diff = 0;
		for (int i = 0; i < 6; i++) {
			res = res + Math.abs(temp1[i] - temp2);
			diff = diff + Math.abs(temp1[i] - temp3[i]);
		}
		return res < 1 || diff < 1;
	}
	
	public boolean converged(int arg0, PointVectorValuePair arg1,
			PointVectorValuePair arg2) {
		double[] temp1 = arg2.getPoint();
		double[] temp2 = arg2.getValue();
		double[] temp3 = arg1.getPoint();
		double res = 0;
		double diff = 0;
		for (int i = 0; i < 6; i++) {
			res = res + Math.abs(temp1[i] - temp2[i]);
			diff = diff + Math.abs(temp1[i] - temp3[i]);
		}
		return res < 0.1 || diff < 0.1;
	}
}
