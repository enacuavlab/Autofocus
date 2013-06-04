package ellipsoide;

import java.awt.Dimension;
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

	
	private int transform(int x) {
		float res;
		res = ((float)x/(float)s.getRayon()) * 100 + 400;
		return (int)res;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.clearRect(0, 0, 800, 800);
		List<Point2D> points;
		int n = zones.get(1).getListContour().size();
		int xPoints[] = new int[n];
		int yPoints[] = new int[n];
		int i = 0;
		for (Zone z : zones) {
			i = 0;
			points = z.getListContour();
			for (Point2D p : points) {
				xPoints[i] = transform((int)p.getX());
				yPoints[i] = transform((int)p.getY());
				//System.out.println("test : " + s.getRayon() + " -> " + xPoints[i] + " : " + yPoints[i]);
				i++;
			}
			g.drawPolygon(xPoints, yPoints, n);
		}
	}

	public AffichSphere(Sphere s) {
		setPreferredSize(new Dimension(800, 800));
		setBackground(java.awt.Color.RED);
		this.s = s;
		majZone();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
