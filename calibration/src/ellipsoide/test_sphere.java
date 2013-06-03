package ellipsoide;

import data.Vecteur;

public class test_sphere {
	public static  void main(String[] args){
		Sphere s = new Sphere(20,10);
		Vecteur center= new Vecteur(5,10,15);
		Vecteur v = new Vecteur(8,14,21);
		s.update(10.5, center, v);
	}

}
