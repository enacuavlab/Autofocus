/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import filtre.VecteurFiltrable;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;


/**
 * Class to build the Accelerometers view
 * 
 * @author Guillaume
 * 
 */
public class AffichAccel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1632196092075420985L;

	private Sphere sp;
	/**
	 * ProgressBar which represents the amount of correct vectors collected on
	 * the current position
	 */
	private JProgressBar progressBar;
	/**
	 * Contains instructions and images
	 */
	private JPanel panelInst;
	/**
	 * The instructions
	 */
	private JTextArea inst;
	/**
	 * Images
	 */
	private JLabel labelPhoto;


	/**
	 * Create the sphere and the layout of the accelerometer's view
	 * s the sphere to display
	 */
	
	public AffichAccel(Sphere s){
		this.setLayout(null);
		sp=s;
		progressBar=new JProgressBar(0,100);
		this.add(progressBar);
		this.add(sp.getAffichage());
		panelInst = new JPanel();
		panelInst.setLayout(null);
		this.add(panelInst);
		inst = new JTextArea();
		inst.setEditable(false);
		inst.setLineWrap(true);
		inst.setText("Maintain your drone in a stable position");	
		panelInst.add(inst);
		labelPhoto = new JLabel();
		panelInst.add(labelPhoto);
	}

	/**
	 * To change instructions
	 * 
	 * @param text
	 * @param urlPhoto
	 */
	public void setInstruction(String text, String urlPhoto) {
		inst.setText(text);
		labelPhoto.setIcon((Icon) new ImageIcon(urlPhoto));
	}

	/**
	 * To make progress the progress bar
	 * 
	 * @param value
	 */
	public void setValueProgressBar(int value) {
		progressBar.setValue(value);
	}

	/**
	 * Give the progress Bar
	 * 
	 * @return progress Bar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * Give the sphere
	 * 
	 * @return sphere
	 */
	public Sphere getSphere() {
		return sp;
	}


	/**
	 * Updates the sphere
	 * @param radius
	 * @param newcenter
	 * @param v
	 * @param vcourant
	 * @param nbCorrectOK
	 */
	public void update(final double radius,
			final VecteurFiltrable<Double> newcenter,
			final VecteurFiltrable<Double> v ,
			final VecteurFiltrable<Double> vcourant, 
			final int nbCorrectOK){
		sp.update(radius, newcenter, v, vcourant);
		setValueProgressBar(nbCorrectOK);
		int value = progressBar.getValue();
		if (value > 0) {
			if (value == 100) {
				
				inst.setText("Change the position of your drone and maintain");
	
			}
			else inst.setText("Continue to maintain the position");
		}
	}

	/**
	 * Give some informations and put the initial value on the progress bar to
	 * indicate a new position for the drone
	 */
	public void changedStates() {
		setValueProgressBar(0);
			
	}

	/**
	 * Give the instruction panel
	 * 
	 * @return the panel where we write the instructions
	 */
	public JPanel getLabel() {
		return panelInst;
	}
	
}
