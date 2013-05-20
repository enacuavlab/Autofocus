package filtre;

class EmulData {
	
	static EmulData instance;
	
	private EmulData(){}
	
	static protected EmulData CreateStorage() {
		if (instance == null){
			instance = new EmulData();
			return instance;
		}
		else return instance;
	}
	
	protected void store(int toStore){
		System.out.println(toStore);
	}
	
}
