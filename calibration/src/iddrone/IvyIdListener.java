package iddrone;


/**
 * Ivy client that allows user to know if the IMU is connected to the bus
 * @author florent Gervais
 */
import java.util.ArrayList;
import java.util.List;
import fr.dgac.ivy.*;

public class IvyIdListener implements IvyMessageListener {
	private List <Integer> listeId;
	private Ivy bus;
	
	/**
	 * permit to read on the Ivy bus to find the matching numbers
	 * @throws IvyException
	 * @throws InterruptedException
	 */
	public IvyIdListener() throws IvyException {
		listeId=new ArrayList();
		bus = new Ivy("IvyIdListener", "IvyIdListener Ready", null);
		bus.bindMsg("^([0-9]+) [A-Za-z0-9]", this);
		//bus.bindMsg("^(.*)",this);
		bus.start(null);
	}
	@Override
	public void receive(IvyClient arg0, String[] args) {
		if (!listeId.contains(Integer.valueOf(args[0]))) {
			listeId.add(Integer.valueOf(args[0]));
		}
	}
	/**
	 * Find the number of the IMU in the list
	 * @param i input IMU number
	 * @return
	 * @throws InterruptedException 
	 */
	public boolean idPresent(int i) throws InterruptedException{
		listeId.clear();
		Thread.sleep(1000);
		return listeId.contains(i);
	}
	/**
	 * return the list of IMU ID (integer) which are currently connected with the Ivy bus
	 * @return
	 */
	public List<Integer> getList(){
		return listeId;
	}
	/**
	 * remove the connection of this client from the Ivy bus
	 * @throws IvyException
	 */
	public void stop() throws IvyException {
		bus.stop();
	}
	
}
