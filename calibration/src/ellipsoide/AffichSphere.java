package ellipsoide;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

public class AffichSphere extends JPanel {

	private Sphere s;
	private List<Zone> zones;

	public void majZone() {
		zones = s.getZones();
		this.repaint();
	}

	public void affiche() {
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		List<Point2D> points;
		int n = zones.get(1).getListContour().size();
		int xPoints[] = new int[n];
		int yPoints[] = new int[n];
		int i = 0;
		for (Zone z : zones) {
			i = 0;
			points = z.getListContour();
			for (Point2D p : points) {
				xPoints[i] = (int) p.getX();
				yPoints[i] = (int) p.getY();
				i++;
			}
			g.drawPolygon(xPoints, yPoints, n);
		}
	}

	public AffichSphere(Sphere s) {
		this.s = s;
		majZone();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
