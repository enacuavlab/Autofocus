package filtre;

import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ellipsoide.Ellipsoide;

import common.TypeCalibration;
import data.Vecteur;

public class Filter {

	private ArrayList<DescriptiveStatistics> variables;
	private int windowSize;
	private SlidingWindow<VecteurFiltrable<Double>> window;
	private int noiseThreshold;
	private Ellipsoide ellipse = new Ellipsoide();

	/**Creates a filter with fixed window size which filter the
	 * type of calibration given in parameter
	 * @param windowSize
	 * @param t
	 */
	public Filter(int windowSize,TypeCalibration t) {
		if (t.equals(TypeCalibration.ACCELEROMETER)){
			noiseThreshold = 3;
		}
		if(t.equals(TypeCalibration.MAGNETOMETER)){
			noiseThreshold = 10;
		}
		
		this.windowSize = windowSize;
		window = new SlidingWindow<VecteurFiltrable<Double>>(windowSize);
		variables = null;
	}

	/** creates a default filter without type and a window size of 40
	 */
	public Filter() {
		this.windowSize = 40;
	}

	/**updates the window of the filter with the new vector given as argument
	 * not public use add instead
	 * @param v
	 */
	private void update(VecteurFiltrable<Double> v) {
		boolean valable = true;
		for (DescriptiveStatistics e : variables) {
			System.out.println("std : " + e.getStandardDeviation());
			valable = (e.getStandardDeviation() < noiseThreshold) && valable;
		}
		if (valable) v.setTrue(); else v.setFalse();   
	}

	/**add the vector building the correct window if not already built and
	 * updates the window
	 * @param v
	 */
	public void add(VecteurFiltrable<Double> v) {
		Collection<Double> toAdd = v.setArray();
		Iterator<Double> a = toAdd.iterator();
		if (variables != null) {
			//System.out.println("variables non null");
			Iterator<DescriptiveStatistics> d = variables.iterator();
			while (a.hasNext() && d.hasNext()) {
				d.next().addValue(a.next());
				//System.out.println("ajoute les donnees");
			}
			window.add(v);
			Vecteur[] b = new Vecteur[window.size()];
			b = window.toArray(b);
			if(b[window.size()/2].getState()) {
				ellipse.add(b[window.size()/2]);
			}
			ellipse.print();
			update(v);
		} else {
			//System.out.println("cree variabless");
			this.variables = new ArrayList<DescriptiveStatistics>(toAdd.size());
			for (int i = 0; i < toAdd.size(); i++) {
				//System.out.println("cree variable");
				variables.add(new DescriptiveStatistics(windowSize));
			}
			this.add(v);
		}
	}

}
