package calibTest;

import data.Vecteur;

public class TestOptimize {
	
	public static void main(String args[]) {
		double radius = 1;
		Vecteur bias = new Vecteur(251, 209, 243);
		Vecteur sensibility = new Vecteur(5, 5, 5);
		jeuDeTest test = new jeuDeTest(radius, bias, sensibility, 15.0, 1000);
		new Optimize(test.getListclean()).optimize();
	}

}
