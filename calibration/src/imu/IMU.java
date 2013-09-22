package imu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;

import rawmode.ExtractRawData;
import calibrate.PrintLog;

import common.TypeCalibration;

import data.Data;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IMU {

	/** list of the names of the connected Aircraft */
	private Set<Aircraft> acL;
	/** bus to which the IMU is connected */
	private static Ivy bus;
	/** Listeners store */
	private final EventListenerList listeners = new EventListenerList();
	/** for request */
	final Integer reqid = 42;
	/** used to update the presence of any aircraft */
	private Hashtable<Integer, Timer> timerPresence;
	/** used to update Raw presence for every aircraft*/
	private Hashtable<Integer, Timer> timerRaw;

	/** Method returning name list */
	public List<String> getNames() {
		List<String> l = new ArrayList<String>();
		for (Aircraft ac : acL) {
			l.add(ac.getName());
		}
		return l;
	}

	/**
	 * Method to refresh aircraft list
	 * 
	 */
	public void refresh() {

	}

	public void addIMUListener(IMUListener imuL) {
		listeners.add(IMUListener.class, imuL);
	}

	public void removeIMUListener(IMUListener imuL) {
		listeners.remove(IMUListener.class, imuL);
	}

	public IMUListener[] getIMUListeners() {
		return listeners.getListeners(IMUListener.class);
	}

	protected void fireAircraftConnected(final Aircraft ac) {
		timerPresence.get(ac.getId()).start();
		for (IMUListener imuL : this.getIMUListeners()) {
			imuL.aircraftConnected(ac);
		}
	}
	
	protected void fireAircraftRawOn(Aircraft ac) {
		for (IMUListener imuL : this.getIMUListeners()) {
			imuL.aircraftRawOn(ac);
		}
	}

	protected void fireAircraftRawOff(Aircraft ac) {
		for (IMUListener imuL : this.getIMUListeners()) {
			imuL.aircraftRawOff(ac);
		}
	}
	
	protected void fireAircraftModChanged(Aircraft ac) {
		for (IMUListener imuL : this.getIMUListeners()) {
			imuL.aircraftModChanged(ac);
		}
	}

	protected void fireAircraftExited(Aircraft ac) {
		timerPresence.get(ac.getId()).stop();
		for (IMUListener imuL : this.getIMUListeners()) {
			imuL.aircraftExited(ac);
		}
	}

	private Aircraft buildAc(final int acId, int reqid) {
		final Aircraft ac = new Aircraft("", acId, "", 0, new ArrayList<String>(),
				0);
		try {
			bus.bindMsgOnce(("^" + reqid + " " + "[A-Za-z0-9]+ CONFIG (.*)"),
					new IvyMessageListener() {
						public void receive(IvyClient arg0, String[] args) {
							String temp[] = args[0].split(" ");
							final List<String> listMod = new ArrayList<String>();
							int i = 0;
							try {
								// Detect all the available modes of the drone
								ExtractRawData d = new ExtractRawData(String
										.valueOf(temp[4].subSequence(7,
												temp[4].length())));
								for (String s : d.extract()) {
									listMod.add(s);
								}
								i = d.getIndex();
							} catch (Exception e) {
								System.out
										.println("Extract mode issue, modes list probably wrong , file \""
												+ temp[4] + "\"");
								e.printStackTrace();
							}
							ac.copy(new Aircraft(temp[6], acId, temp[4], 0,
									listMod, i));
						}
					});
			Thread.sleep(20);
			bus.sendMsg("calibrate " + reqid + " CONFIG_REQ " + acId);
			Thread.sleep(20);
			// Creates a timer to check presence
			timerPresence.put(new Integer(acId), new Timer(2000,
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (ac.isConnected()) {
								fireAircraftExited(ac);
								ac.connected(false);
								System.out.println(ac + " has exited");
							}
						}
					}));
			// add a listener to the telemetry mode of the aircraft
			bus.bindMsg("^ground" + " DL_VALUES ([0-9]+) (.*)",
					new IvyMessageListener() {
						public void receive(IvyClient arg0, String[] args) {
							if (Integer.valueOf(args[0]).equals(
									Integer.valueOf(acId)) &&
									ac.getMode() != (Double.valueOf(
											args[1].split(",")[ac
																.getIndexTelemetry()])
														.intValue()) ) {
								ac.setMode(Double.valueOf(
										args[1].split(",")[ac
												.getIndexTelemetry()])
										.intValue());
								fireAircraftModChanged(ac);
							}
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// it is considered that the two first DL_SETTINGS
							// of
							// the .XML of
							// the drone are unused
						}
					});
			//Creates timer to check raw
			timerRaw.put(new Integer(acId), new Timer(2000,
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							fireAircraftRawOff(ac);
							ac.setRaw(false);
							System.out.println(ac + " has stopped emit raw data");
						}
					}));
			//Creates associated listener
			bus.bindMsg("^" + acId + " IMU_[A-Z]+_RAW(.*)",
					new IvyMessageListener() {
						public void receive(IvyClient arg0, String[] args) {
							try {
								Thread.sleep(20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (!ac.getIsRawData()) {
								ac.setRaw(true);
								fireAircraftRawOn(ac);
							}
							timerRaw.get(acId).restart();
						}
					});
		} catch (InterruptedException e) {
			System.out
					.println("thread sleep fail in IMU, aircraft list probably incorrect");
			e.printStackTrace();
		} catch (IvyException e) {
			System.out
					.println("Ivy reception failed in IMU, aircraft list probably incorrect");
			e.printStackTrace();
		}
		// TODO
		try {
			String test = new String("^" + acId);
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					if (!ac.isConnected()) {
						fireAircraftConnected(ac);
						ac.connected(true);
					}
					timerPresence.get(acId).restart();
				}
			});
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("new aircraft built");
		return ac;
	}
	
	public static void deleteAc(Aircraft ac) {
		bus.unBindMsg("^ground" + " DL_VALUES ([0-9]+) (.*)");
		bus.unBindMsg("^" + ac.getId() + " IMU_[A-Z]+_RAW(.*)");
		bus.unBindMsg("^" + ac.getId());
	}

	/** Method used to keep update the list of all connected aicraft */
	private void refreshAllAc() {
		try {
			String test = new String("^ground NEW_AIRCRAFT ([0-9]*)");
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					System.out.println("new aircraft detected");
					acL.add(buildAc(Integer.valueOf(args[0]), reqid));
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
							fireAircraftExited(ac);
							IMU.deleteAc(ac);
						}
						i++;
					}
					acL.remove(res);
				}
			});
			Thread.sleep(20);
			test = new String("^ground" + " DL_VALUES ([0-9]+) (.*)");
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
							for (String id : temp) {
								acL.add(buildAc(Integer.valueOf(id), reqid));
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
		bus.unBindMsg("^ground NEW_AIRCRAFT ([0-9]*)");
		bus.unBindMsg("^ground AIRCRAFT_DIE ([0-9]*)");
		bus.unBindMsg("^ground" + " DL_VALUES ([0-9]+) (.*)");
		for (Aircraft ac : acL) {
			deleteAc(ac);
		}
	}
	
	public void stopListenAllId(Aircraft acToSave) {
		bus.unBindMsg("^ground NEW_AIRCRAFT ([0-9]*)");
		bus.unBindMsg("^ground AIRCRAFT_DIE ([0-9]*)");
		bus.unBindMsg("^ground" + " DL_VALUES ([0-9]+) (.*)");
		for (Aircraft ac : acL) {
			if (!ac.equals(acToSave)) {
				deleteAc(ac);
			}
		}
	}

	/**
	 * method called to listen the RAW DATA messages on the IVY bus
	 * 
	 * @param data
	 * @param calibration
	 */
	public void ListenRaw(final Data data, final TypeCalibration calibration,
			final PrintLog log, final int idDrone) {
		System.out.println("listenRaw for " + idDrone);
		try {

			// build the regexp according to parameters
			StringBuffer regexp = new StringBuffer("^");
			regexp.append(idDrone);
			regexp.append(TypeCalibration.MAGNETOMETER.equals(calibration) ? " IMU_MAG_RAW"
					: " IMU_ACCEL_RAW");

			regexp.append(" ([\\-]*[0-9]+)");
			regexp.append(" ([\\-]*[0-9]+)");
			regexp.append(" ([\\-]*[0-9]+)");
			String test = regexp.toString();
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					data.store(Integer.valueOf(args[0]),
							Integer.valueOf(args[1]), Integer.valueOf(args[2]));
					log.add(idDrone
							+ (TypeCalibration.MAGNETOMETER.equals(calibration) ? " IMU_MAG_RAW"
									: " IMU_ACCEL_RAW") + " " + args[0] + " "
							+ args[1] + " " + args[2]);
				}
			});

		} catch (Exception e) {
			System.out.println("Erreur d'initialisation d'IMU");
			e.printStackTrace();
		}
	}
	
	/**
	 * unbind the imu from data messages
	 * 
	 * @param calibration
	 *            type of the current calibration
	 */
	public void stopListenRaw(final TypeCalibration calibration,
			final PrintLog log, final int idDrone) {
		StringBuffer regexp = new StringBuffer("^");
		regexp.append(idDrone);
		regexp.append(TypeCalibration.MAGNETOMETER.equals(calibration) ? " IMU_MAG_RAW"
				: " IMU_ACCEL_RAW");

		regexp.append(" ([\\-]*[0-9]+)");
		regexp.append(" ([\\-]*[0-9]+)");
		regexp.append(" ([\\-]*[0-9]+)");
		String test = regexp.toString();
		bus.unBindMsg(test);
		System.out.println("stoplistenRAW");
	}
	
	
	public Aircraft[] getAcs() {
		return acL.toArray(new Aircraft[1]);
	}

	public void changeAcMode(int mode, Aircraft ac) {
		ac.setMode(mode);
		try {
			bus.sendMsg("calibrate DL_SETTING " + ac.getId() + " 0 " + mode);
		} catch (IvyException e) {
			System.out.println("failed sending new mode");
			e.printStackTrace();
		}
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
		timerPresence = new Hashtable<Integer, Timer>();
		timerRaw = new Hashtable<Integer, Timer>();
	}

	/** Test method */
	public static void main(String args[]) {
		new IMU();
		String test = new String("(.*)");// + " IMU_[A-Z]+_RAW(.*)");
		try {
			bus.bindMsg(test, new IvyMessageListener() {
				public void receive(IvyClient arg0, final String args[]) {
					System.out.println(args[0]);
				}
			});
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
