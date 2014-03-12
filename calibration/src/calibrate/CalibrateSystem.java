package calibrate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import common.TypeCalibration;

/**
 * Implements version of the calibration algorithm using a system call
 * 
 * @author Alino�
 * 
 */

/**
 * Uses the system call in order to get the results of the calibration
 * 
 * @author Guillaume
 * 
 */
public class CalibrateSystem extends Thread {

	private static Logger logger = Logger.getLogger(CalibrateSystem.class
			.getName());

	/**
	 * Allows to determine the string to launch the extern program
	 */
	private TypeCalibration type;

	/**
	 * The name of the file we pull the parameters of
	 */
	private String logName;

	/**
	 * The script to be executed
	 */
	private String paparazziScriptCalibration;

	/**
	 * The string displayed in the area
	 */
	private String parameters = "calcul en cours";

	/**
	 * the textArea to print to
	 */
	private JTextArea result;

	/**
	 * Initialize the values of the attributes
	 * 
	 * @param t
	 * @param paparazziHome
	 * @param logName
	 * @param result
	 *            the textArea to print to
	 * @param textResult
	 *            the textArea with results
	 */
	public CalibrateSystem(TypeCalibration t,
			String paparazziScriptCalibration, String logName, JTextArea result) {
		type = t;
		this.logName = logName;
		this.paparazziScriptCalibration = paparazziScriptCalibration;
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

		String command = "python "
				+ paparazziScriptCalibration
				+ " -s "
				+ (type.equals(TypeCalibration.ACCELEROMETER) ? "ACCEL "
						: "MAG ") + " -v " + logName;
		logger.info("executing : " + command);
		final Process process = runtime.exec(command);

		logger.info("python script result for " + type + " :");
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
			parameters = line.toString();
			logger.info(parameters);

		} catch (IOException e) {
			parameters = "Unable to parse";
			logger.warning(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			parameters = "no data";
			logger.warning(e.getMessage());
		}
		logger.info("end of python script for " + type);
	}

	/**
	 * Update the string displayed in the textArea
	 */
	public void run() {
		try {
			calibrates();
		} catch (InterruptedException e) {
			logger.warning("can't get python script to calibrate : "
					+ e.getMessage());
		} catch (IOException e) {
			logger.warning(" can't get result file to calibrate : "
					+ e.getMessage());
		} catch (Exception e) {
			logger.warning(" can't get calibration : " + e.getMessage());
			e.printStackTrace();
		}

		// Affichage des résultats
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				result.setText(parameters);
			}
		});
	}
}
