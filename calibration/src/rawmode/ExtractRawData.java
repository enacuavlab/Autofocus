package rawmode;

import java.io.File;
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

	
	public ExtractRawData(String name){
		parse(name);
	}
	/**
	 * Used to create the tree of the xml document given in input
	 * 
	 * @param toParse
	 */
	private void parse(String toParse) {
		// On cree une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On creee un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est termine ;
			File doc = new File(toParse);
			document = sxb.build(doc);
		} catch (Exception e) {
			e.printStackTrace();
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
	private String infoNoeud() {
		try {
			// On saute systematiquement le premier noeud du fichier
			List<Element> listdl_settings = racine.getChildren("dl_settings");
			indexTelemetry++;
			listdl_settings = listdl_settings.get(0).getChildren("dl_settings");
			indexTelemetry++;
			// On arrive dans la liste des dl_settings avec attributs var
			// dont on recherche la valeur var
			Iterator<Element> i = listdl_settings.iterator();
			String test = null;
			Element elem = null;
			do {
				elem = i.next();
				test = elem.getAttribute("NAME").getValue();
				// On lit un dl_setting a chaque iteration
				indexTelemetry++;
			} while (i.hasNext() && !test.equals("Misc"));
			// On lit un nouveau dl_Setting
			i = elem.getChildren().iterator();
			// On entre dans le noeud misc et on cherche le noeud
			// telemetry_mode_main
			do {
				elem = i.next();
				test = elem.getAttribute("var").getValue();
			} while (i.hasNext() && !test.equals("telemetry_mode_Main"));
			return elem.getAttribute("values").getValue();
		} catch (Exception e) {
			System.out.println("Fichier probablement vide");
			e.printStackTrace();
			return "";
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
	 * @param toParse
	 * @return the list of the telemetry modes
	 */
	public List<String> extract() {
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
		ExtractRawData d = new ExtractRawData("C:\\Users\\Alino�\\Desktop\\settings_booz2.xml");
		System.out.println(parseChoice(d.infoNoeud()));
		System.out.println(d.getIndex());
	}
}
