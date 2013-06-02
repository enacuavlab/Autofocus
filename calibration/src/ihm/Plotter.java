package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import data.Vecteur;

import filtre.VecteurFiltrable;

public class Plotter extends JPanel {

	private List<VecteurFiltrable<Double>> points = new LinkedList<VecteurFiltrable<Double>>();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		for (VecteurFiltrable<Double> v : points) {
			if (v.isCorrect()) {
				g.setColor(Color.blue);
			} else
				g.setColor(Color.red);
			g.drawOval((int) v.getX() / 2 + 200, (int) v.getY() / 2 + 600, 5, 5);
			// cast explicite de double avec des 0 après la virgule en int
		}
	}

	public void add(VecteurFiltrable<Double> v) {
		points.add(v);
		this.repaint();
	}

	public Plotter() {
		setPreferredSize(new Dimension(800, 800));
		setBackground(java.awt.Color.WHITE);
	}

	/**
	 * Added for serialization
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		Plotter plot = new Plotter();
		Vecteur v = new Vecteur(50, 0, 0);
		v.setTrue();
		plot.add(v);
		GUIHelper.showOnFrame(plot, "test");
		plot.add(new Vecteur(0, 0, 0));
	}
}
