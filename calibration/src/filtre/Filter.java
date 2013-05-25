package filtre;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
public class Filter {

	private Collection<DescriptiveStatistics> variables = null;
	private int windowSize;
	private SlidingWindow<VecteurFiltrable<Double>> window; 
	
	public Filter(int windowSize){
		this.windowSize=windowSize;
		window = new SlidingWindow<VecteurFiltrable<Double>>(windowSize);
	}
	
	public Filter() {
		this.windowSize = 40;
	}

	private void update(){
		for(DescriptiveStatistics e : variables){
			System.out.println("std" + e.getStandardDeviation());
		}
	}
	
	public void add(VecteurFiltrable<Double> v){
		Collection<Double> toAdd = v.setArray();
		Iterator<Double> a = toAdd.iterator();
		Iterator<DescriptiveStatistics> d = variables.iterator();
		if (variables != null) {
			while(a.hasNext()){
				d.next().addValue(a.next());
			}
			window.add(v);
			update();
		}
		else {
			this.variables = new ArrayList<DescriptiveStatistics>(toAdd.size());
			for(DescriptiveStatistics e : variables){
				e = new DescriptiveStatistics(windowSize);
			}
			this.add(v);
		}
	}
	
}

	