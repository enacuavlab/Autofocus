package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyRawListener implements IvyMessageListener{
	private Boolean rawOnBus = false;
	private Ivy bus;

	IvyRawListener() throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.bindMsg("^[A-Za-z0-9]+ RAW (.*)",new IvyMessageListener(){
			public void receive(IvyClient arg0, String[] args) {
				rawOnBus = true;
			}
		});
		//bus.bindMsg("(.*)", this);
		bus.start(null);
	}
	
	/**
	 * Allow to test if there is any raw data on the bus
	 *
	 */
	public Boolean IsRawOnBus(){
		return rawOnBus;
	}
	
	/**fonction de test de la classe*/
	public static void main(String args[]) throws IvyException, InterruptedException {
		IvyRawListener irl = new IvyRawListener();
		while(!irl.IsRawOnBus()) {
			Thread.sleep(200);
			System.out.println("No raw data");
		}
		System.out.println("There's raw on bus!!!!!!!!!!!!!");
	}

	@Override
	public void receive(IvyClient arg0, String[] arg1) {
		// TODO Auto-generated method stub
		
	}
}

