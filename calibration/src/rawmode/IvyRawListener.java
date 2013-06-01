package rawmode;

import fr.dgac.ivy.*;

public class IvyRawListener implements IvyMessageListener{
	private String telemetry_mode;
	private Ivy bus;

	IvyRawListener(final int id_drone,final int index_telemetry) throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.bindMsg("^[A-Za-z0-9]+ DL_SETTING ([0-9]+) ([0-9]+) ([0-9]+[/.]*[0-9]*)", this);
		bus.bindMsg("^[A-Za-z0-9]+ DL_VALUES ([0-9]+) (.*)",new IvyMessageListener(){
			public void receive(IvyClient arg0, String[] args) {
				if (Integer.valueOf(args[0])== id_drone)
				telemetry_mode =args[1].split(",")[index_telemetry];
			}
		});
		bus.bindMsg("^[A-Za-z0-9]+ DL_VALUE (.*)",new IvyMessageListener(){
			public void receive(IvyClient arg0, String[] args) {
							System.out.println("values "+args[0] );
						}
					});
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
	public void sendconfig(int id, String arg) throws IvyException{
		bus.sendMsg("calibrate CONFIG_REC"+" "+id+" "+arg);
	}

	public void receive(IvyClient arg0, String[] args) {
		System.out.println(args[0]+" "+args[1]+" "+args[2]);
	}

	public void stop() throws IvyException {
		bus.stop();
	}
	public double getTelemetrymode(){
		return Double.valueOf(telemetry_mode);
	}
	
	/**fonction de test de la classe*/
	public static void main(String args[]) throws IvyException, InterruptedException {
		//class never used because all the opertations are done in the builder
		IvyRawListener irl = new IvyRawListener(6,2);
	}
}
