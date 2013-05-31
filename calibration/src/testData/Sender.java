package testData;



import java.util.Iterator;
import java.util.List;

import fr.dgac.ivy.*;

public class Sender implements IvyMessageListener{
	private DataReader dr;
	private Ivy bus;

	public Sender(String arg) throws IvyException, InterruptedException {
		dr=new DataReader(arg);
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
		bus.start(null);
	}
	
	/**
	 *  permit to send a message to the drone in order to set the new telemetry mode.
	 * @param id id of the drone
	 * @param numbermode mode of telemetry
	 * @throws IvyException 
	 * @throws InterruptedException 
	 */
	public void sendRawMessage() throws IvyException, InterruptedException{
		Iterator<String> i = dr.iterator();
		while(i.hasNext()){
			bus.sendMsg(i.next());
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
}
