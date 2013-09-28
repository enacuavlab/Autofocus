/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import data.Vecteur;
import filtre.VecteurFiltrable;

/**
 * this class represent the sphere
 * 
 * @author GERVAIS florent
 * 
 */
public class Sphere {
	/**
	 * The longitude of the sphere
	 */
	private double longitude;
	/**
	 * The latitude of the sphere
	 */
	private double latitude;
	/**
	 * The radius of the sphere
	 */
	private double radius;
	/**
	 * The center of the sphere
	 */
	private VecteurFiltrable<Double> center;
	/**
	 * List of the vectors
	 */
	private List<VecteurFiltrable<Double>> lvector;
	/**
	 * List of the zones
	 */
	private List<Zone> lzone;
	/**
	 * error of the previous center of the sphere tolerated
	 */
	private final double error = 50.0;
	/**
	 * The display of the sphere
	 */
	private AffichSphere affichage;
	/**
	 * The surface of the sphere used to calculate the density accross the
	 * sphere
	 */
	private double surfaceSphere;
	/**
	 * @param nbMaxPoints
	 *            number of points on the surface of the sphere to get the right
	 *            calibration
	 */
	private int nbPointsMax;
	/**
	 * Current zone
	 */
	private Zone zoneCourante;

	/**
	 * Create the Sphere and define the number of zones
	 * 
	 * @param longitude
	 *            this is the longitude step of 2PI rad
	 * @param latitude
	 *            this is the latitude step of PI rad
	 * @param i
	 */
	public Sphere(double longitude, double latitude, int nbPointsMax) {
		this.nbPointsMax=nbPointsMax;
		this.longitude = longitude;
		this.latitude = latitude;
		center = new Vecteur(0, 0, 0);
		radius = 0;
		surfaceSphere = 0;
		lvector = new ArrayList<VecteurFiltrable<Double>>();
		lzone = new ArrayList<Zone>();
		createZone();
		ListIterator<Zone> j = lzone.listIterator();
		zoneCourante = j.next();
	}

	/**
	 * getter of the display
	 * 
	 * @return the display
	 */
	public AffichSphere getAffichage() {
		return affichage;
	}

	/**
	 * Returns the zone in which the user is plotting
	 * 
	 */
	public Zone getZoneCurrent() {
		return zoneCourante;
	}

	/**
	 * method called each time a new vector is added
	 * 
	 * @param radius
	 *            radius of the sphere
	 * @param newcenter
	 *            center of the sphere
	 * @param v
	 *            new vector added
	 * @param vcourant
	 *            current vector
	 */
	public void update(double radius, VecteurFiltrable<Double> newcenter,
			VecteurFiltrable<Double> v, VecteurFiltrable<Double> vcourant) {
		if (v.isCorrect()) {
			lvector.add(v);
			if ((Math.abs(center.getX() - newcenter.getX()) > error)
					|| (Math.abs(center.getY() - newcenter.getY()) > error)
					|| (Math.abs(center.getZ() - newcenter.getZ()) > error)
					|| (Math.abs(this.radius - radius) > error)) {
				this.radius = radius;
				this.center = newcenter;
				this.surfaceSphere = 4 * Math.PI * Math.pow(radius, 2);
				updateAllZone();
				updateVecCourant(vcourant);
				affichage.majZone();
			} else {
				update(v);
				updateVecCourant(vcourant);
				affichage.affiche();
			}
		} else {
			updateVecCourant(vcourant);
			affichage.affiche();
		}
	}

	/**
	 * method implemented to get back the list of zones
	 * 
	 * @return return the ArrayList of zone
	 */
	public List<Zone> getZones() {
		return lzone;
	}

	/**
	 * method that create all the zones
	 */
	private void createZone() {
		for (int i = 0; i < longitude; i++) {
			for (int j = 1; j < latitude - 1; j++) {
				lzone.add(new Zone((Math.PI / latitude) * ((double) j)
						- Math.PI / 2.0, (Math.PI / latitude)
						* ((double) (j + 1)) - Math.PI / 2.0,
						((2.0 * Math.PI) / longitude) * ((double) i) - Math.PI,
						((2.0 * Math.PI) / longitude) * ((double) (i + 1))
								- Math.PI,nbPointsMax));
			}
		}
		lzone.add(new Zone((Math.PI / latitude) * ((double) (latitude - 1))
				- Math.PI / 2.0, Math.PI / 2.0, -Math.PI, Math.PI,nbPointsMax));
		lzone.add(new Zone(-Math.PI / 2.0,
				(Math.PI / latitude) - Math.PI / 2.0, -Math.PI, Math.PI,nbPointsMax));
	}

	/**
	 * method that update all the zones with all vectors
	 */
	private void updateAllZone() {
		Zone ztemp;
		ListIterator<Zone> j = lzone.listIterator();
		// ListIterator<VecteurFiltrable<Double>> i;

		while (j.hasNext()) {
			ztemp = j.next();
			ztemp.maj_list_contour(radius);
			ztemp.calculateSurface(radius, surfaceSphere);
			ztemp.reset();
			ListIterator<VecteurFiltrable<Double>> i = lvector.listIterator();
			while (i.hasNext()) {
				ztemp.isIn(i.next(), center);
			}

		}
	}

	/**
	 * method that update the zones with only one vector
	 * 
	 * @param v
	 */
	protected void update(VecteurFiltrable<Double> v) {
		boolean b = false;
		ListIterator<Zone> j = lzone.listIterator();
		Zone temp;
		while (!b && j.hasNext()) {
			temp = j.next();
			b = temp.isIn(v, center);
		}
	}

	/**
	 * Update the currant zone thanks to the last vector received
	 * 
	 * @param vcourant
	 *            last vector sent by the IMU
	 */
	protected void updateVecCourant(VecteurFiltrable<Double> vcourant) {
		boolean b = false;
		ListIterator<Zone> j = lzone.listIterator();
		Zone temp;
		while (!b && j.hasNext()) {
			temp = j.next();
			if (b = temp.updateZoneCourante(vcourant, center)) {
				zoneCourante = temp;
			}
		}
	}

	/**
	 * Function for display
	 * 
	 * @return radius of the sphere
	 */
	protected int getRayon() {
		return (int) radius;
	}
	
	/** Display setter
	 * 
	 */
	public void setDisplay(AffichSphere as) {
		this.affichage = as;
	}
	/**
	 * Test functions of the class
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		Sphere s = new Sphere(20, 10,800);
		Vecteur center = new Vecteur(5, 10, 15);
		Vecteur v = new Vecteur(8, 20, 21);
		s.update(10.5, center, v, v);
		ListIterator<Zone> j = s.getZones().listIterator();
		while (j.hasNext()) {
			if (j.next().getDensity().getColor() > 0) {
				System.out.println("point rentré");
			}
		}

	}

}
