package calibTest;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.*;
import org.ddogleg.optimization.*;

class TestFunc {
	
	double sf = 9.81;
	
	//static DenseMatrix64F numbers;
	
	/*DenseMatrix64F points;
	DenseMatrix64F sm;
	DenseMatrix64F param = new DenseMatrix64F(6,1);
	DenseMatrix64F x; 
	DenseMatrix64F y;
	DenseMatrix64F z;
	
	double smx;
	double smy;
	double smz;
	int i;
//	*/

	
	
public static void main(String []args){
	
		
	int i,j;
	int maxElements =10;
	
	DenseMatrix64F numbers = new DenseMatrix64F(maxElements,3);
	//x.add(1,0,5);
	
	for (j = 0; j < numbers.getNumCols(); j++){
	for (i = 0; i < numbers.getNumRows(); i++){
	numbers.add(i,j,i);
	}}
	Fonction awesome = new Fonction(numbers);
	//tt.hallo();
	
	
	DenseMatrix64F init = new DenseMatrix64F(awesome.initialPar());
	awesome.compute(numbers, init, numbers);
	
	
	
	
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
	


		
	
		
	
