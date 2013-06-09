/**
 * Classes used to display view of the current calibration
 */
package ellipsoide;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import filtre.VecteurFiltrable;


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
	 * @param s the sphere to display
	 */
	
	public AffichAccel(Sphere s){
		this.setPreferredSize(new Dimension(1000,600));
		this.setLayout(null);
		sp=s;
		progressBar=new JProgressBar(0,100);
		progressBar.setBounds(240, 400, 300, 25);
		this.add(progressBar);
		sp.getAffichage().setBounds(0, 0, 800, 400);
		this.add(sp.getAffichage());
		panelInst=new JPanel();
		panelInst.setLayout(null);
		panelInst.setBounds(850, 50, 320, 420);
		this.add(panelInst);
		inst = new JTextArea();
		inst.setEditable(false);
		inst.setBounds(20, 20, 280, 280);
		inst.setLineWrap(true);

		panelInst.add(inst);
		labelPhoto = new JLabel();
		labelPhoto.setBounds(20, 20, 280, 100);
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
	}

	/**
	 * Give some informations and put the initial value on the progress bar to
	 * indicate a new position for the drone
	 */
	public void changedStates() {
		setValueProgressBar(0);
		inst.setText("Maintain your drone in a stable position not yet explored");	
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
