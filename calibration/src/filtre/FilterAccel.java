/**Package grouping all classes used to filter data*/
package filtre;

import javax.swing.SwingUtilities;

import common.TypeCalibration;

import data.Vecteur;
import ellipsoide.AffichAccel;

public class FilterAccel extends Filter {

	/**
	 * Maximum and minimum on each directions used to update the sphere
	 * 
	 */
	private VecteurFiltrable<Double> maxX = new Vecteur(0, 0, 0);
	private VecteurFiltrable<Double> minX = new Vecteur(0, 0, 0);
	private VecteurFiltrable<Double> maxY = new Vecteur(0, 0, 0);
	private VecteurFiltrable<Double> minY = new Vecteur(0, 0, 0);
	private VecteurFiltrable<Double> maxZ = new Vecteur(0, 0, 0);
	private VecteurFiltrable<Double> minZ = new Vecteur(0, 0, 0);
	private int rayon = 0;
	/** The center of the sphere */
	private Vecteur center = new Vecteur(0, 0, 0);
	/** The accelerator of the sphere */
	private AffichAccel affAccel;
	/** The number of correct vector added */
	private int nbCorrectVect;
	/**
	 * The number of vector discarded used to reinitialize the progress bar
	 */
	private int nbWrongVect;
	/**
	 * The threshold that trigger the reinitialization of the progress bar
	 */
	private int thresholdOK;
	/**
	 * The threshold that trigger the reinitialization of the progress bar
	 */
	private int thresholdWrong;

	/**total number of vector
	 * 
	 */
	private int nbVec;
	/**
	 * Creates a filter who plots the vector in a two dimensional window (simple
	 * orthogonal projection on xy)
	 * 
	 * @param windowSize
	 * @param type
	 */
	public FilterAccel(int windowSize, TypeCalibration type, int thresholdOK,
			int thresholdWrong, AffichAccel affAccel) {
		super(windowSize, type);
		this.thresholdOK = thresholdOK;
		this.thresholdWrong = thresholdWrong;
		nbCorrectVect = 0;
		nbWrongVect = 0;
		nbVec = 0;
		this.affAccel = affAccel;
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
		if (v.isCorrect()) {
			nbCorrectVect++;
			nbVec++;
			if (v.getX() > maxX.getX())
				maxX = v;
			if (v.getY() > maxY.getY())
				maxY = v;
			if (v.getZ() > maxZ.getZ())
				maxZ = v;
			if (v.getX() < minX.getX())
				minX = v;
			if (v.getY() < minY.getY())
				minY = v;
			if (v.getZ() < minZ.getZ())
				minZ = v;
		} else {
			nbWrongVect++;
			nbVec++;
		}
		if ( nbVec < 500) {
			rayon = (int)(maxZ.getZ());
			center = new Vecteur(maxX.getX(),maxY.getY(),0);
		}
		rayon = (int)(maxX.getX() - minX.getX() > maxY.getY() - minY.getY() ? (maxX
				.getX() - minX.getX() > maxZ.getZ() - minZ.getZ() ? maxX.getX()
				- minX.getX() : maxZ.getZ() - minZ.getZ()) : (maxY.getY()
				- minY.getY() > maxZ.getZ() - minZ.getZ() ? maxY.getY()
				- minY.getY() : maxZ.getZ() - minZ.getZ())
				);
		center = new Vecteur((maxX.getX() + minX.getX()) / 2, (maxY.getY() + minY.getY()) / 2,
				(maxZ.getZ() + minZ.getZ()) / 2);
		if (!(window.remainingCapacity() > 0)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					affAccel.update(rayon, center, window.toArray(a)[0], v,
							nbCorrectVect);
				}
			});
			if ((nbWrongVect > thresholdWrong) || (nbCorrectVect > thresholdOK)) {
				affAccel.changedStates();
				nbWrongVect = 0;
				nbCorrectVect = 0;
			}
		}
		// System.out.println(center);
	}

}
