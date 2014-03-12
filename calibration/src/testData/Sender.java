package testData;

import java.util.Iterator;
import java.util.logging.Logger;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class Sender extends Thread implements IvyMessageListener {

	private static Logger logger = Logger.getLogger(Sender.class.getName());

	private DataReader dr;
	private Ivy bus;

	public Sender(String arg) throws IvyException, InterruptedException {
		dr = new DataReader(arg);
		bus = new Ivy("Sender", "Sender Ready", null);
		bus.start(null);
		bus.sendToSelf(true);
	}

	/**
	 * permit to send a message to the drone in order to set the new telemetry
	 * mode.
	 * 
	 * @throws IvyException
	 * @throws InterruptedException
	 */
	public void sendRawMessage() throws IvyException, InterruptedException {
		logger.info("starting sending IMU test data...");
		Iterator<String> i = dr.iterator();
		String temp = new String();
		while (i.hasNext()) {
			temp = i.next();
			bus.sendMsg(temp);
			Thread.sleep(20);
		}
		logger.info("stopped sending IMU test data");
	}

	public void arret() {
		try {
			bus.stop();
			logger.info("stopped sending IMU test data");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receive(IvyClient arg0, String[] arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		try {
			this.sendRawMessage();
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}

	}

	// public static void main(String args[]) {
	// try {
	// Sender s = new Sender(
	// "/home/deltadrone3/development/autofocus/calibration/test/calib_prod1_3_droneId_5.data");
	// s.sendRawMessage();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
