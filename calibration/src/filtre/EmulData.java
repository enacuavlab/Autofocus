package filtre;

import java.util.*;

class EmulData {

	List<Vecteur> db = new ArrayList<Vecteur>();

	// pattern singleton
	static EmulData instance;

	private EmulData() {
	}

	/** creates a new Data */
	static protected EmulData CreateStorage() {
		if (instance == null) {
			instance = new EmulData();
			return instance;
		} else
			return instance;
	}

	/**
	 * simulates the call to filter and the storage
	 * 
	 * @param data
	 *            to store
	 */
	protected void store(int toStore) {
		db.add(new Vecteur(toStore));
	}

	public String toString() {
		Iterator<Vecteur> iter = db.iterator();
		String res = null;
		while (iter.hasNext()) {
			res = res + "\n" + iter.next();
		}
		return res;
	}
}
