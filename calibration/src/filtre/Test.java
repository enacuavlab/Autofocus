package filtre;

public class Test {
	
	public static void main(String[] args) {
		AleatGen aleat = new AleatGen(20,10);
		//first loop for initial value
		for (int i = 0; i < 20; i++) {
			aleat.store();
		}
		//second loop for transition values
		aleat = new AleatGen(50,20);
		for (int i = 0; i < 10; i++) {
			aleat.store();
		}
		//third loop for end value
		aleat = new AleatGen(100,10);
		for (int i = 0; i < 10; i++) {
			aleat.store();
		}
		//print result
		EmulData db = EmulData.CreateStorage();
		System.out.println(db);
	}
}