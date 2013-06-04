package calibTest;

import org.ejml.alg.dense.linsol.AdjustableLinearSolver;
import org.ejml.alg.dense.linsol.LinearSolverAbstract;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.LinearSolver;
import org.ejml.factory.LinearSolverFactory;

import common.TypeCalibration;
import org.ejml.ops.*;
import org.apache.commons.math3.optimization.general.*;
import org.ddogleg.*;
import org.ddogleg.optimization.FactoryOptimization;
import org.ddogleg.optimization.UnconstrainedLeastSquares;
import org.ddogleg.optimization.functions.FunctionNtoM;
import org.ddogleg.optimization.impl.LevenbergDenseBase;

class TestFunc {
	
	double sf = 1;
	
	//static DenseMatrix64F numbers;
	
DenseMatrix64F points;
	DenseMatrix64F sm;

	DenseMatrix64F x; 
	DenseMatrix64F y;
	DenseMatrix64F z;
	
	double smx;
	double smy;
	double smz;
	int i;
	

	
	
public static void main(String []args){
	
		
	int i,j;
	int maxElements =6;
	
	DenseMatrix64F numbers = new DenseMatrix64F(maxElements,3);
	DenseMatrix64F output = new DenseMatrix64F(maxElements,1);
	DenseMatrix64F param = new DenseMatrix64F(6,1);
	
	//x.add(1,0,5);
	
	/*for (j = 0; j < numbers.getNumCols(); j++){
	for (i = 0; i < numbers.getNumRows(); i++){
	numbers.set(i,j,i);
	
	if (j == 0){output.set(i,0,1);}
	}}
	*/
	
	for (i = 0; i < numbers.getNumRows(); i++){
		output.set(i,0,1);
	}
	
	numbers.set(0,0,5.1);
	numbers.set(0,1,2);
	numbers.set(0,2,2);
	
	numbers.set(1,0,2);
	numbers.set(1,1,4.1);
	numbers.set(1,2,2);
	
	numbers.set(2,0,0.1);
	numbers.set(2,1,2);
	numbers.set(2,2,2);
	
	numbers.set(3,0,2);
	numbers.set(3,1,0.1);
	numbers.set(3,2,2);
	
	numbers.set(4,0,2);
	numbers.set(4,1,2);
	numbers.set(4,2,5.1);
	
	numbers.set(5,0,2);
	numbers.set(5,1,2);
	numbers.set(5,2,0);
	

	
	Fonction ff = new Fonction(numbers);
	
	
	LevenbergMarquardt lm = new LevenbergMarquardt(ff);
	
	System.out.println(lm.optimize(param, numbers, output));
	
	param.set(lm.getParameters());
	
	double r = param.get(1);
	
	double a = lm.getInitialCost();
	
	System.out.println(lm.getInitialCost());
	System.out.println(lm.getFinalCost());
	System.out.println(lm.getParameters());
	
	
	//Fonction awesome = new Fonction(numbers);
	//tt.hallo();
	
	
	
	
	

	//awesome.compute( init,numbers, numbers);
	/*
	LinearSolver<DenseMatrix64F> solver;
	
	solver = LinearSolverFactory.leastSquares(6,70);

	solver.setA(numbers);
	solver.solve(output, param);
	double a = param.get(1);
	System.out.println(a);*/
}	
}
	
	/*
	
	public void hallo(){
		
					//double a = x.get(i,j);
			//System.out.println(a);
		/*
		this.param = new DenseMatrix64F(6,1);
		this.param.add(0,0,1);
		this.param.add(1,0,2);
		this.param.add(2,0,3);
		this.param.add(3,0,1);
		this.param.add(4,0,2);
		this.param.add(5,0,3);
		
		DenseMatrix64F wow = new DenseMatrix64F(numbers.getNumRows(),1);
		wow = CommonOps.extract(numbers,0,numbers.getNumRows(),0,1);
		
		for(i=0;i<wow.getNumRows(); i++){
		double c = wow.get(i);
		//System.out.println(c);
		//scalepoints(x,param);
		
				}
	}
	



	public void Fonc(DenseMatrix64F data){
		
		this.points = new DenseMatrix64F(data.getNumRows(),data.getNumCols());
		this.points.set(data);
		this.sm = new DenseMatrix64F(data.getNumRows(),1);
		
		this.x = new DenseMatrix64F(points.getNumRows(),1);
		this.y = new DenseMatrix64F(points.getNumRows(),1);
		this.z = new DenseMatrix64F(points.getNumRows(),1);
		
		this.x = CommonOps.extract(points,0,points.getNumRows(),0,1);
		this.y = CommonOps.extract(points,0,points.getNumRows(),1,2);
		this.z = CommonOps.extract(points,0,points.getNumRows(),2,3);
		
		System.out.println("fonclala");
		
		initialPar();
		
		
	}
	

	
	public void scalepoints(){
		
		for (i = 0; i< points.getNumRows();i++){

		smx = (points.get(i,0) - param.get(0))*param.get(3);
		smy = (points.get(i,1) - param.get(1))*param.get(4);
		smz = (points.get(i,2) - param.get(2))*param.get(5);
		
		
		//this.smNorm.add(i,0,NormOps.normP1(smtest));
		
		this.sm.set(i, 0, Math.sqrt(smx*smx + smy*smy + smz*smz));
		System.out.println("scales");

		}
		
	}
	public void initialPar(){
		
				
		this.param.set(0,0,(CommonOps.elementMax(x)+CommonOps.elementMin(x))/2);
		this.param.set(1,0,(CommonOps.elementMax(y)+CommonOps.elementMin(y))/2);
		this.param.set(2,0,(CommonOps.elementMax(z)+CommonOps.elementMin(z))/2);
		
		this.param.set(3,0,2*sf/(CommonOps.elementMax(x)-CommonOps.elementMin(x)));
		this.param.set(4,0,2*sf/(CommonOps.elementMax(y)-CommonOps.elementMin(y)));
		this.param.set(5,0,2*sf/(CommonOps.elementMax(z)-CommonOps.elementMin(z)));
		
		System.out.println("initpar");
		
		for (i = 0; i< param.getNumRows();i++){
			
			double b = param.get(i);

		System.out.println(b);

		}
	}

	
	
	public void compute( DenseMatrix64F paramameter , DenseMatrix64F input , DenseMatrix64F output){
		

		
		//err.add(i, 0, sf-NormOps.normP1(sm));
	
					
		
		
	}*/
	


		
	
		
	
