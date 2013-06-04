package ihm;

import javax.swing.SwingUtilities;



public class TestShell {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// On crée une nouvelle instance de notre JDialog
				ShellV2 shell = new ShellV2();
				shell.setVisible(true);// On la rend visible
				// test.run();
			}
		});
	}
}
