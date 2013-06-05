package calibTest;

import org.ddogleg.optimization.functions.*;
import org.ejml.data.DenseMatrix64F;

public class FoncDeux implements LevenbergMarquardt.Function{
	
	
	double smx, smy, smz;
	
	public FoncDeux(DenseMatrix64F data){
	
	}
		
	

	public void compute( DenseMatrix64F parameter , DenseMatrix64F points , DenseMatrix64F output)
	{
					
		if (points.getNumCols()==3){
				
			for (int i = 0; i< points.getNumRows();i++){

			smx = (points.get(i,0) - parameter.get(0))*parameter.get(3);
			smy = (points.get(i,1) - parameter.get(1))*parameter.get(4);
			smz = (points.get(i,2) - parameter.get(2))*parameter.get(5);
			
			output.set(i, 0, Math.sqrt(smx*smx + smy*smy + smz*smz));
						
			}
		}
		else {
			throw new java.lang.NullPointerException();
			//System.out.println("points error");
			}
	}
}	