package filtre;

import java.util.*;

class EmulData implements Iterable<Vecteur> {

	List<Vecteur> db = new ArrayList<Vecteur>();
	Filter<Vecteur> filtre;

	/** creates a new Data */
	protected EmulData(Filter<Vecteur> filtre) {
		this.filtre = filtre;
	}

	/**
	 * simulates the call to filter and the storage
	 * 
	 * @param data
	 *            to store
	 */
	protected void store(int toStore) {
		Vecteur v = new Vecteur(toStore);
		db.add(v);
		filtre.add(v);
	}

	public Iterator<Vecteur> iterator(){
		return db.iterator();
	}
	
	public String toString() {
		Iterator<Vecteur> iter = db.iterator();
		String res = null;
		int i = 1;
		while (iter.hasNext()) {
			res = res + "\n" + i + " " + iter.next();
			i++;
		}
		return res;
	}
}
