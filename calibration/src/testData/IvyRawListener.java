package testData;



import java.util.List;

import fr.dgac.ivy.*;

public class IvyRawListener implements IvyMessageListener{

	private Ivy bus;

	IvyRawListener(final int id_drone,final int index_telemetry) throws IvyException {
		bus = new Ivy("IvyRawListener", "IvyRawListener Ready", null);
	}
	
	/**
	 *  permit to send a message to the drone in order to set the new telemetry mode.
	 * @param id id of the drone
	 * @param numbermode mode of telemetry
	 * @throws IvyException 
	 */
	public void sendRawMessage(int x,int y,int z) throws IvyException{
		bus.sendMsg("calibrate IMU_MAG_RAW "+x+" "+y+" "+z);
	}


	public void stop() throws IvyException {
		bus.stop();
	}

	@Override
	public void receive(IvyClient arg0, String[] arg1) {
		// TODO Auto-generated method stub
		
	}
}
