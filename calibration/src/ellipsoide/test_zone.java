package ellipsoide;

public class test_zone {
	public static void main(String[] args){
		Zone zone1 = new Zone(Math.PI/4,0,-0.3,0.5);
		if(zone1.est_dans_long(3,2,1,1)){
			System.out.println("le point est bien dans la zone");
		}
		else{
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		
	}

}
