package ihm;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import filtre.VecteurFiltrable;



public class Plotter extends JPanel {
	
	private List<VecteurFiltrable<Double>> points = new LinkedList<VecteurFiltrable<Double>>();
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		int i = 1;
		for(VecteurFiltrable<Double> v : points){
			g.drawLine(i,(int)v.getX(), i,(int)v.getX()+1);
			//cast explicite de double avec des 0 après la virgule en int
			i++;
		}
	}
	
	public void add(VecteurFiltrable<Double> v){
		points.add(v);
		this.repaint();
	}

	/**
	 * Added for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	

}
