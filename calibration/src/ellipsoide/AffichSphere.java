/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

/**
 * The class responsible of displaying the sphere
 * @author Alinoé
 *
 */
public class AffichSphere extends JPanel {

	/**
	 * Size of the canvas and translation factor for display
	 */
	private final int height = 800;
	private final int width = 1000;
	private final int translateFactor = 200;
	/**
	 * New stroke used to emphasize the zone on the sphere currently
	 * being plotted
	 */
	private Stroke stroke = new BasicStroke(2f);
	/**
	 * The sphere to display
	 */
	private Sphere s;
	/**
	 * The list of the zones belonging to the sphere.
	 * It allows us to draw each of them at each iteration
	 */
	private List<Zone> zones;

	/**
	 * Recalculates all the zones thanks to sphere
	 */
	public void majZone() {
		zones = s.getZones();
		this.repaint();
	}

	/** 
	 * Just repaints the canvas
	 */
	public void affiche() {
		this.repaint();
	}

	/**
	 * The geometric function used to get the sphere correctly displayed on the canvas
	 * 
	 * @param x the integer to transform into the right form
	 * @return the new value of the integer
	 */
	private int transform(int x) {
		float res;
		res = ((float) x / (float)s.getRayon() ) * 100 + translateFactor;
		return (int) res;
	}

	/** 
	 * The main method, it repaint all the zones according to
	 * the new status of the sphere
	 * 
	 * @param g the graphics given by the swing api
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		//defines the canvas
		Graphics2D g2d = (Graphics2D) g;
		g.clearRect(0, 0, height, width);
		List<Point2D> points;
		//useful store structure
		int n = zones.get(1).getListContour().size();
		int xPoints[] = new int[n];
		int yPoints[] = new int[n];
		int i = 0;
		//Redraw all the zones
		for (Zone z : zones) {
			i = 0;
			points = z.getListContour();
			for (Point2D p : points) {
				xPoints[i] = transform((int)p.getX())+translateFactor;
				yPoints[i] = transform((int)p.getY());
				//System.out.println("test : " + s.getRayon() + " -> " + xPoints[i] + " : " + yPoints[i]);
				i++;
			}
			g.setColor(new Color(255-z.getDensity().getColor(),z.getDensity().getColor(),0));
			g.fillPolygon(xPoints, yPoints, n);
		}
		//Draws the current zone
		Zone temp = s.getZoneCurrent();
		i = 0;
		points = temp.getListContour();
		for (Point2D p : points) {
			xPoints[i] = transform((int)p.getX())+translateFactor;
			yPoints[i] = transform((int)p.getY());
			//System.out.println("test : " + s.getRayon() + " -> " + xPoints[i] + " : " + yPoints[i]);
			i++;
		}
		g.setColor(Color.yellow);
		try {
		g2d.setStroke(stroke);
		g2d.drawPolygon(xPoints, yPoints, n);
		} catch (Exception e) {
		}
	}

	/**
	 * The method used to creates the class.
	 * Only defines the canvas and defines the zones
	 * 
	 * @param s the sphere to draw
	 */
	public AffichSphere(Sphere s) {
		setPreferredSize(new Dimension(height, width));
		setBackground(java.awt.Color.RED);
		this.s = s;
		majZone();
	}

	/**
	 * Number used to serialize, not yet used.
	 */
	private static final long serialVersionUID = 1L;

}
