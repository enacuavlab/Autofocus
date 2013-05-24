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
	
	
	IvyIdListener() throws IvyException, InterruptedException {
		listeId=new ArrayList();
		bus = new Ivy("IvyIdListener", "IvyIdListener Ready", null);
		bus.bindMsg("^([0-9]+)", this);
		bus.start(null);
		bus.stop();
	}

	@Override
	public void receive(IvyClient arg0, String[] args) {
		if (!listeId.contains(Integer.valueOf(args[0]))) {
			listeId.add(Integer.valueOf(args[0]));
			System.out.println(args[0]);
		}
	}
	
	public boolean present(int i){
		return listeId.contains(i);
	}
	
	public List<Integer> getList(){
		return listeId;
	}
	
	public void Stop() throws IvyException {
		bus.stop();
	}
	
	public static void main(String args[]) throws IvyException, InterruptedException {
		IvyIdListener iil =new IvyIdListener();	
		System.out.println("listener demare");
		Thread.sleep(500);
		if(iil.present(6)){
			System.out.println("present");
		}
		else{
			System.out.println("absent");
		
		}
		
	}
	
}
