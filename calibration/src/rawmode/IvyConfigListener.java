package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyConfigListener {

	
	private Ivy bus;
	private int id;
	private int reqid =176;
	
	public IvyConfigListener(final int idDrone) throws IvyException {
		id = idDrone;
		bus = new Ivy("IvyConfigListener", "IvyConfigListener Ready", null);
		bus.start(null);
		System.out.print("str");
		bus.sendMsg("marche connard");
	}
	
	public void sendRequest() throws IvyException {
		bus.bindMsg(("^" + reqid + " " + "([A-Za-z0-9]+) CONFIG (.*)"),
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						System.out.println(args[0] + " CONFIG "+ args[1]);
						bus.unBindMsg("" + reqid + " " + "[A-Za-z0-9]+ CONFIG .*");
					}
				});
		String str ="CONFIG";
		bus.sendMsg(str);
		System.out.print(str);
		reqid++;
	}
	
	public static void main(String args[]) {
		try {
			IvyConfigListener listen = new IvyConfigListener(6);
			listen.sendRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
