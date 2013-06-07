package calibTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import common.TypeCalibration;

import filtre.GUIHelper;

public class CalibrateSystem extends JTextArea {

	/**
	 * Allows to determine the string to launch the extern program
	 */
	TypeCalibration type;

	/**
	 * To find the place where the calibration script is
	 */
	String ppzHome;

	/**
	 * The name of the file we pull the parameters of
	 */
	String logName;

	/**
	 * The string displayed in the area
	 */
	String parameters = "calcul en cours";

	/**
	 * Default serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize the values of the attributes
	 * 
	 * @param t
	 * @param paparazziHome
	 * @param logName
	 */
	public CalibrateSystem(TypeCalibration t, String paparazziHome,
			String logName) {
		type = t;
		ppzHome = paparazziHome;
		this.logName = logName;
		this.setText(parameters);
		this.setBounds(20, 20, 500, 158);
	}

	/**
	 * The procedure used to get the calibration values
	 * 
	 * @param logName
	 * @throws IOException
	 */
	private void calibrates() throws InterruptedException, IOException {
		new Thread() {
			public void run() {
				String Newligne = System.getProperty("line.separator");
				Runtime runtime = Runtime.getRuntime();
				try {
					final Process process = runtime.exec("python " + ppzHome
							+ "/sw/tools/calibration/calibrate.py " + logName);
					// Consommation de la sortie standard
					StringBuffer line = new StringBuffer("");
					String l = "";
					while (line.length() < 109) {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));
						while ((l = reader.readLine()) != null) {
							// Traitement du flux de sortie de l'application
							line.append(l);
							line.append(Newligne);
						}
						reader.close();
					}
					parameters = line.toString().substring(109);
				} catch (IOException ioe) {
					ioe.printStackTrace();
					parameters = "unable to parse";
				}
			}
		}.start();
	}

	/**
	 * Update the string displayed in the textArea
	 */
	public void maj() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					calibrates();
				} catch (Exception e) {
					parameters = "unable to get the parameters" + e;
				}
				setText(parameters);
			}
		});
	}

	/**
	 * Test Function
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String args[]) throws InterruptedException,
			IOException {
		try {
			CalibrateSystem s = new CalibrateSystem(
					TypeCalibration.MAGNETOMETER, "/home/gui/paparazzi",
					"/home/gui/test.data");
			GUIHelper.showOnFrame(s, "test");
			s.maj();
		} finally {
			System.out.println("done");
		}
	}

}
