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
	private double latAngleHigh;// varie entre -PI/2 et + PI/2
	private double latAngleLow;
	private double longAngleBegin;// varie entre -PI et +PI
	private double longAngleEnd;
	private double surface;
	private int nbPointsByLine = 4;
	private List<Point2D> listContour;
	private double surfaceSphere;
	private Density density;
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
		density=new Density();
		surface = 1;
		surfaceSphere=1;
	}

	/**
	 * this method reset the zone
	 */
	public void reset() {
		density.reset();
	}
	/**
	 * return the density
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
	public boolean is_in(VecteurFiltrable<Double> v,
			VecteurFiltrable<Double> center) {
		if (is_in_lat(v.getX(), v.getY(), v.getZ(), center.getX(),
				center.getY(), center.getZ())
				&& is_in_long(v.getX(), v.getY(), center.getX(), center.getY())) {
			density.updateDensity(surfaceSphere,surface);
			// System.out.println("nb = "+ nb_points +" "+
			// this.lat_angle_low+" "+this.lat_angle_high+" "+this.long_angle_begin+" "+this.long_angle_end);

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
			if (xc_x >= 0 && yc_y >= 0) {
				alpha = Math.asin(xc_x / den1);
				if (alpha >= longAngleBegin && alpha <= longAngleEnd) {
					// System.out.println(alpha + "number 1");
					return true;
				} else {
					return false;
				}
			}
			if (xc_x <= 0 && yc_y >= 0) {
				alpha = Math.asin(-xc_x / den1) + (Math.PI / 2);
				if (alpha >= longAngleBegin && alpha <= longAngleEnd) {
					// System.out.println(alpha + "number 2");
					return true;
				} else {
					return false;
				}
			}

			if (xc_x >= 0 && yc_y <= 0) {
				alpha = Math.asin(xc_x / den1) - (Math.PI / 2);
				if (alpha >= longAngleBegin && alpha <= longAngleEnd) {
					// System.out.println(alpha + "number 3");
					return true;
				} else {
					return false;
				}
			}
			if (xc_x <= 0 && yc_y <= 0) {
				alpha = Math.asin(-xc_x / den1) - (Math.PI);
				if (alpha >= longAngleBegin && alpha <= longAngleEnd) {
					// System.out.println(alpha + "number 4");
					return true;
				} else {
					return false;
				}
			}
			return false;

		} else
			return false;
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
			if (alpha <= latAngleHigh && alpha >= latAngleLow) {
				return true;
			} else {
				return false;
			}
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
	public void calculateSurface(double radius,double surfaceS){
		surfaceSphere=surfaceS;
		System.out.println("surfaceSphere "+ surfaceSphere );
		surface = Math.pow(radius,2)*(latAngleHigh-latAngleLow)*Math.abs(Math.sin(longAngleEnd)-Math.sin(longAngleBegin));

	}
	
	public String toString() {
		ListIterator<Point2D> j = listContour.listIterator();
		String str = "";
		while (j.hasNext()) {
			str += " " + j.next();
		}
		return str;
	}
	
	public static void main(String[] args) {
		// test de l'angle theta du repère sphérique
		Zone zone1 = new Zone( 0,Math.PI/2, 0, Math.PI / 2);
		if (zone1.is_in_long(3, 2, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone2 = new Zone(-Math.PI/2,0, -Math.PI / 2, 0);
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
		Zone zone3 = new Zone(0,Math.PI /4,  -Math.PI, -Math.PI / 2);
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
		Zone zone4 = new Zone(0,Math.PI /4, Math.PI / 2, Math.PI);
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
		if (zone1.is_in_lat(16, 15,12, 1, 1,1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone1.is_in_lat(16, 15,-12, 1, 1,1)) { // ne doit pas etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_lat(16, 15,-12, 1, 1,1)) { // ne doit pas etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
	}

}
