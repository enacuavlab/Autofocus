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

import common.TypeCalibration;
import filtre.GUIHelper;

public class CalibrateSystem extends Thread {

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
		String newline = System.getProperty("line.separator");
		Runtime runtime = Runtime.getRuntime();
		final Process process = runtime
				.exec("python "
						+ ppzHome
						+ "sw/tools/calibration/calibrate.py "
						+ "-s "
						+ (type.equals(TypeCalibration.ACCELEROMETER) ? "ACCEL"
								: "MAG") + logName);

		// Consommation de la sortie standard
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			StringBuffer line = new StringBuffer("");
			String l = "";
			while ((l = reader.readLine()) != null) {
				// Traitement du flux de sortie de l'application
				line.append(l);
				line.append(newline);
			}

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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				result.setText(parameters);
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
	public static void main(String[] args) throws InterruptedException,
			IOException {
		try {
			JTextArea t = new JTextArea();
			new CalibrateSystem(TypeCalibration.MAGNETOMETER,
					"/home/gui/paparazzi", "/home/gui/test.data", t).run();
			GUIHelper.showOnFrame(t, "test");
		} finally {
			System.out.println("done");
		}
	}

}
