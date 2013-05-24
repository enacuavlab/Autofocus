package rawmode;


import fr.dgac.ivy.*;

public class IvyRawListener implements IvyMessageListener{
	private boolean raw_data_available= false;
	private Ivy bus;

	IvyRawListener() throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.bindMsg("^[A-Za-z0-9]+ DL_VALUES (.*)", this);
		bus.bindMsg("(.*)", this);
		bus.start(null);
	}

	public void receive(IvyClient arg0, String[] args) {
		System.out.println(args[0]);
	}

	public void stop() throws IvyException {
		bus.stop();
	}

}
