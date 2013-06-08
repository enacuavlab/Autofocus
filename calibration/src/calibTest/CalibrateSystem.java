package calibTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


/**
 * Implements version of the calibration algorithm using a system call
 * 
 * @author Alinoé
 * 
 */
public class CalibrateSystem {

	/**
	 * Allows to determine the string to launch the extern program
	 */
	private TypeCalibration type;

	/**
	 * To find the place where the calibration script is
	 */
	private String ppzHome;

	/**
	 * The name of the file we pull the parameters of
	 */
	private String logName;

	/**
	 * The string displayed in the area
	 */
	private String parameters = "calcul en cours";

	/**
	 * the textArea to print to
	 */
	JTextArea result;

	/**
	 * Initialize the values of the attributes
	 * 
	 * @param t
	 * @param paparazziHome
	 * @param logName
	 * @param result
	 *            the textArea to print to
	 */
	public CalibrateSystem(TypeCalibration t, String paparazziHome,
			String logName, JTextArea result) {
		type = t;
		ppzHome = paparazziHome;
		this.logName = logName;
		this.parameters = "calcul en cours";
		this.result = result;
	}

	/**
	 * The procedure used to get the calibration values
	 * 
	 * @param logName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void calibrates() throws InterruptedException, IOException {
<<<<<<< HEAD
		String newligne = System.getProperty("line.separator");
=======
		String Newligne = System.getProperty("line.separator");
>>>>>>> branch 'master' of ssh://git@git.ienac.fr/java11s/autofocus.git
		Runtime runtime = Runtime.getRuntime();
<<<<<<< HEAD
		final Process process = runtime
				.exec("python "
						+ ppzHome
						+ "sw/tools/calibration/calibrate.py "
						+ "-s "
						+ (type.equals(TypeCalibration.ACCELEROMETER) ? "ACCEL"
								: "MAG") + logName);

=======
		String t = (type.equals(TypeCalibration.ACCELEROMETER) ?
				"ACCEL" : "MAG");
		String commande = new String("python " + ppzHome
				+ "/sw/tools/calibration/calibrate.py " + "-s " + t + " " + logName);
		final Process process = runtime.exec(commande);
>>>>>>> branch 'master' of ssh://git@git.ienac.fr/java11s/autofocus.git
		// Consommation de la sortie standard
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			StringBuffer line = new StringBuffer("");
			String l = "";
			while ((l = reader.readLine()) != null) {
				// Traitement du flux de sortie de l'application
				line.append(l);
<<<<<<< HEAD
				line.append(newligne);

=======
				line.append(Newligne);
>>>>>>> branch 'master' of ssh://git@git.ienac.fr/java11s/autofocus.git
			}
<<<<<<< HEAD
=======
			try {
				parameters = line.toString().substring(112);
			} catch (Exception e) {
				parameters = "not enough data";
			}
		} catch (IOException e) {
			e.printStackTrace();
			parameters = "Unable to parse";
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String l = "";
			while ((l = reader.readLine()) != null) {
				// Traitement du flux de sortie de l'application
				System.out.println(l);
			}
>>>>>>> branch 'master' of ssh://git@git.ienac.fr/java11s/autofocus.git
		} catch (IOException e) {
			e.printStackTrace();
			parameters = "Unable to parse";
		}
	}

	/**
	 * Update the string displayed in the textArea
	 * 
	 * @return String
	 */
	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				result.setText(parameters);
			}
		});
		try {
			calibrates();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
		return parameters;

=======
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				result.setText(parameters);
			}
		});
>>>>>>> branch 'master' of ssh://git@git.ienac.fr/java11s/autofocus.git
	}

	/**
	 * Test Function
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		try {
<<<<<<< HEAD
			CalibrateSystem s = new CalibrateSystem(

			TypeCalibration.MAGNETOMETER, "/home/gui/paparazzi/",
					"/home/gui/paparazzi/var/logs/13_04_03__13_49_35.data");
			System.out.println(s.maj());
=======
			JTextArea t = new JTextArea();
			new CalibrateSystem(TypeCalibration.MAGNETOMETER,
					"/home/gui/paparazzi", "/home/gui/test.data", t).run();
			GUIHelper.showOnFrame(t, "test");
>>>>>>> branch 'master' of ssh://git@git.ienac.fr/java11s/autofocus.git
		} finally {
			System.out.println("done");
		}
	}

}
