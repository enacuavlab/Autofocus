package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyConfigListener {

	
	private Ivy bus;
	
	public IvyConfigListener(final int idDrone) throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyConfigListener Ready", null);
		bus.bindMsg("(.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						System.out.println(args[0]);
					}
				});
		// bus.bindMsg("(.*)", this);
		bus.start(null);
	}
	
	public static void main(String args[]) {
		try {
			IvyConfigListener listen = new IvyConfigListener(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
