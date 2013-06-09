package calibrate;

import java.io.FileWriter;
import java.util.ArrayList;

/**Stores the messages used during calibration.
 * @author Alino�
 */

public class PrintLog {

	/**Data to store all the messages.
	 */
	private ArrayList<String> lines = new ArrayList<String>();

	/**Store a new line in the object.
	 *
	 * @param line to add
	 */
	public final void add(final String line) {
		lines.add(line);
	}

	/**Creates the log file.
	 *
	 * @param fileName
	 */
	public final void print(final String fileName) {
		try {
			String nl = System.getProperty("line.separator");
			FileWriter fs = new FileWriter(fileName);
			for (String line : lines) {
				fs.write("11.111 ");
				fs.write(line);
				fs.write(nl);
			}
			fs.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	/**Test function of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrintLog test = new PrintLog();
		test.add("ok");
		test.print("C:\\Users\\Alino�\\ok.txt");
	}
}
