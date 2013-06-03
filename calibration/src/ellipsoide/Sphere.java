package ellipsoide;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import data.Vecteur;

public class Sphere {
	private double longitude;
	private double latitude;
	private double radius;
	private Vecteur center;
	private List<Vecteur> lvector;
	protected List<Zone> lzone;

	public Sphere(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		lvector = new ArrayList<Vecteur>();
		lzone = new ArrayList<Zone>();
		create_zone();
	}

	public void update(double radius, Vecteur center, Vecteur v) {
		this.radius = radius;
		this.center = center;
		lvector.add(v);
		update_zone();
	}

	public double getRadius() {
		return radius;
	}

	public Vecteur getcenter() {
		return center;
	}

	public List<Zone> getZones() {
		return lzone;
	}

	protected void create_zone() {
		for (int i = 0; i < longitude; i++) {
			for (int j = 0; j < latitude; j++) {
				lzone.add(new Zone((Math.PI / latitude) * j - Math.PI / 2,
						(Math.PI / latitude) * (j + 1) - Math.PI / 2,
						(Math.PI / longitude) * i - Math.PI,
						(Math.PI / longitude) * (i + 1) - Math.PI));
			}
		}
	}

	protected void update_zone() {
		Zone ztemp;
		ListIterator<Zone> j= lzone.listIterator();
		ListIterator<Vecteur> i ;

		while (j.hasNext()) {
			ztemp = j.next();
			ztemp.reset();
			i = lvector.listIterator();
			while (i.hasNext()){
				ztemp.is_in(i.next(), center);
			}

		}
	}
}
