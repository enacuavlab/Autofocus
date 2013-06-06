package filtre;

import common.TypeCalibration;

import data.Vecteur;
import ellipsoide.AffichAccel;
import ellipsoide.Sphere;

public class FilterAccel extends Filter {

		private int maxX = 0;
		private int minX = 0;
		private int maxY = 0;
		private int minY = 0;
		private int maxZ = 0;
		private int minZ = 0;
		private int rayon = 0;
		private Vecteur center = new Vecteur(0, 0, 0);
		
		private AffichAccel affAccel;
		private int nbCorrectVect;
		private int nbWrongVect;
		private int thresholdOK;
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
		public void add(VecteurFiltrable<Double> v) {
			Vecteur a[] = new Vecteur[windowSize];
			super.add(v);
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
			if (!(window.remainingCapacity() > 0)) {
				affAccel.update(rayon, center, window.toArray(a)[windowSize - 1],v,nbCorrectVect);
				if ((nbWrongVect> thresholdWrong)||(nbCorrectVect > thresholdOK)) {
					affAccel.changedStates();
					nbWrongVect=0;
					nbCorrectVect=0;
				}
			}
			// System.out.println(center);
		}

	}


