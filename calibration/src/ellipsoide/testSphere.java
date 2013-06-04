package ellipsoide;

import java.util.List;
import java.util.ListIterator;

import data.Vecteur;

public class testSphere {
	public static  void main(String[] args){
		Sphere s = new Sphere(20,10);
		Vecteur center= new Vecteur(5,10,15);
		Vecteur v = new Vecteur(8,20,21);
		s.update(10.5, center, v);
		ListIterator<Zone> j= s.getZones().listIterator();
		while (j.hasNext()){
			if(j.next().getNbPoints()==1){
				System.out.println("point rentré");	
			}
			
		}
		
	}

}
