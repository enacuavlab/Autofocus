/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import data.IVector;
import data.Vector;

/**
 * this class represent the sphere
 * 
 * @author GERVAIS florent
 * 
 */
public class Sphere {

	private static Logger logger = Logger.getLogger(Sphere.class.getName());

	/**
	 * error of the previous center of the sphere tolerated
	 */
	private final static double ERROR_TOLERATED = 50.0;

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
	private IVector<Double> center;
	/**
	 * List of the zones
	 */
	private List<Zone> zoneList;

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
	 * @param nbPointsMax
	 *            the max number of points on the sphere
	 */
	public Sphere(double longitude, double latitude, int nbPointsMax) {
		this.nbPointsMax = nbPointsMax;
		this.longitude = longitude;
		this.latitude = latitude;
		center = new Vector(0, 0, 0);
		radius = 0;
		surfaceSphere = 0;
		zoneList = new ArrayList<Zone>();
		createAllZones();
		ListIterator<Zone> j = zoneList.listIterator();
		zoneCourante = j.next();
	}

	public void clean() {
		center = new Vector(0, 0, 0);
		radius = 0;
		surfaceSphere = 0;
		zoneList = new ArrayList<Zone>();
		createAllZones();
		ListIterator<Zone> j = zoneList.listIterator();
		zoneCourante = j.next();
	}

	/**
	 * Returns the zone in which the user is plotting
	 * 
	 */
	public Zone getCurrentZone() {
		return zoneCourante;
	}

	/**
	 * method called each time a new vector is added
	 * 
	 * @param p_radius
	 *            radius of the sphere
	 * @param newcenter
	 *            center of the sphere
	 * @param vectorToAdd
	 *            current vector
	 */
	public void update(final double p_radius, final IVector<Double> newcenter,
			final IVector<Double> vectorToAdd) {
		boolean valuesHaveChanged = false;
		if (vectorToAdd.isCorrect()) {
			// Si le centre ou le rayon ont suffisamment changé, on met à jour
			// les informations sur la sphere (rayon, centre, surface...)
			if ((Math.abs(center.getX() - newcenter.getX()) > ERROR_TOLERATED)
					|| (Math.abs(center.getY() - newcenter.getY()) > ERROR_TOLERATED)
					|| (Math.abs(center.getZ() - newcenter.getZ()) > ERROR_TOLERATED)
					|| (Math.abs(this.radius - p_radius) > ERROR_TOLERATED)) {
				this.radius = p_radius;
				this.center = newcenter;
				this.surfaceSphere = 4 * Math.PI * Math.pow(this.radius, 2);
				valuesHaveChanged = true;
			}
		}
		updateAllZones(vectorToAdd, valuesHaveChanged);
	}

	/**
	 * method implemented to get back the list of zones
	 * 
	 * @return return the ArrayList of zone
	 */
	public List<Zone> getZones() {
		return zoneList;
	}

	/**
	 * method that create all the zones
	 */
	private void createAllZones() {
		double long_angle_begin;
		double long_angle_end;
		double lat_angle_low;
		double lat_angle_high;

		for (int i = 0; i < longitude; i++) {
			long_angle_begin = ((2.0 * Math.PI) / longitude) * i - Math.PI;
			long_angle_end = ((2.0 * Math.PI) / longitude) * (i + 1) - Math.PI;

			for (int j = 1; j < latitude - 1; j++) {
				lat_angle_low = (Math.PI / latitude) * j - Math.PI / 2.0;
				lat_angle_high = (Math.PI / latitude) * (j + 1) - Math.PI / 2.0;

				zoneList.add(new Zone(lat_angle_low, lat_angle_high,
						long_angle_begin, long_angle_end, nbPointsMax));
			}
		}

		lat_angle_low = (Math.PI / latitude) * ((double) (latitude - 1))
				- Math.PI / 2.0;
		lat_angle_high = (Math.PI / latitude) - Math.PI / 2.0;

		zoneList.add(new Zone(lat_angle_low, Math.PI / 2.0, -Math.PI, Math.PI,
				nbPointsMax));
		zoneList.add(new Zone(-Math.PI / 2.0, lat_angle_high, -Math.PI,
				Math.PI, nbPointsMax));
	}

	/**
	 * method that update all the zones with all vectors
	 */
	private void updateAllZones(IVector<Double> vectorToAdd,
			boolean valuesHaveChanged) {
		Zone ztemp;
		ListIterator<Zone> zoneListIt = zoneList.listIterator();

		while (zoneListIt.hasNext()) {
			ztemp = zoneListIt.next();

			if (valuesHaveChanged) {
				ztemp.majListContour(radius);
				ztemp.calculateSurface(radius, surfaceSphere);
			}

			boolean vectorIsInZone = ztemp.containsVector(vectorToAdd, center);

			if (vectorIsInZone && vectorToAdd.isCorrect()) {
				ztemp.updateDensity();
				zoneCourante = ztemp;
			}
		}
	}

	/**
	 * Function for display
	 * 
	 * @return radius of the sphere
	 */
	public int getRayon() {
		return (int) radius;
	}

}
