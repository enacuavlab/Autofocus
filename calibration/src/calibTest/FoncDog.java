package calibTest;
import org.ddogleg.optimization.functions.*;

public class FoncDog implements FunctionNtoM{

	double[][] arg0;
	double[] param;
	//double output;
	double smx,smy,smz;
	double[] output;
	double sf = 1;
	
	public FoncDog(double[][] points){
		
		this.arg0 = new double[points.length][3];
		this.param = new double[6];
		this.output = new double[points.length];
		
			for (int i = 0; i< points.length;i++){
			
				this.arg0[i][0] = points[i][0];
				this.arg0[i][1] = points[i][1];
				this.arg0[i][2] = points[i][2];
			}
	}

	
	@Override
	public int getN() {
		
		return param.length;
	}

	
	public void process(double[] parameter, double[] out ) {
		
			this.output = out;
			
			for (int i = 0; i< arg0.length;i++){

				smx = ((arg0[i][0] - parameter[0])*parameter[3]);
				smy = ((arg0[i][1] - parameter[1])*parameter[4]);
				smz = ((arg0[i][2] - parameter[2])*parameter[5]);
				
				System.out.println(smx+"   "+smy+"    "+smz);
				
			//out[i] = Math.sqrt(   (smx-smx/Math.sqrt(smx*smx+smy*smy+smz*smz) )+(smy-smy/Math.sqrt(smx*smx+smy*smy+smz*smz))+(smz-smz/Math.sqrt(smx*smx+smy*smy+smz*smz)));	
			
			out[i] = Math.sqrt(smx*smx+smy*smy+smz*smz);
			//System.out.println(out[i]);
			//output = Math.sqrt(arg0[i][0]*arg0[i][0] + arg0[i][1]*arg0[i][1] + arg0[i][2]*arg0[i][2]);
			}			
			

	
	}


	@Override
	public int getM() {
		
		return output.length;
	}

	public double[] initial(){
		
		double[] x = new double[arg0.length];
		double[] y = new double[arg0.length];
		double[] z = new double[arg0.length];
		
		
		for (int i =0; i<arg0.length;i++){
			
		x[i] = arg0[i][0];
		y[i] = arg0[i][1];
		z[i] = arg0[i][2];
		
		this.param[0] = (getMaxValue(x) + getMinValue(x))/2;
		this.param[1] = (getMaxValue(y) + getMinValue(y))/2;
		this.param[2] = (getMaxValue(z) + getMinValue(z))/2;
		
		this.param[3] = 2*sf*(getMaxValue(x) - getMinValue(x));
		this.param[4] = 2*sf*(getMaxValue(x) - getMinValue(x));
		this.param[5] = 2*sf*(getMaxValue(x) - getMinValue(x));
		}
		return param;
	}

	
    public static double getMaxValue(double[] numbers){  
        double maxValue = numbers[0];  
        for(int i=1;i < numbers.length;i++){  
          if(numbers[i] > maxValue){  
            maxValue = numbers[i];  
          }  
        }  
        return maxValue;  
      }  
        
      public static double getMinValue(double[] numbers){  
        double minValue = numbers[0];  
        for(int i=1;i<numbers.length;i++){  
          if(numbers[i] < minValue){  
            minValue = numbers[i];  
          }  
        }  
        return minValue;  
      }  
	
	
	
}




