package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyConfigListener {

	
	private Ivy bus;
	private int id;
	
	public IvyConfigListener(final int idDrone) throws IvyException {
		id = idDrone;
		bus = new Ivy("IvyRawListener", "IvyConfigListener Ready", null);
		bus.bindMsg("(.*) CONFIG (.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						System.out.println(args[0] + " CONFIG " + args[1]);
					}
				});
		// bus.bindMsg("(.*)", this);
		bus.start(null);
	}
	
	public void sendRequest() throws IvyException {
		bus.sendMsg("calibrate CONFIG_REQ " + id);
	}
	
	public static void main(String args[]) {
		try {
			IvyConfigListener listen = new IvyConfigListener(5);
			listen.sendRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
