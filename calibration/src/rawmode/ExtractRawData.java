package rawmode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ExtractRawData {

	private static Logger logger = Logger.getLogger(ExtractRawData.class
			.getName());

	private org.jdom2.Document document;
	private Element racine;
	// permet de sotcker l'index de la telemetry
	private int indexTelemetry = 0;

	public ExtractRawData(String name) throws IOException {
		parse(name);
		try {
			extract();
		} catch (IncorrectXmlException e) {
			logger.warning(e.getMessage());
		}
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
			logger.warning("Fichier xml non valide");
			logger.warning(e.getMessage());
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
		logger.info(racine.toString());
	}

	/**
	 * used to create the information about the DL_SETTING node named telemetry
	 * 
	 * @return the var field of the node
	 */
	private String infoNoeud() throws IncorrectXmlException {
		try {
			Iterator<Element> i = racine.getChildren("dl_settings").iterator();
			i = i.next().getChildren("dl_settings").iterator();
			Element temp = i.next();
			String res = "";
			while (!(temp.getAttribute("name").getValue().equals("Telemetry"))) {
				temp = i.next();
				indexTelemetry++;
			}
			i = temp.getChildren("dl_setting").iterator();
			while (i.hasNext()) {
				res = res + i.next().getAttribute("values").getValue() + '|';
			}
			return res;
		} catch (Exception e) {
			throw new IncorrectXmlException("lecture du fichier", e);
		}

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
	 * @return the list of the telemetry modes
	 */
	public List<String> extract() throws IncorrectXmlException {
		return parseChoice(this.infoNoeud());
	}

	/**
	 * function use to get the number of the node named telemetry_mode_Main
	 * 
	 * @return number of the node Telemetry_Mode_MAIN;
	 * @throws IncorrectXmlException
	 */
	public int getIndex() throws IncorrectXmlException {
		if (indexTelemetry >= 0) {
			return indexTelemetry;
		} else
			throw new IncorrectXmlException();
	}

	public static void main(String args[]) {
		try {

			ExtractRawData d = new ExtractRawData(
					"C:\\Users\\Alino�\\Desktop\\settings.xml");
			// logger.info(d.racine);
			logger.info(parseChoice(d.infoNoeud()).toArray().toString());
			logger.info("" + d.getIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
