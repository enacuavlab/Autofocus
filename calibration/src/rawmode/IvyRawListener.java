package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyRawListener extends Thread implements IvyMessageListener {
	private Boolean rawOnBus = false;
	private Ivy bus;
	private String telemetryMode=null;

	public IvyRawListener(final int idDrone, final int indexTelemetry) throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.bindMsg("^" + idDrone +" [A-Za-z0-9_]+RAW(.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						rawOnBus = true;
					}
				});
		bus.bindMsg("^" + idDrone + " DL_VALUES ([0-9]+) (.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						if (Integer.valueOf(args[0]).equals(
								Integer.valueOf(idDrone)))
							telemetryMode = args[1].split(",")[indexTelemetry];
					}
				});
		// bus.bindMsg("(.*)", this);
		bus.start(null);
	}

	/**
	 * Allow to test if there is any raw data on the bus
	 * 
	 */
	public Boolean isRawOnBus() {
		return rawOnBus;
	}

	public int getTelemetryMode() {
		if (telemetryMode != null){
			return Integer.valueOf(telemetryMode);
		} else return 0;
	}
	
	public void sendMode(int id,double numbermode) throws IvyException{
		bus.sendMsg("calibrate DL_SETTING "+id+" 0 "+numbermode);
	}
	
	
	/** fonction de test de la classe */
	public static void main(String args[]) throws IvyException,
			InterruptedException {
		IvyRawListener irl = new IvyRawListener(1,2);
		while (!irl.isRawOnBus()) {
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
