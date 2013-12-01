/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import java.awt.BasicStroke;
import java.awt.Color;
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
		//Date date = new Date();
		//System.out.println("debut affichage : "+ date.toString());
		super.paintComponents(g);
		//defines the canvas
		Graphics2D g2d = (Graphics2D) g;
		g.clearRect(0, 0, super.getHeight(), super.getWidth());
		//System.out.println(s.getRayon() + " : " + super.getHeight() + " : " + super.getWidth());
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
				xPoints[i] = (int)((p.getX()/(float)s.getRayon()*(float)super.getWidth()/2 + (float)super.getWidth()/2));
				yPoints[i] = (int)((p.getY()/(float)s.getRayon()*(float)super.getHeight()/2 + (float)super.getHeight()/2));
				if (xPoints[i] > 10 || yPoints[i] > 10) {
					//System.out.println("test : " +s.getRayon() + " : " + super.getHeight() + " : " + super.getWidth()+
					//	" ->1 " + xPoints[i] + " : " + yPoints[i]);
				}
				i++;
			}
			g.setColor(new Color(255-z.getDensity().getColor(),z.getDensity().getColor(),0));
			try {
				g2d.setStroke(stroke);
				g2d.fillPolygon(xPoints, yPoints, n);
				g2d.drawPolygon(xPoints, yPoints, n);
				} catch (Exception e) {
				}
		}
		//Draws the current zone
		Zone temp = s.getZoneCurrent();
		points = temp.getListContour();
		i = 0;
		n = zones.get(1).getListContour().size();
		for (Point2D p : points) {
			xPoints[i] = (int)((p.getX()/(float)s.getRayon())*(float)super.getWidth()/2 + (float)super.getWidth()/2);
			yPoints[i] = (int)((p.getY()/(float)s.getRayon())*(float)super.getHeight()/2 + (float)super.getHeight()/2);
			//System.out.println("test : " + s.getRayon() + " -> " + xPoints[i] + " : " + yPoints[i]);
			i++;
		}
		g.setColor(Color.yellow);
		try {
		g2d.setStroke(stroke);
		g2d.drawPolygon(xPoints, yPoints, n);
		} catch (Exception e) {
		}
		//System.out.println("fin affichage : "+ date.toString());
	}


	/**
	 * Number used to serialize, not yet used.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an affichsphere for a sphere
	 */
	public AffichSphere(Sphere s) {
		this.s = s;
		this.zones = s.getZones();
	}
}
