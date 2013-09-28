package calibrate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.sun.servicetag.SystemEnvironment;

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
	private JTextArea result;
	/**
	 * the textArea with result
	 */
	private JTextArea resultCopy;
	/**
	 * The accuracy of the mesure
	 */
	private String prec;

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
	public CalibrateSystem(TypeCalibration t, String paparazziHome,
			String logName, JTextArea result, JTextArea textResult) {
		type = t;
		ppzHome = paparazziHome;
		this.logName = logName;
		this.parameters = "calcul en cours";
		this.prec = "indisponible";
		this.result = result;
		this.resultCopy = textResult;
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
		final Process process = runtime.exec("python "
				+ "/home/alinoe/paparazzi"
				+ "/sw/tools/calibration/calibrate.py "
				+ "-s "
				+ (type.equals(TypeCalibration.ACCELEROMETER) ? "ACCEL "
						: "MAG ") + logName);
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
			String[] aline;
			aline = line.toString().split("<");
			prec = aline[0];
			parameters = "<" + aline[1] + "<" + aline[2] + "<" + aline[3] + "<"
					+ aline[4] + "<" + aline[5] + "<" + aline[6];
			
		} catch (IOException e) {
			parameters = "Unable to parse";
		} catch (ArrayIndexOutOfBoundsException e) {
			parameters = "no data";
		}
	}

	/**
	 * Update the string displayed in the textArea
	 * 
	 * @return String
	 */
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				result.setText(parameters);
				resultCopy.setText(prec);
			}
		});
		try {
			calibrates();
		} catch (InterruptedException e) {
			System.out.println("can't get python script to calibrate");
		} catch (IOException e) {
			System.out.println("can't get result file to calibrate");
		} catch (Exception e) {
			System.out.println("can't get calibration");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				result.setText(parameters);
				resultCopy.setText(prec);
			}
		});
	}
}
