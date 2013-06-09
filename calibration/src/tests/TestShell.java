/**
 * Package IHM contents the interface of our application
 */
package tests;

import ihm.Shell;

import javax.swing.SwingUtilities;


/**
 * Launch the shell
 * @author Guillaume
 *
 */
public class TestShell {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// On crée une nouvelle instance de notre JDialog
				Shell shell = new Shell();
				shell.setVisible(true);// On la rend visible
				// test.run();
			}
		});
	}
}
