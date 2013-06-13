/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import ihm.ScreenHelper;

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
 * @author Alino�
 *
 */
public class AffichSphere extends JPanel {

	private ScreenHelper screen;

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
		g.clearRect(0, 0, super.getHeight(), super.getWidth());
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
				xPoints[i] = (int)((p.getX()/s.getRayon())*(float)super.getWidth() + (float)super.getWidth()/2);
				yPoints[i] = (int)((p.getY()/s.getRayon())*(float)super.getHeight() + (float)super.getHeight()/2);
				System.out.println("test : " + s.getRayon() + " ->1 " + xPoints[i] + " : " + yPoints[i]);
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
			xPoints[i] = (int)((p.getX()/s.getRayon())*super.getWidth() + super.getWidth()/2);
			yPoints[i] = (int)((p.getY()/s.getRayon())*super.getHeight() + super.getHeight()/2);
			System.out.println("test : " + s.getRayon() + " -> " + xPoints[i] + " : " + yPoints[i]);
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
		screen = new ScreenHelper();
		this.s = s;
		majZone();
	}

	/**
	 * Number used to serialize, not yet used.
	 */
	private static final long serialVersionUID = 1L;

}
