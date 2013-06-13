/**Package grouping all classes used to filter data*/
package filtre;

import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


import common.TypeCalibration;
import data.Vecteur;

/**Mother class of all filter use to discriminates vector
 * according to their noise factor
 */

public class Filter {

	/**type de la calibration
	 * 
	 */
	private TypeCalibration type;
	/**Maximum and minimum on each directions used to update the sphere
	 * 
	 */
	private int maxX = 0;
	private int minX = 0;
	private int maxY = 0;
	private int minY = 0;
	private int maxZ = 0;
	private int minZ = 0;
	protected int rayon = 0;
	/**The center of the sphere*/
	protected Vecteur center = new Vecteur(0, 0, 0);
	protected int nbCorrectVect=0;
	/**The number of vector discarded used 
	 * to reinitialize the progress bar
	 */
	protected int nbWrongVect=0;
	/**The threshold that trigger the 
	 * reinitialization of the progress bar
	 */
	
	/**The array used to store data, generates medium and standard deviation*/
	protected ArrayList<DescriptiveStatistics> variables;
	/**The size of the sliding window of the filter*/
	protected int windowSize;
	/**The sliding window of the filter*/
	protected SlidingWindow<VecteurFiltrable<Double>> window;
	/**The noise threshold of the filter*/
	private double noiseThreshold;

	/**Creates a filter with fixed window size which filter the
	 * type of calibration given in parameter
	 * @param windowSize
	 * @param t
	 */
	public Filter(int windowSize,TypeCalibration t) {
		this.noiseThreshold=100; //start value
		this.type = t;
		this.windowSize = windowSize;
		window = new SlidingWindow<VecteurFiltrable<Double>>(windowSize);
		variables = null;
	}

	/** creates a default filter without type and a window size of 40
	 * debug only
	 */
	@Deprecated
	public Filter() {
		this.windowSize = 40;
	}

	/**updates the window of the filter with the new vector given as argument
	 * not public use add instead
	 * @param v
	 */
	protected boolean update(VecteurFiltrable<Double> v) {
		boolean validite = true;
		for (DescriptiveStatistics e : variables) {
			//System.out.println("std : " + e.getStandardDeviation());
			validite = (e.getStandardDeviation() < noiseThreshold) && validite;
		}
		if (validite != v.isCorrect()) {
			if (validite) v.setTrue();
			else v.setFalse();
			return true;
		}
		else return false;
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
			for (VecteurFiltrable<Double> vec : window){
				update(vec);
			}
			//System.out.println("ajout d'un vecteur");
		} else {
			//System.out.println("cree variabless");
			this.variables = new ArrayList<DescriptiveStatistics>(toAdd.size());
			for (int i = 0; i < toAdd.size(); i++) {
				//System.out.println("cree variable");
				variables.add(new DescriptiveStatistics(windowSize));
			}
			this.add(v);
		}
		if (v.isCorrect()) {
			nbCorrectVect++;
			if (v.getX() > maxX)
				maxX = (int) v.getX();
			if (v.getY() > maxY)
				maxY = (int) v.getY();
			if (v.getZ() > maxZ)
				maxZ = (int) v.getZ();
			if (v.getX() < minX)
				minX = (int) v.getX();
			if (v.getY() < minY)
				minY = (int) v.getY();
			if (v.getZ() < minZ)
				minZ = (int) v.getZ();
		}
		else nbWrongVect++;

		rayon = (maxX - minX > maxY - minY ? (maxX - minX > maxZ - minZ ? maxX
				- minX : maxZ - minZ) : (maxY - minY > maxZ - minZ ? maxY
				- minY : maxZ - minZ));
		center = new Vecteur((maxX + minX) / 2, (maxY + minY) / 2,
				(maxZ + minZ) / 2);
		if (TypeCalibration.ACCELEROMETER.equals(this.type)) {
			noiseThreshold=(float)rayon/100 * 2;
		} else {
			noiseThreshold=(float)rayon/100 * 5;
		}
	}


}
