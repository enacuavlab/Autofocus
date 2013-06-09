package obsolete;

import java.util.List;


import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.PointVectorValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.MultiDirectionalSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.optim.nonlinear.vector.JacobianMultivariateVectorOptimizer;
import org.apache.commons.math3.optim.nonlinear.vector.ModelFunction;
import org.apache.commons.math3.optim.nonlinear.vector.ModelFunctionJacobian;
import org.apache.commons.math3.optim.nonlinear.vector.Target;
import org.apache.commons.math3.optim.nonlinear.vector.Weight;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;

import filtre.VecteurFiltrable;

public class Optimize {

	MyFunction error = new MyFunction();
	MyJacobian jac = new MyJacobian();
	
	public Optimize(List<VecteurFiltrable<Double>> data) {
		for(VecteurFiltrable<Double> v : data) {
			error.add(v);
			jac.add(v);
		}
	}

	public void optimize() {

		// weight Matrix
		 final double[] weights = error.calculateWeight();

		// guess
		double[] startPoint = error.getInitialGuess();

		final JacobianMultivariateVectorOptimizer optimizer = new LevenbergMarquardtOptimizer(new Check());
		//final SimplexOptimizer SO = new SimplexOptimizer(new Check());
		
		final PointVectorValuePair optimum = optimizer.optimize(
				new MaxEval(10000), new InitialGuess(startPoint), new Target(error.calculateTarget()),
				new Weight(weights), new ModelFunction(error), new ModelFunctionJacobian(jac));

		
		final double[] solution = optimum.getPoint();

		for (int i = 0; i < solution.length; i++) {
			System.out
					.println("Parameter " + i + ", solution = " + solution[i]);
		}// end of for

	}// end

}// end of class

