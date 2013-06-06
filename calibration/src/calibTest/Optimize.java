package calibTest;

import java.util.List;

import org.apache.commons.math3.optim.nonlinear.vector.ModelFunction;
import org.apache.commons.math3.optim.nonlinear.vector.ModelFunctionJacobian;
import org.apache.commons.math3.optim.nonlinear.vector.Target;
import org.apache.commons.math3.optim.nonlinear.vector.Weight;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.optim.*;

import data.Vecteur;

public class Optimize {

	MyFunction error = new MyFunction();
	MyJacobian jac = new MyJacobian();
	
	public Optimize(List<Vecteur> data) {
		for(Vecteur v : data) {
			error.add(v);
			jac.add(v);
		}
	}
	
	public void optimize() {


		// weight Matrix
		 final double[] weights = error.calculateWeight();

		// guess
		double[] startPoint = {300,300,300,5,5,5};

		final LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();

		final PointVectorValuePair optimum = optimizer.optimize(
				new MaxEval(100), new InitialGuess(startPoint), new Target(error.calculateTarget()),
				new Weight(weights), new ModelFunction(error), new ModelFunctionJacobian(jac));

		final double[] solution = optimum.getPoint();

		for (int i = 0; i < startPoint.length; i++) {
			System.out
					.println("Parameter " + i + ", solution = " + solution[i]);
		}// end of for

	}// end

}// end of class

