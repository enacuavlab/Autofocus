package imu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.Timer;

import rawmode.ExtractRawData;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IMU {
	
	/**list of the names of the connected Aircraft*/
	private Set<Aircraft> acL;
	/**The associated ComboBox*/
	private JComboBox<Aircraft> acCombo;
	/**Timer to refresh AcCombo with acL*/
	private Timer acTimer;
	/**bus to which the IMU is connected*/
	private static Ivy bus;
	
	/**Method returning name list*/
	public List<String> getNames() {
		List<String> l = new ArrayList<String>();
		for(Aircraft ac : acL) {
			l.add(ac.getName());
		}
		return l;
	}
	
	/**Method to refresh aircraft list
	 * 
	 */
	public void refresh() {
		
	}
	
	private Aircraft buildAc(final int acId, int reqid) {
		final Aircraft ac = new Aircraft("",0,"",0,new ArrayList<String>(),0);
		try {
		bus.bindMsgOnce(("^" + reqid + " " + "[A-Za-z0-9]+ CONFIG (.*)"),
				new IvyMessageListener() {
					public void receive(IvyClient arg0, String[] args) {
						String temp[] = args[0].split(" ");
						final List<String> listMod = new ArrayList<String>();
						int i=0;
						try {
							// Detect all the available modes of the drone
							ExtractRawData d = new ExtractRawData(String.valueOf(temp[4].subSequence(7,temp[4].length())));
							for (String s : d.extract()) {
								listMod.add(s);
							}
							i = d.getIndex();
						} catch (Exception e) {
							System.out.println("Extract mode issue, modes list probably wrong , file \""+temp[4]+"\"");
							e.printStackTrace();
						}
						ac.copy(new Aircraft(temp[6], acId, temp[4], 0, listMod, i));
					}
				});
			Thread.sleep(20);
			bus.sendMsg("calibrate " + reqid + " CONFIG_REQ " + acId);
			Thread.sleep(20);
		} catch (InterruptedException e) {
			System.out.println("thread sleep fail in IMU, aircraft list probably incorrect");
			e.printStackTrace();
		} catch (IvyException e) {
			System.out.println("Ivy reception failed in IMU, aircraft list probably incorrect");
			e.printStackTrace();
		}
		System.out.println("new aircraft built");
		return ac;
	}
	
	/**Method used to keep update the list of all connected aicraft*/
	private void refreshAllAc() {
		final int reqid = 42;
		try {
			String test = new String("^ground NEW_AIRCRAFT ([0-9]*)");
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					System.out.println("new aircraft detected");
					acL.add(buildAc(Integer.valueOf(args[0]),reqid));
					acCombo.setModel(new DefaultComboBoxModel<Aircraft>(acL.toArray(new Aircraft[acL.size()])));
				}
			});
			test = new String("^ground AIRCRAFT_DIE ([0-9]*)");
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					System.out.println("aircraft dies");
					int i = 0;
					int res = 0;
					for (Aircraft ac : acL) {
						if (ac.getId() == Integer.valueOf(args[0]).intValue()) {
							res = i;
						}
						i++;
					}
					acL.remove(res);
					acCombo.setModel(new DefaultComboBoxModel<Aircraft>(acL.toArray(new Aircraft[acL.size()])));
				}
			});
			Thread.sleep(20);
			test = new String("^ground" + " DL_VALUES ([0-9]+) (.*)");
			bus.bindMsg(test, new IvyMessageListener() {
						public void receive(IvyClient arg0, String[] args) {
							for (Aircraft ac : acL) {
								if (Integer.valueOf(args[0]).equals(ac.getId())) {
									double temp = Double.valueOf((args[1].split(","))[ac.getIndexTelemetry()]);
									ac.setMode((int)temp);
								}
							}
						}
			});
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getAllAc() {
		final int reqid = 42;
		try {
			bus.bindMsgOnce(("^" + reqid + " " + "ground AIRCRAFTS (.*)"),
					new IvyMessageListener() {
						public void receive(IvyClient arg0, String[] args) {
							String temp[] = args[0].split(",");
							for (String id : temp){
								acL.add(buildAc(Integer.valueOf(id), reqid));
								acCombo.setModel(new DefaultComboBoxModel<Aircraft>(acL.toArray(new Aircraft[acL.size()])));
							}
						}
					});
			Thread.sleep(20);
			bus.sendMsg("IMU " + reqid + " AIRCRAFTS_REQ");
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listenAllAc() {
		getAllAc();
		refreshAllAc();
	}
	
	public void stopListenAllId() {
		acTimer.stop();
		bus.unBindMsg("^ground NEW_AIRCRAFT ([0-9]*)");
		bus.unBindMsg("^ground AIRCRAFT_DIE ([0-9]*)");
		bus.unBindMsg("^ground" + " DL_VALUES ([0-9]+) (.*)");
	}
	
	/**Set the comboBox displaying Aircraft
	 * 
	 * @param box
	 */
	public void setAcCombo(JComboBox<Aircraft> box) {
		this.acCombo=box;
		listenAllAc();
	}
	
	
	public IMU() {
		bus = new Ivy("IMU", "IMU Ready", null);
		try {
			bus.start(null);
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acL = new HashSet<Aircraft>();
	}
	
	/**Test method*/
	public static void main(String args[]) {
		new IMU();
		String test = new String("(.*)AIR(.*)");
		try {
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					System.out.println(args[0] + "AIR" + args[1]);
				}
			});
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
