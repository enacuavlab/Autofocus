package testData;

import java.util.Iterator;

import fr.dgac.ivy.*;

public class Sender implements IvyMessageListener {
	private DataReader dr;
	private Ivy bus;

	public Sender(String arg) throws IvyException, InterruptedException {
		dr = new DataReader(arg);
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.start(null);
	}

	/**
	 * permit to send a message to the drone in order to set the new telemetry
	 * mode.
	 * 
	 * @param id
	 *            id of the drone
	 * @param numbermode
	 *            mode of telemetry
	 * @throws IvyException
	 * @throws InterruptedException
	 */
	public void sendRawMessage() throws IvyException, InterruptedException {
		Iterator<String> i = dr.iterator();
		String temp = new String();
		while (i.hasNext()) {
			temp = i.next();
			System.out.println(temp);
			bus.sendMsg(temp);
			Thread.sleep(20);
		}

	}

	public void stop() throws IvyException {
		bus.stop();
	}

	@Override
	public void receive(IvyClient arg0, String[] arg1) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]){
		try {
			Sender s = new Sender("C:\\Users\\Alinoé\\Desktop\\13_05_29__10_15_23.data");
			s.sendRawMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
