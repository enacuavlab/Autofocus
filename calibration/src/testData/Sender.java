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

	public void arret() {
		try {
			bus.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receive(IvyClient arg0, String[] arg1) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]){
		try {
			Sender s = new Sender("input test file");
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
			System.out.println("Sender Error");
			e.printStackTrace();
		}
		
	}
}
