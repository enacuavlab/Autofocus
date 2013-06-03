package ellipsoide;

public class test_zone {
	public static void main(String[] args) {
		// test de l'angle theta du repère sphérique
		Zone zone1 = new Zone(Math.PI / 2, 0, 0, Math.PI / 2);
		if (zone1.is_in_long(3, 2, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone2 = new Zone(0, -Math.PI/2, -Math.PI / 2, 0);
		if (zone1.is_in_long(3, -15, 1, 1)) { // ne doit pas etre dans la
													// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_long(3, -15, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone3 = new Zone(Math.PI / 4, 0, -Math.PI, -Math.PI / 2);
		if (zone1.is_in_long(-9, -15, 1, 1)) { // ne doit pas etre dans la
													// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_long(-9, -15, 1, 1)) { // ne doit pas etre dans la
													// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone3.is_in_long(-9, -15, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		Zone zone4 = new Zone(Math.PI / 4, 0, Math.PI / 2, Math.PI);
		if (zone1.is_in_long(-9, 15, 1, 1)) { // ne doit pas etre dans la
													// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_long(-9, 15, 1, 1)) { // ne doit pas etre dans la
													// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone3.is_in_long(-9, 15, 1, 1)) { // ne doit pas etre dans la
													// zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone4.is_in_long(-9, 15, 1, 1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		// test de l'angle Phi du repère sphérique
		if (zone1.is_in_lat(16, 15,12, 1, 1,1)) { // doit etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone1.is_in_lat(16, 15,-12, 1, 1,1)) { // ne doit pas etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
		if (zone2.is_in_lat(16, 15,-12, 1, 1,1)) { // ne doit pas etre dans la zone
			System.out.println("le point est bien dans la zone");
		} else {
			System.out.println("le point n'est bizarement pas dans la zone");
		}
	}

}
