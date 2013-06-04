package ellipsoide;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class AffichSphere extends JPanel {
	
	private Sphere s;
	private List<Zone> zones;
	
	public void majZone() {
		zones = s.getZones();
	}
	
	public void affiche() {
		this.repaint();
	}
	
	public void paintComponent(){
		for(Zone z : zones){
			z.getCoord();
		}
	}

	public AffichSphere(Sphere s){
		this.s = s;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
