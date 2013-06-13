/**Package grouping all classes used to filter data*/
package filtre;

import javax.swing.SwingUtilities;

import common.TypeCalibration;

import data.Vecteur;
import ellipsoide.AffichAccel;

public class FilterAccel extends Filter {


		/**The accelerator of the sphere*/
		private AffichAccel affAccel;
		/**The number of correct vector added*/

		private int thresholdOK;
		/**The threshold that trigger the 
		 * reinitialization of the progress bar
		 */
		private int thresholdWrong;

		/**
		 * Creates a filter who plots the vector in a two dimensional window (simple
		 * orthogonal projection on xy)
		 * 
		 * @param windowSize
		 * @param type
		 */
		public FilterAccel(int windowSize, TypeCalibration type,int thresholdOK,int thresholdWrong,AffichAccel affAccel) {
			super(windowSize, type);
			this.thresholdOK=thresholdOK;
			this.thresholdWrong=thresholdWrong;
			nbCorrectVect=0;
			nbWrongVect=0;
			this.affAccel=affAccel;		
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
								affAccel.update(rayon, center, 
										window.toArray(a)[0],
										v,nbCorrectVect);
							}
						});
					if ((nbWrongVect> thresholdWrong)||(nbCorrectVect > thresholdOK)) {
					affAccel.changedStates();
					nbWrongVect=0;
					nbCorrectVect=0;
				}
			}
			// System.out.println(center);
		}

	}


