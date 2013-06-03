package ellipsoide;

import data.Vecteur;

public class Zone {
	private double lat_angle_haut;
	private double lat_angle_bas;
	private double long_angle_debut;// varie entre -PI et +PI
	private double long_angle_fin;
	private int nb_points;

	public Zone(double lat_angle_haut, double lat_angle_bas,
			double long_angle_debut, double long_angle_fin) {
		this.lat_angle_bas = lat_angle_bas;
		this.lat_angle_haut = lat_angle_haut;
		this.long_angle_debut = long_angle_debut;
		this.long_angle_fin = long_angle_fin;
		nb_points=0;
	}
	
	public void reset(){
		nb_points=0;
	}

	public boolean est_dans_long(double x_coord, double y_coord,
			double x_center, double y_center) {
		double alpha = 0;
		double xc_x = x_center - x_coord;
		double yc_y = y_center - y_coord;
		double den1 = Math.sqrt(Math.pow(xc_x, 2) + Math.pow(yc_y, 2));
		if (den1 != 0) {
			if (xc_x >= 0 && yc_y >= 0) {
				alpha = Math.asin(xc_x / den1);
				System.out.println();
			}
			if (xc_x <= 0 && yc_y >= 0) {
				alpha = Math.asin(-xc_x / den1) + (Math.PI / 2);
			}
			if (xc_x >= 0 && yc_y <= 0) {
				alpha = Math.asin(xc_x / den1) - (Math.PI / 2);
			}
			if (xc_x <= 0 && yc_y <= 0) {
				alpha = Math.asin(-xc_x / den1) - (Math.PI);
			}

			if (alpha >= long_angle_debut && alpha <= long_angle_fin) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public boolean est_dans_lat(double x_coord, double y_coord, double z_coord,
			double x_center, double y_center, double z_center) {
		double alpha;
		double den = Math.sqrt(Math.pow(x_center - x_coord, 2)
				+ Math.pow(y_center - y_coord, 2));
		double zc_z = z_center - z_coord;
		if (den != 0) {
			alpha = Math.atan(zc_z / den);
			if (alpha <= lat_angle_haut && alpha >= lat_angle_bas) {
				return true;
			} else {
				return false;
			}
		}
		else { return false; }
	}
}
