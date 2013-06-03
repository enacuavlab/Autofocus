package rawmode;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import org.jdom2.*;
import org.jdom2.input.*;

public class ExtractRawData {

	private org.jdom2.Document document;
	private Element racine;
	// permet de sotcker l'index de la telemetry
	private int indexTelemetry = 0;

	public ExtractRawData(String name) throws IOException {
		parse(name);
	}

	/**
	 * Used to create the tree of the xml document given in input
	 * 
	 * @param toParse
	 */
	private void parse(String toParse) throws IOException {
		// On cree une instance de SAXBuilder
		try {
			SAXBuilder sxb = new SAXBuilder();
			// On creee un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est termine ;
			File doc = new File(toParse);
			document = sxb.build(doc);
		} catch (JDOMException e) {
			System.out.println("Fichier xml non valide");
		}
		// On initialise un nouvel element racine avec l'element racine du
		// document.
		racine = document.getRootElement();
	}

	/**
	 * test function returning the root of the xml document stored in the object
	 * 
	 */
	public void test() {
		System.out.println(racine);
	}

	/**
	 * used to create the information about the DL_SETTING node named telemetry
	 * 
	 * @return the var field of the node
	 */
	private String infoNoeud() throws IncorrectXmlException{
		try {
			// On saute systematiquement le premier noeud du fichier
			List<Element> listdl_settings = racine.getChildren("dl_settings");
			indexTelemetry++;
			Iterator<Element> i = listdl_settings.iterator();
			String test = null;
			Element elem = null;
			do {
				elem = i.next();
				test = elem.getAttribute("NAME").getValue();
				// On lit un dl_setting a chaque iteration
				indexTelemetry++;
			} while (i.hasNext() && !test.equals("Telemetry"));
			if (!elem.getAttribute("NAME").getValue().equals("Telemetry")) {
				throw new IncorrectXmlException();
			}
			listdl_settings = listdl_settings.get(0).getChildren("dl_settings");
			indexTelemetry++;
			String temp = "";
			do {
				elem = i.next();
				temp = temp + elem.getAttribute("var").getValue();
			} while (i.hasNext());
			return temp;
		} catch (Exception e) {
			// System.out
			// .println("Fichier xml incorrect, pas de noeud Telemetry_Mode_MAIN trouve");
			new IncorrectXmlException("Xml incorrect", e);
		}
		return("");
	}

	// on tient donc i au noeud dlSettings contenant les modes de telemetry
	// on recupere la chaine qui va bien

	/**
	 * used to transform the node in a list of choice
	 * 
	 * @param toParse
	 * @return list of the possible modes
	 */
	private static List<String> parseChoice(String toParse) {
		List<String> res = new LinkedList<String>();
		String[] temp = toParse.split("\\|");
		for (int i = 0; i < temp.length; i++) {
			res.add(temp[i]);
		}
		return res;
	}

	/**
	 * main fonction return the possible telemetry modes
	 * 
	 * @param toParse
	 * @return the list of the telemetry modes
	 */
	public List<String> extract() throws IncorrectXmlException {
		return parseChoice(this.infoNoeud());
	}

	/**
	 * function use to get the number of the node named telemetry_mode_Main
	 * 
	 * @return number of the node Telemetry_Mode_MAIN;
	 */
	public int getIndex() {
		return indexTelemetry;
	}

	public static void main(String args[]) {
		try {
			ExtractRawData d = new ExtractRawData(
				"C:\\Users\\Alinoe\\Desktop\\settings_booz2.xml");
		System.out.println(parseChoice(d.infoNoeud()));
		System.out.println(d.getIndex());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
