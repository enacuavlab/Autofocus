package obsolete;

import imu.GetConfigException;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyConfigListener {

	private Ivy bus;
	private int id;
	private int reqid = 176;
	private String settings;
	private String acName;

	/**Creates a client on the Ivy bus in charge to send request for config
	 * message relative to the drone given in parameter and to collect ac_name
	 * and settings
	 * @param idDrone
	 * @throws IvyException
	 * @throws InterruptedException
	 */
	public IvyConfigListener(final int idDrone) throws GetConfigException {
		id = idDrone;
		bus = new Ivy("IvyConfigListener", "IvyConfigListener Ready", null);
		try {
			bus.start(null);
		Thread.sleep(20);
		sendRequest();
		} catch (Exception e) {
			throw new GetConfigException("problem of bus creation",e);
		}
	}

	/**
	 * Bind to a config message once and then send a request message which triggers
	 * the send of the message
	 * @throws IvyException
	 * @throws InterruptedException
	 */
	public void sendRequest() throws IvyException, InterruptedException {
		// busBind only once
		bus.bindMsgOnce(("^" + reqid + " " + "[A-Za-z0-9]+ CONFIG (.*)"),
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						//System.out.println(args[0]);
						String temp[] = args[0].split(" ");
						settings = temp[4];
						acName = temp[6];
					}
				});
		Thread.sleep(20);
		bus.sendMsg("calibrate " + reqid + " CONFIG_REQ " + id);
		Thread.sleep(1000);
		reqid++;

	}

	/** Returns the aircraft linked to the id 
	 * 
	 * @throws getConfigException
	 * @return aircraft name
	 */
	public String getAcName() throws GetConfigException {
		if (acName == null) throw new GetConfigException();
		else return acName;
	}

	/** Returns the url of the settings.xml file of the aircraft
	 * 
	 * @throws GetConfigException
	 * @return url to settings.xml
	 */
	public String getSettingsURL() throws GetConfigException {
		if (settings == null) throw new GetConfigException();
		else return settings.substring(7);
	}

	/** Updates the values of the aircraft name and the settings.xml's url
	 * 
	 * @throws GetConfigException
	 */
	public void update() throws GetConfigException {
		try {
			this.sendRequest();
		} catch (IvyException e) {
			throw new GetConfigException("Problème de lancement de bus",e);
		} catch (InterruptedException e) {
			throw new GetConfigException("Problème de lancement du thread d'écoute",e);
		}
	}
	
	/** Kill the listener on the bus once the object collected by the garbage
	 * 
	 */
	@Override
	public void finalize() {
		bus.stop();
	}

	/**Test function
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			IvyConfigListener listen = new IvyConfigListener(5);
			System.out.println(listen.getAcName());
			System.out.println(listen.getSettingsURL());
			listen.finalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
