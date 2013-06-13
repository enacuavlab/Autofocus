/**Package grouping all classes used to filter data*/
package filtre;

import javax.swing.SwingUtilities;

import common.TypeCalibration;
import data.Vecteur;

import ellipsoide.Sphere;

public class FilterSphere extends Filter {

	/**Data of the sphere, max and min of each axis
	 * center, radius and the sphere
	 */
	private Sphere s;

	/**
	 * Creates a filter who plots the vector in a two dimensional window (simple
	 * orthogonal projection on xy)
	 * 
	 * @param windowSize
	 * @param type
	 */
	public FilterSphere(int windowSize, TypeCalibration type) {
		super(windowSize, type);
		s = new Sphere(20, 10, 800);
	}

	/**
	 * Creates a filter who uses the plot given as an argument to plot in a two
	 * dimensional graph (simple orthogonal projection on xy)
	 * 
	 * @param plot
	 * @param windowSize
	 * @param type
	 */
	public FilterSphere(Sphere sphere, int windowSize, TypeCalibration type) {
		super(windowSize, type);
		this.s = sphere;
	}

	/**
	 * Add the vector given in argument to the filter and update the sphere with
	 * new radius and center
	 * 
	 * @param v
	 *            vector to add
	 */
	@Override
	public void add(final VecteurFiltrable<Double> v) {
		final Vecteur a[] = new Vecteur[windowSize];
		super.add(v);
		if (!(window.remainingCapacity() > 0)) {
			SwingUtilities.invokeLater(
					new Runnable() {
						public void run() {
						s.update(rayon, center, window.toArray(a)[0],v);
						}
					});
		}
		// System.out.println(center);
	}

}
