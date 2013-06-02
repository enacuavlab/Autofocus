package testData;

import java.util.Iterator;

import fr.dgac.ivy.*;

public class Sender extends Thread implements IvyMessageListener {
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
	 * @param id
	 *            id of the drone
	 * @param numbermode
	 *            mode of telemetry
	 * @throws IvyException
	 * @throws InterruptedException
	 */
	public void sendRawMessage() throws IvyException, InterruptedException {
		System.out.println("Debut sender");
		Iterator<String> i = dr.iterator();
		String temp = new String();
		while (i.hasNext()) {
			temp = i.next();
			//System.out.println("Sender : " + temp);
			bus.sendMsg(temp);
			Thread.sleep(20);
		}
	}

	public void arret() throws IvyException {
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

	@Override
	public void run() {
		try {
			this.sendRawMessage();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
