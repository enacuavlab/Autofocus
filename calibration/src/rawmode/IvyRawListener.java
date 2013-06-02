package rawmode;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyRawListener extends Thread implements IvyMessageListener {
	private Boolean rawOnBus = false;
	private Ivy bus;
	private String telemetryMode;

	public IvyRawListener(final int idDrone, final int indexTelemetry) throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.bindMsg("^[A-Za-z0-9]+ [A-Za-z0-9_]+RAW(.*)",
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						rawOnBus = true;
					}
				});
		bus.bindMsg("^[A-Za-z0-9]+ DL_VALUES ([0-9]+) (.*)",
				new IvyMessageListener() {
					private int idDrone;
					private int indexTelemetry;

					public void receive(IvyClient arg0, String[] args) {
						if (Integer.valueOf(args[0]).equals(
								Integer.valueOf(this.idDrone)))
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
		return Integer.valueOf(telemetryMode);
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
	public void run() {
		try {
			while (!this.isRawOnBus()) {
				Thread.sleep(200);
				System.out.println("No raw data");
			}
			System.out.println("There's raw on bus!!!!!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receive(IvyClient arg0, String[] arg1) {
		// TODO Auto-generated method stub

	}
}
