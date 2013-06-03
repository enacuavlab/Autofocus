package calibTest;

import org.ddogleg.optimization.functions.FunctionNtoM;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import common.TypeCalibration;

class Fonction{
	
	//DenseMatrix64F x;
	
	

	private DenseMatrix64F points;
	private DenseMatrix64F sm;
	public DenseMatrix64F param = new DenseMatrix64F(6,1);
	private DenseMatrix64F x; 
	private DenseMatrix64F y;
	private DenseMatrix64F z;
	private DenseMatrix64F err;
	public DenseMatrix64F out;
	private TypeCalibration calib;
	
	//ArrayList points = new ArrayList();
	private double sf;
	
	private double smx;
	private double smy;
	private double smz;
	
	public Fonction(DenseMatrix64F data){
		
		if (calib.equals(TypeCalibration.ACCELEROMETER)){
			this.sf = 9.81;
		}
		else{this.sf = 1;	
		}
		
		
		this.points = new DenseMatrix64F(data.getNumRows(),data.getNumCols());
		this.sm = new DenseMatrix64F(data.getNumRows(),1);
		
		this.out = new DenseMatrix64F(data.getNumRows(),1);
		this.out.set(0,out.getNumRows(),sf);
		
		this.err = new DenseMatrix64F(data.getNumRows(),1);
		
		this.x = new DenseMatrix64F(points.getNumRows(),1);
		this.y = new DenseMatrix64F(points.getNumRows(),1);
		this.z = new DenseMatrix64F(points.getNumRows(),1);
		
		this.x = CommonOps.extract(points,0,points.getNumRows(),0,1);
		this.y = CommonOps.extract(points,0,points.getNumRows(),1,2);
		this.z = CommonOps.extract(points,0,points.getNumRows(),2,3);
		System.out.println("start ok");
		
	}
	
	
	public DenseMatrix64F initialPar(){
				
		this.param.set(0,0,(CommonOps.elementMax(x)+CommonOps.elementMin(x))/2);
		this.param.set(1,0,(CommonOps.elementMax(y)+CommonOps.elementMin(y))/2);
		this.param.set(2,0,(CommonOps.elementMax(z)+CommonOps.elementMin(z))/2);
		
		this.param.set(3,0,2*sf/(CommonOps.elementMax(x)-CommonOps.elementMin(x)));
		this.param.set(4,0,2*sf/(CommonOps.elementMax(y)-CommonOps.elementMin(y)));
		this.param.set(5,0,2*sf/(CommonOps.elementMax(z)-CommonOps.elementMin(z)));
		
		
		System.out.println("initial ok");
		return param;
	}

	public void scalepoints(){
		
		for (int i = 0; i< points.getNumRows();i++){

			smx = (points.get(i,0) - param.get(0))*param.get(3);
			smy = (points.get(i,1) - param.get(1))*param.get(4);
			smz = (points.get(i,2) - param.get(2))*param.get(5);
			
			this.sm.set(i, 0, Math.sqrt(smx*smx + smy*smy + smz*smz));
		}
		System.out.println("scale ok");
	
	}
	
	
	public void compute( DenseMatrix64F parameter , DenseMatrix64F input , DenseMatrix64F output){
		
		if (parameter.getNumRows()==6){
				
			for (int i = 0; i< points.getNumRows();i++){

			smx = (points.get(i,0) - parameter.get(0))*parameter.get(3);
			smy = (points.get(i,1) - parameter.get(1))*parameter.get(4);
			smz = (points.get(i,2) - parameter.get(2))*parameter.get(5);
			
			input.set(i, 0, Math.sqrt(smx*smx + smy*smy + smz*smz));
			output.set(i,0, sf - sm.get(i));
			
			}
		}
		else {System.out.println("param error");}
		
		System.out.println("compu ok");
		
		
		//err.add(i, 0, sf-NormOps.normP1(sm));
			
	}
	
}
