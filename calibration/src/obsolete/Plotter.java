package obsolete;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import tests.GUIHelper;

import data.Vecteur;
import ellipsoide.Sphere;
import filtre.VecteurFiltrable;


public class Plotter extends JPanel {

	private int maxX = 0;
	private int minX = 0;
	private int maxY = 0;
	private int minY = 0;
	private int maxZ = 0;
	private int minZ = 0;
	private int rayon = 0;
	private List<VecteurFiltrable<Double>> points = new LinkedList<VecteurFiltrable<Double>>();

	private Sphere s = null;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.clearRect(0, 0, 800, 800);
		for (VecteurFiltrable<Double> v : points) {
			if (v.isCorrect()) {
				g.setColor(Color.blue);
			} else
				g.setColor(Color.red);
			g.drawOval((int) v.getX() / 2 + 200, (int) v.getY() / 2 + 600, 5, 5);
			g.setColor(Color.green);
			g.drawOval((int) (maxX + minX) / 4 + 200,
					(int) (maxY + minY) / 4 + 600, 5, 5);
			g.drawOval((int) (maxX + minX) / 4 + 200 - rayon / 4,
					(int) (maxY + minY) / 4 + 600 - rayon / 4,
					rayon / 2, rayon / 2);
			// cast explicite de double avec des 0 après la virgule en int
		}
	}

	public void add(VecteurFiltrable<Double> v) {
		points.add(v);
		if (v.isCorrect()) {
			if (v.getX() > maxX)
				maxX = (int) v.getX();
			if (v.getY() > maxY)
				maxY = (int) v.getY();
			if (v.getZ() > maxZ)
				maxZ = (int) v.getZ();
			if (v.getX() < minX)
				minX = (int) v.getX();
			if (v.getY() < minY)
				minY = (int) v.getY();
			if (v.getZ() < minZ)
				minZ = (int) v.getZ();
		}
		rayon = (maxX - minX > maxY - minY ? (maxX - minX > maxZ - minZ ? maxX
				- minX : maxZ - minZ) : (maxY - minY > maxZ - minZ ? maxY
				- minY : maxZ - minZ));
		if (s!=null) {
			s.update(rayon, new Vecteur((minX+maxX)/2, (minY+maxY)/2, (minZ+maxZ)/2),v);
		}
		this.repaint();
	}

	public Plotter() {
		setPreferredSize(new Dimension(800, 800));
		setBackground(java.awt.Color.WHITE);
	}
	
	public Plotter(Sphere s) {
		this.s = s;
	}

	/**
	 * Added for serialization
	 */
	private static final long serialVersionUID = 1L;

	public void printMinMax() {
		System.out.println("");
		System.out.println("X min " + minX + " max " + maxX);
		System.out.println("Y min " + minY + " max " + maxY);
		System.out.println("Z min " + minZ + " max " + maxZ);
	}

	public static void main(String args[]) {
		Plotter plot = new Plotter();
		Vecteur v = new Vecteur(50, 0, 0);
		v.setTrue();
		plot.add(v);
		GUIHelper.showOnFrame(plot, "test");
		plot.add(new Vecteur(0, 0, 0));
	}
}
