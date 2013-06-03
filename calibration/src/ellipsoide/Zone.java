package ellipsoide;

import data.Vecteur;

/**
 * class which is counting the number of vectors in the right field of the
 * ellipse. An ellipse is built of different zones. each one must be colored
 * agreeing with the number of vectors it contains
 * 
 * @author GERVAIS florent
 */
public class Zone {
	private double lat_angle_high;//varie entre -PI/2 et + PI/2
	private double lat_angle_low;
	private double long_angle_begin;// varie entre -PI et +PI
	private double long_angle_end;
	private int nb_points;

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
	public Zone(double lat_angle_high, double lat_angle_low,
			double long_angle_begin, double long_angle_end) {
		this.lat_angle_low = lat_angle_low;
		this.lat_angle_high = lat_angle_high;
		this.long_angle_begin = long_angle_begin;
		this.long_angle_end = long_angle_end;
		nb_points = 0;
	}

	/**
	 * this method reset the zone
	 */
	public void reset() {
		nb_points = 0;
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
	public boolean is_in(Vecteur v, Vecteur center) {
		if (is_in_lat(v.getX(), v.getY(), v.getZ(), center.getX(),
				center.getY(), center.getZ())
				&& is_in_long(v.getX(), v.getY(), center.getX(), center.getY())) {
			nb_points += 1;

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
	protected boolean is_in_long(double x_coord, double y_coord,
			double x_center, double y_center) {
		double alpha;
		double xc_x = x_coord - x_center;
		double yc_y = y_coord - y_center;
		double den1 = Math.sqrt(Math.pow(xc_x, 2) + Math.pow(yc_y, 2));
		if (den1 != 0) {
			if (xc_x >= 0 && yc_y >= 0) {
				alpha = Math.asin(xc_x / den1);
				System.out.println(alpha + "number 1");
				if (alpha >= long_angle_begin && alpha <= long_angle_end) {
					System.out.println(alpha + "number 1");
					return true;
				} else {
					return false;
				}
			}
			if (xc_x <= 0 && yc_y >= 0) {
				alpha = Math.asin(-xc_x / den1) + (Math.PI / 2);
				System.out.println(alpha + "number 2");
				if (alpha >= long_angle_begin && alpha <= long_angle_end) {
					System.out.println(alpha + "number 2");
					return true;
				} else {
					return false;
				}
			}

			if (xc_x >= 0 && yc_y <= 0) {
				alpha = Math.asin(xc_x / den1) - (Math.PI / 2);
				System.out.println(alpha + "number 3");
				if (alpha >= long_angle_begin && alpha <= long_angle_end) {
					System.out.println(alpha + "number 3");
					return true;
				} else {
					return false;
				}
			}
			if (xc_x <= 0 && yc_y <= 0) {
				alpha = Math.asin(-xc_x / den1) - (Math.PI);
				System.out.println(alpha + "number 4");
				if (alpha >= long_angle_begin && alpha <= long_angle_end) {
					System.out.println(alpha + "number 4");
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
	public boolean is_in_lat(double x_coord, double y_coord, double z_coord,
			double x_center, double y_center, double z_center) {
		double alpha;
		double den = Math.sqrt(Math.pow(x_center - x_coord, 2)
				+ Math.pow(y_center - y_coord, 2));
		double zc_z = z_coord - z_center;
		if (den != 0) {
			alpha = Math.atan(zc_z / den);
			if (alpha <= lat_angle_high && alpha >= lat_angle_low) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
