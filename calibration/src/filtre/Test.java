package filtre;

public class Test {
	
	public static void main(String[] args) {
		Filter<Vecteur> filtre = new Filter<Vecteur>();
		EmulData db = new EmulData(filtre);
		ihm.Plotter plot = new ihm.Plotter();
		
		/*Vecteur v1 = new Vecteur(1);
		Vecteur v2 = new Vecteur(2);
		System.out.println(v1.equals(v2));
		*/
		
		AleatGen aleat = new AleatGen(20,10,db);
		//first loop for initial value
		for (int i = 0; i < 20; i++) {
			aleat.store();
		}
		//second loop for transition values
		aleat = new AleatGen(50,50,db);
		for (int i = 0; i < 10; i++) {
			aleat.store();
		}
		//third loop for end value
		aleat = new AleatGen(100,10,db);
		for (int i = 0; i < 10; i++) {
			aleat.store();
		}
		//print result
		int i = 1;
		plot.execute();
		for(Vecteur e : db){
			plot.drawPoint(i,e.getObject());
		}
		System.out.println(db);
		
	}
}