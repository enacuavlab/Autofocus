package filtre;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
public class Filter {

	private ArrayList<DescriptiveStatistics> variables;
	private int windowSize;
	private SlidingWindow<VecteurFiltrable<Double>> window; 
	
	public Filter(int windowSize){
		this.windowSize=windowSize;
		window = new SlidingWindow<VecteurFiltrable<Double>>(windowSize);
		variables = null;
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
		if (variables != null) {
			System.out.println("variables non null");
			Iterator<DescriptiveStatistics> d = variables.iterator();
			while(a.hasNext() && d.hasNext()){
				d.next().addValue(a.next());
				System.out.println("ajoute les donnees");
			}
			window.add(v);
			update();
		}
		else {
			System.out.println("cree variabless");
			this.variables = new ArrayList<DescriptiveStatistics>(toAdd.size());
			for(int i=0;i<toAdd.size();i++){
				System.out.println("cree variable");
				variables.add(new DescriptiveStatistics(windowSize));
			}
			this.add(v);
		}
	}
	
}

	