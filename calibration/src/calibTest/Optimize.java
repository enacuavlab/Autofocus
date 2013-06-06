package calibTest;

import org.apache.commons.math3.optim.nonlinear.vector.ModelFunction;
import org.apache.commons.math3.optim.nonlinear.vector.ModelFunctionJacobian;
import org.apache.commons.math3.optim.nonlinear.vector.Target;
import org.apache.commons.math3.optim.nonlinear.vector.Weight;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.optim.*;

public class Optimize {

	public static void main(String[] args) {

		MyFunction error = new MyFunction();
		My jac = new My
		
		 error.addPoint(1, 34.234064369);
		 error.addPoint(2, 68.2681162306);
		 error.addPoint(3, 118.6158990846);
		 error.addPoint(4, 184.1381972386);
		 error.addPoint(5, 266.5998779163);
		 error.addPoint(6, 364.1477352516);
		 error.addPoint(7, 478.0192260919);
		 error.addPoint(8, 608.1409492707);
		 error.addPoint(9, 754.5988686671);
		 error.addPoint(10, 916.1288180859);

		// weight Matrix
		final double[] weights = new double[12];

		// guess
		double[] startPoint = { 1, 0.0 }; // expectedSolution : a = 2, b=1; (2x
											// + 1)

		final LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();

		final PointVectorValuePair optimum = optimizer.optimize(
				new MaxEval(100), new InitialGuess(startPoint), new Target(
						), new Weight(weights), new ModelFunction(
						), new ModelFunctionJacobian(
						new MyJacobian()));

		final double[] solution = optimum.getPoint();

		for (int i = 0; i < startPoint.length; i++) {
			System.out
					.println("Parameter " + i + ", solution = " + solution[i]);
		}// end of for

	}// end

}// end of class

