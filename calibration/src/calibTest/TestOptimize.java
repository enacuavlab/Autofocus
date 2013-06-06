package calibTest;

import data.Vecteur;

public class TestOptimize {
	
	public static void main(String args[]) {
		double radius = 1;
		Vecteur bias = new Vecteur(200, 250, 300);
		Vecteur sensibility = new Vecteur(1, 5, 3);
		jeuDeTest test = new jeuDeTest(radius, bias, sensibility, 15.0, 1000);
		new Optimize(test.getListnoised()).optimize();
	}
}
