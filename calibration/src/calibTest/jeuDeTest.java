package calibTest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import data.Vecteur;
import filtre.VecteurFiltrable;

/**
 * class that allows you to test the regression of a sphere
 * 
 * @author florent
 * 
 */
public class jeuDeTest {
	private List<VecteurFiltrable<Double>> listnoised;
	private List<VecteurFiltrable<Double>> listclean;
	Vecteur sensibility;
	Vecteur bias;
	double radius;
	double bruit;

	/**
	 * constructor
	 * 
	 * @param radius
	 *            radius of the sphere you want to create
	 * @param sensibility
	 *            vector sensibility you want to apply to the sphere in order to
	 *            generate test data
	 * @param bias
	 *            vector bias you want to apply to the sphere in order to
	 *            generate test data
	 * @param noise double that defines the level of noise 
	 * @param nb it is the number of points generated;
	 * 
	 */
	public jeuDeTest(double radius, Vecteur sensibility, Vecteur bias,
			double noise,int nb) {
		this.radius = radius;
		this.bias = bias;
		this.sensibility = sensibility;
		this.bruit = noise;
		listclean = new ArrayList<VecteurFiltrable<Double>>();
		listnoised = new ArrayList<VecteurFiltrable<Double>>();
		double tempx;
		double tempy;
		double tempz;
		double theta;
		double phi;
		for (int i = 0; i < nb; i++) {
			theta = myRandomDouble(-Math.PI, Math.PI);
			phi = myRandomDouble(-Math.PI / 2, Math.PI / 2);
			tempx = radius * Math.cos(theta) * Math.cos(phi);
			System.out.println(theta + phi);
			theta = myRandomDouble(-Math.PI, Math.PI);
			phi = myRandomDouble(-Math.PI / 2, Math.PI / 2);
			tempy = radius * Math.sin(theta) * Math.cos(phi);
			System.out.println(theta + phi);
			phi = myRandomDouble(-Math.PI / 2, Math.PI / 2);
			System.out.println(theta + phi);
			tempz = radius * Math.sin(phi);
			listclean.add(new Vecteur(sensibility.getX()
					* (tempx - bias.getX()), sensibility.getY()
					* (tempy - bias.getY()), sensibility.getZ()
					* (tempz - bias.getZ())));
			ListIterator<VecteurFiltrable<Double>> iter = listclean
					.listIterator();
			Vecteur temp;
			while (iter.hasNext()) {
				temp = (Vecteur) iter.next();
				listnoised.add(new Vecteur(temp.getX()
						+ myRandomDouble(-noise, noise), temp.getY()
						+ myRandomDouble(-noise, noise), temp.getZ()
						+ myRandomDouble(-noise, noise)));
			}
		}
	}

	private double myRandomDouble(double min, double max) {
		return (min + Math.random() * (max - min + 1));
	}

	/**
	 * method that return the listNoised
	 * 
	 * @return the list of vector noised
	 */
	public List<VecteurFiltrable<Double>> getListnoised() {
		return listnoised;
	}

	/**
	 * method that return the listClean without noise
	 * 
	 * @return the list of vector clean
	 */
	public List<VecteurFiltrable<Double>> getListclean() {
		return listclean;
	}

	/**
	 * return one of the two list noised or clean
	 * @param b if true listclean if not listnoised
	 */
	public void affiche(boolean b) {
		if (b) {
			ListIterator<VecteurFiltrable<Double>> iter = listclean
					.listIterator();
			Vecteur temp;
				while (iter.hasNext()) {
				temp = (Vecteur) iter.next();
				System.out.println(" [" + temp.getX() + ";" + temp.getY() + ";"
						+ temp.getZ() + "] ");
			}
			System.out.println();
			System.out.println();
			System.out.println();
		} else {
			ListIterator<VecteurFiltrable<Double>> iternoised = listnoised
					.listIterator();
			Vecteur temp;
			//System.out.println("vector noised");
			while (iternoised.hasNext()) {
				temp = (Vecteur) iternoised.next();
				System.out.println(" [" + temp.getX() + ";" + temp.getY() + ";"
					+ temp.getZ() + "] ");
			}
		}

	}

	public static void main(String[] args) {
		double radius = 154;
		Vecteur bias = new Vecteur(251, 209, 243);
		Vecteur sensibility = new Vecteur(30, 32, 38);
		jeuDeTest test = new jeuDeTest(radius, bias, sensibility, 15.0, 1);
		test.affiche(true);
		test.affiche(false);
	}
}
