package data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Store the date relative to an aircraft in order to facilitate reuse
 * 
 * @author Alinoe
 * 
 */
public class Aircraft {

	private static Logger logger = Logger.getLogger(Aircraft.class.getName());

	/** Name of the aircraft */
	private String name;
	/** Id of the aircraft */
	private int id;
	/** Path to the setting file of the aircraft */
	private String settings;
	/** The current telemetry mode of the aircraft */
	private int mode;
	/** Says if there is raw_data linked to this airplane */
	private boolean isRaw;
	/** List of available modes for the aircraft */
	private List<String> modes;
	/** index telemetry for dlvalues messages */
	private int indexTelemetry;
	/** indicates if connected or not */
	private boolean connected;

	/**
	 * create the aircraft, no default constructor
	 * 
	 * @param name
	 * @param id
	 * @param settings
	 * @param mode
	 */
	public Aircraft(String name, int id, String settings, int mode,
			List<String> modes, int indexTelemetry) {
		this.name = new String(name);
		this.id = id;
		this.settings = new String(settings);
		this.mode = mode;
		this.modes = new ArrayList<String>(modes);
		this.indexTelemetry = indexTelemetry;
		this.connected = false;
	}

	public boolean isConnected() {
		return connected;
	}

	public void connected(boolean b) {
		this.connected = b;
	}

	public boolean equals(Aircraft ac) {
		return ac.getId() == this.id;
	}

	/**
	 * return the name of the aircraft
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * return a path so settings of the aicraft
	 * 
	 * @return settings path
	 */
	String getSettings() {
		return settings;
	}

	/**
	 * return the id of the aircraft
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * return the cursor on the current telemetry mode
	 * 
	 * @return mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * set the value of isRaw
	 * 
	 */
	public void setRaw(boolean b) {
		isRaw = b;
	}

	/**
	 * set the current telemetry mode
	 * 
	 * @param m
	 */
	public void setMode(int m) {
		mode = m;
	}

	/**
	 * get is raw data
	 * 
	 */
	public boolean isRawData() {
		return isRaw;
	}

	/**
	 * return the name in order to override tostring
	 */
	@Override
	public String toString() {
		return name;
	}

	/** List modes getter */
	public List<String> getModes() {
		return modes;
	}

	public int getIndexTelemetry() {
		return indexTelemetry;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setModes(List<String> modes) {
		this.modes = modes;
	}

	public void setIndexTelemetry(int indexTelemetry) {
		this.indexTelemetry = indexTelemetry;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

}
