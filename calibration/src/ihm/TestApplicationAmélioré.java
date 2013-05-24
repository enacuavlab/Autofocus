package ihm;

import fr.dgac.ivy.IvyException;

public class TestApplicationAmélioré {
	public static void main(String args[]) {
		try {
			ApplicationAmélioré app = new ApplicationAmélioré();
			app.execute();
		} catch (IvyException e) {
			e.printStackTrace();
		}
	}
}
