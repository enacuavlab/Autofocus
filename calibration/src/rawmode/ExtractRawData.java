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

	/**Used to create the tree of the xml document given in input
	 * 
	 * @param toParse
	 */
	private void parse(String toParse) {
		// On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;
			File doc = new File(toParse);
			document = sxb.build(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// On initialise un nouvel élément racine avec l'élément racine du
		// document.
		racine = document.getRootElement();
	}
	
	/**test function returning the root of the xml document stored in the object
	 * 
	 */
	public void test(){
		System.out.println(racine);
	}
	
	/**used to create the information about the DL_SETTING node named telemetry
	 * 
	 * @return the var field of the node
	 */
	private String infoNoeud(){
	//On saute systematiquement le premier noeud du fichier
		List<Element> listdl_settings = racine.getChildren("dl_settings");
		listdl_settings = listdl_settings.get(0).getChildren("dl_settings");
		String buf = null;
		Iterator<Element> i = listdl_settings.iterator();
		String test = null;
		Element elem = null;
		try {
			do {
				elem = i.next();
				test = elem.getAttribute("var").getName();
			} while(i.hasNext() && !test.equals("telemetry_mode_Main"));
		} catch (Exception e){
			System.out.println("Fichier probablement vide");
			e.printStackTrace();
		}
		Iterator<Element> j = i.next().getChildren().iterator();
		buf = j.next().getAttributeValue("values").toString();
		return buf;
		}
		
		//on tient donc i au noeud dlSettings contenant les modes de telemetry
		//on recupere la chaine qui va bien
	
	/**used to transform the node in a list of choice
	 * 
	 * @param toParse
	 * @return list of the possible modes
	 */
	private static List<String> parseChoice(String toParse){
		List<String> res = new LinkedList<String>();
		String[] temp = toParse.split("\\|");
		for(int i = 0; i<temp.length; i++){
			res.add(temp[i]);
		}
		return res;
	}
	
	/**main fonction return the possible telemetry modes
	 * 
	 * @param toParse
	 * @return the list of the telemetry modes
	 */
	public List<String> extract(String toParse) {
		this.parse(toParse);
		return parseChoice(this.infoNoeud());
	}
	
	public static void main(String args[]){
		ExtractRawData d = new ExtractRawData();
		d.parse("C:\\Users\\Alinoé\\Desktop\\settings_booz2.xml");
		d.test();
		System.out.println(parseChoice(d.infoNoeud()));
	}
}
