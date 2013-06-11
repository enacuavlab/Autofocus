/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import filtre.VecteurFiltrable;

/**
 * class which is counting the number of vectors in the right field of the
 * ellipse. An ellipse is built of different zones. each one must be colored
 * agreeing with the number of vectors it contains
 * 
 * @author GERVAIS florent
 */
public class Zone {
	/**
	 * Latitude Angle High: The latitude angle defining the highest bound of
	 * zone variates between -PI/2 and + PI/2
	 */
	private double latAngleHigh;
	/**
	 * Latitude Angle Low : The latitude angle defining the lowest bound of zone
	 */
	private double latAngleLow;
	/**
	 * Longitude Angle Begin : The western angle defining the zone
	 */
	private double longAngleBegin;// varie entre -PI et +PI
	/**
	 * Longitude Angle End : The eastern angle defining the zone
	 */
	private double longAngleEnd;
	/**
	 * The surface of the zone
	 */
	private double surface;
	/**
	 * The number of points on each boundary of the zone. More or less the
	 * resolution of the sphere
	 */
	private int nbPointsByLine = 4;
	/**
	 * List of the points defining the boundaries of the zone
	 */
	private List<Point2D> listContour;
	/**
	 * The surface of the Sphere
	 */
	private double surfaceSphere;
	/**
	 * The density of points over the zone
	 */
	private Density density;

	/**
	 * Give the list of the points defining the boundaries
	 * 
	 * @return listContour
	 */
	public List<Point2D> getListContour() {
		return listContour;
	}

	/**
	 * this constructor create the zone
	 * 
	 * @param lat_angle_high
	 *            maximum latitude delimiting the zone
	 * @param lat_angle_low
	 *            minimum latitude delimiting the zone
	 * @param long_angle_begin
	 *            minimum longitude delimiting the zone
	 * @param long_angle_end
	 *            maximum longitude delimiting the zone
	 */
	public Zone(double lat_angle_low, double lat_angle_high,
			double long_angle_begin, double long_angle_end) {
		this.latAngleLow = lat_angle_low;
		this.latAngleHigh = lat_angle_high;
		this.longAngleBegin = long_angle_begin;
		this.longAngleEnd = long_angle_end;
		listContour = new ArrayList<Point2D>();
		density = new Density();
		surface = 1;
		surfaceSphere = 1;
	}

	/**
	 * this method reset the zone
	 */
	public void reset() {
		density.reset();
	}

