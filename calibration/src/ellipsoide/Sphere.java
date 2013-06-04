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
	private double longitude;
	private double latitude;
	private double radius;
	private VecteurFiltrable<Double> center;
	private List<VecteurFiltrable<Double>> lvector;
	protected List<Zone> lzone;
	final double erreur = 20.0;
	private AffichSphere affichage;

	/**
	 * Create the Sphere and define the number of zones
	 * 
	 * @param longitude
	 *            this is the longitude step of 2PI rad
	 * @param latitude
	 *            this is the latitude step of PI rad
	 */
	public Sphere(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		center = new Vecteur(0, 0, 0);
		radius = 0;
		affichage = new AffichSphere(this);
		lvector = new ArrayList<VecteurFiltrable<Double>>();
		lzone = new ArrayList<Zone>();
		create_zone();
	}
	public AffichSphere getAffichage(){
		return affichage;
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
	 */
	public void update(double radius, VecteurFiltrable<Double> newcenter,
			VecteurFiltrable<Double> v) {
		lvector.add(v);
		if ((Math.abs(center.getX() - newcenter.getX()) > erreur)
				|| (Math.abs(center.getY() - newcenter.getY()) > erreur)
				|| (Math.abs(center.getZ() - newcenter.getZ()) > erreur)) {
			this.radius = radius;
			this.center = newcenter;
			System.out.println("maj sphere");
			update_all_zone();
			affichage.majZone();

		} else {
			update(v);
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
	private void create_zone() {
		for (int i = 0; i < longitude; i++) {
			for (int j = 0; j < latitude; j++) {
				lzone.add(new Zone((Math.PI / latitude) * j - Math.PI / 2,
						(Math.PI / latitude) * (j + 1) - Math.PI / 2,
						((2 * Math.PI) / longitude) * i - Math.PI,
						((2 * Math.PI) / longitude) * (i + 1) - Math.PI));
			}
		}
	}

	/**
	 * method that update all the zones with all vectors
	 */
	private void update_all_zone() {
		Zone ztemp;
		ListIterator<Zone> j = lzone.listIterator();
		ListIterator<VecteurFiltrable<Double>> i;

		while (j.hasNext()) {
			ztemp = j.next();
			ztemp.maj_list_contour(radius);
			ztemp.reset();
			i = lvector.listIterator();
			while (i.hasNext()) {
				ztemp.is_in(i.next(), center);
			}

		}
	}
	/**
	 * method that update the zones with only one vector
	 * @param v
	 */
	protected void update(VecteurFiltrable<Double> v) {
		boolean b = false;
		ListIterator<Zone> j = lzone.listIterator();
		while (b == false && j.hasNext()) {
			b = j.next().is_in(v, center);
		}
	}
}
