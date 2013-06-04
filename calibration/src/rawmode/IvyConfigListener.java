package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyConfigListener {

	
	private Ivy bus;
	private int id;
	private int reqid = 1;
	
	public IvyConfigListener(final int idDrone) throws IvyException {
		id = idDrone;
		bus = new Ivy("IvyRawListener", "IvyConfigListener Ready", null);
		bus.start(null);
		bus.bindMsg("" + reqid + " " + "^[A-Za-z0-9]+ CONFIG (.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						System.out.println(args[0] + " CONFIG " + args[1]);
					}
				});
		bus.bindMsg("^([A-Za-z0-9]+) ([A-Za-z0-9]+) CONFIG_REQ (.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						System.out.println("Ret: " + args[0] + " " + args[1] + " CONFIG_REQ " + args[2]);
					}
				});
		// bus.bindMsg("(.*)", this);
		bus.sendToSelf(true);
	}
	
	public void sendRequest() throws IvyException {
		//System.out.println("calibrate CONFIG_REQ " + id);
		bus.sendMsg("calibrate " + reqid + " CONFIG_REQ " + id);
		reqid++;
	}
	
	public static void main(String args[]) {
		try {
			IvyConfigListener listen = new IvyConfigListener(5);
			for(int i = 0; i<10;i++){
				listen.sendRequest();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
