package calibTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import common.TypeCalibration;

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
		this.parameters = "calcul en cours";
	}

	/**
	 * The procedure used to get the calibration values
	 * 
	 * @param logName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void calibrates() throws InterruptedException, IOException {
		String newligne = System.getProperty("line.separator");
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
				line.append(newligne);

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
	public String maj() {
		try {
			calibrates();
		} catch (Exception e) {
			parameters = "unable to get the parameters" + e;
		}
		return parameters;

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
			CalibrateSystem s = new CalibrateSystem(

			TypeCalibration.MAGNETOMETER, "/home/gui/paparazzi/",
					"/home/gui/paparazzi/var/logs/13_04_03__13_49_35.data");
			System.out.println(s.maj());
		} finally {
			System.out.println("done");
		}
	}

}
