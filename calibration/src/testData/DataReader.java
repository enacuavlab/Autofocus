package testData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class DataReader implements Iterable<String> {

	private static Logger logger = Logger.getLogger(DataReader.class.getName());

	ArrayList<String> lignes = new ArrayList<String>();

	public DataReader(String arg) {
		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(arg);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				lignes.add(ligne.substring(ligne.indexOf(" ") + 1));
			}
			br.close();
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
	}

	@Override
	public Iterator<String> iterator() {
		return lignes.iterator();
	}
}