	/**
	 * Thanks to that method we can find the currant zone in order to print the
	 * current zone
	 * 
	 * @param v
	 *            the currant vector that is actually the last one received by
	 *            the IMU
	 * @param center
	 *            center of the sphere
	 * @return boolean true when the vector is in this zone
	 */
	public boolean updateZoneCourante(VecteurFiltrable<Double> v,
			VecteurFiltrable<Double> center) {
		if (is_in_lat(v.getX(), v.getY(), v.getZ(), center.getX(),
				center.getY(), center.getZ())
				&& is_in_long(v.getX(), v.getY(), center.getX(), center.getY())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * return the density
	 * 
	 * @return return
	 */
	public Density getDensity() {
		return density;

	}

	/**
	 * this method return a boolean : true means that the point is in the area
	 * 
	 * @param v
	 *            vector that is tested to be in the area
	 * @param center
	 *            center of the ellipsoid
	 * @return
	 */
	public boolean isIn(VecteurFiltrable<Double> v,
			VecteurFiltrable<Double> center) {
		if (is_in_lat(v.getX(), v.getY(), v.getZ(), center.getX(),
				center.getY(), center.getZ())
				&& is_in_long(v.getX(), v.getY(), center.getX(), center.getY())) {
			if (v.isCorrect()) {
				density.updateDensity(surfaceSphere, surface);
			}
			return true;
		} else
			return false;
	}

	/**
	 * this method return true if the vector is in the right longitude area
	 * 
	 * @param x_coord
	 *            x vector coordinate
	 * @param y_coord
	 *            y vector coordinate
	 * @param x_center
	 *            x center coordinate of the ellipsoid
	 * @param y_center
	 *            y center coordinate of the ellipsoid
	 * @return
	 */
	private boolean is_in_long(double x_coord, double y_coord, double x_center,
			double y_center) {
		double alpha;
		double xc_x = x_coord - x_center;
		double yc_y = y_coord - y_center;
		double den1 = Math.sqrt(Math.pow(xc_x, 2) + Math.pow(yc_y, 2));
		if (den1 != 0) {
			alpha = Math.acos(xc_x / den1)
					* Math.signum(Math.asin(yc_y / den1));
			return (alpha > longAngleBegin && alpha < longAngleEnd);
		} else{
			return false; }
	}

	/**
	 * this method return true if the vector is in the right latitude area
	 * 
	 * @param x_coord
	 *            x vector coordinate
	 * @param y_coord
	 *            y vector coordinate
	 * @param z_coord
	 *            z vector coordinate
	 * @param x_center
	 *            x center coordinate of the ellipsoid
	 * @param y_center
	 *            y center coordinate of the ellipsoid
	 * @param z_center
	 *            z center coordinate of the ellipsoids
	 * @return
	 */
	private boolean is_in_lat(double x_coord, double y_coord, double z_coord,
			double x_center, double y_center, double z_center) {
		double alpha;
		double den = Math.sqrt(Math.pow(x_center - x_coord, 2)
				+ Math.pow(y_center - y_coord, 2));
		double zc_z = z_coord - z_center;
		if (den != 0) {
			alpha = Math.atan(zc_z / den);
			return (alpha < latAngleHigh && alpha > latAngleLow);
		} else {
			return false;
		}
	}

	/**
	 * this function convert the 3D coordinate into 2D coordinate agreeing to
	 * the Mollweide projection
	 * 
	 * @param radius
	 *            radius of the sphere needed to represent the 2D points
	 */
	public void maj_list_contour(double rad) {
		listContour.clear();
		double radius = rad;
		double x;
		double racineDeux = Math.sqrt(2);
		double y = radius * Math.sqrt(2) * Math.sin(latAngleLow);
		double step_longitude = (longAngleEnd - longAngleBegin)
				/ nbPointsByLine;
		double step_latitude = (latAngleHigh - latAngleLow) / nbPointsByLine;
		double temp;
		for (int i = 0; i < nbPointsByLine; i++) {
			listContour.add(new Point2D.Double(
					(radius * 2 * racineDeux / Math.PI)
							* (longAngleBegin + step_longitude * i)
							* Math.cos(latAngleLow), y));
		}
		x = radius * (2 * Math.sqrt(2) / Math.PI) * (longAngleEnd);
		for (int i = 0; i < nbPointsByLine; i++) {
			temp = i * step_latitude;
			listContour.add(new Point2D.Double(
					x * Math.cos(latAngleLow + temp), radius * racineDeux
							* Math.sin(latAngleLow + temp)));
		}
		y = radius * Math.sqrt(2) * Math.sin(latAngleHigh);
		for (int i = 0; i < nbPointsByLine; i++) {
			listContour.add(new Point2D.Double(
					(radius * 2 * racineDeux / Math.PI)
							* (longAngleEnd - step_longitude * i)
							* Math.cos(latAngleHigh), y));
		}
		x = radius * (2 * Math.sqrt(2) / Math.PI) * (longAngleBegin);
		for (int i = 0; i < nbPointsByLine; i++) {
			temp = i * step_latitude;
			listContour.add(new Point2D.Double(x
					* Math.cos(latAngleHigh - temp), radius * racineDeux
					* Math.sin(latAngleHigh - temp)));
		}
	}

	/**
	 * this method calculate the surface of the zone and update the surface of
	 * the sphere
	 * 
	 * @param radius
	 *            the actual radius of the sphere
	 * @param surfaceS
	 *            the actual surface of the sphere
	 */
	public void calculateSurface(double radius, double surfaceS) {
		surfaceSphere = surfaceS;
		surface = Math.pow(radius, 2) * (longAngleEnd -longAngleBegin)
				* Math.abs(Math.cos(latAngleHigh+Math.PI/2.0) - Math.cos(latAngleLow+Math.PI/2.0));

			System.out.println(surface + " " + surfaceS);

	}

	/**
	 * @return String
	 */
	public String toString() {
		ListIterator<Point2D> j = listContour.listIterator();
		String str = "";
		while (j.hasNext()) {
			str += " " + j.next();
		}
		return str;
	}

	/**
	 * Test functions
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// test de l'angle theta du repère sphérique
		Zone zone1 = new Zone(0, Math.PI / 2, 0, Math.PI / 2);
		if (zone1.is_in_long(3, 2, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone2 = new Zone(-Math.PI / 2, 0, -Math.PI / 2, 0);
		if (zone1.is_in_long(3, -15, 1, 1)) { // ne doit pas etre dans la
												// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_long(3, -15, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone3 = new Zone(0, Math.PI / 4, -Math.PI, -Math.PI / 2);
		if (zone1.is_in_long(-9, -15, 1, 1)) { // ne doit pas etre dans la
												// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_long(-9, -15, 1, 1)) { // ne doit pas etre dans la
												// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone3.is_in_long(-9, -15, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone4 = new Zone(0, Math.PI / 4, Math.PI / 2, Math.PI);
		if (zone1.is_in_long(-9, 15, 1, 1)) { // ne doit pas etre dans la
												// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_long(-9, 15, 1, 1)) { // ne doit pas etre dans la
												// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone3.is_in_long(-9, 15, 1, 1)) { // ne doit pas etre dans la
												// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone4.is_in_long(-9, 15, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		// test de l'angle Phi du repère sphérique
		if (zone1.is_in_lat(16, 15, 12, 1, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone1.is_in_lat(16, 15, -12, 1, 1, 1)) { // ne doit pas etre dans la
														// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_lat(16, 15, -12, 1, 1, 1)) { // ne doit pas etre dans la
														// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
	}

}
