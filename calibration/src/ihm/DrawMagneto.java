/**
 * Classes used to display view of the current calibration
 */
package ihm;

import java.util.logging.Logger;

import ellipsoide.Sphere;

/**
 * The class responsible of displaying the sphere
 * 
 * @author Alino�
 * 
 */
public class DrawMagneto extends Draw {

	private static Logger logger = Logger
			.getLogger(DrawMagneto.class.getName());

	/**
	 * Number used to serialize, not yet used.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an affichsphere for a sphere
	 */
	public DrawMagneto(Sphere s) {
		super(s, "Magnétométrie", 0);

		// Affichage du texte
		getInstructions().setText("Tourner le drone dans tous les sens");
	}
}
