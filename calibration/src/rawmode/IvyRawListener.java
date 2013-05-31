package rawmode;


import fr.dgac.ivy.*;

public class IvyRawListener implements IvyMessageListener{
	private boolean raw_data_available= false;
	private Ivy bus;

	IvyRawListener() throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.bindMsg("^[A-Za-z0-9]+ DL_SETTING ([0-9]+) ([0-9]+) ([0-9]+[/.]*[0-9]*)", this);
		//bus.bindMsg("(.*)", this);
		bus.start(null);
	}
	
	/**
	 *  permit to send a message to the drone in order to set the new telemetry mode.
	 * @param id id of the drone
	 * @param numbermode mode of telemetry
	 * @throws IvyException 
	 */
	public void sendRawMode(int id,double numbermode) throws IvyException{
		bus.sendMsg("calibrate DL_SETTING "+id+" 0 "+numbermode);
	}

	public void receive(IvyClient arg0, String[] args) {
		System.out.println(args[0]+" "+args[1]+" "+args[2]);
	}

	public void stop() throws IvyException {
		bus.stop();
	}

}
