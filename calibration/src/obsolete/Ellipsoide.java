package obsolete;

import org.ejml.simple.SimpleMatrix;

import filtre.VecteurFiltrable;

public class Ellipsoide {
	private SimpleMatrix coefficientEllipsoide;
	private SimpleMatrix A;
	private SimpleMatrix B;

	public Ellipsoide() {
		B = new SimpleMatrix(1, 1);
		A = new SimpleMatrix(1, 9);
		coefficientEllipsoide = new SimpleMatrix(1, 9);
	}
	
	public void add(VecteurFiltrable<Double> b2) {
		double[][] ctable = { {
				(Math.pow(b2.getX(), 2) + Math.pow(b2.getY(), 2) - 2 * Math.pow(
						b2.getZ(), 2)),
				(Math.pow(b2.getX(), 2) - 2 * Math.pow(b2.getY(), 2) + Math.pow(
						b2.getZ(), 2)), 4 * b2.getX() * b2.getY(),
				2 * b2.getX() * b2.getZ(), 2 * b2.getY() * b2.getZ(), b2.getX(),
				b2.getY(), b2.getZ(), 1 } };
		SimpleMatrix C = new SimpleMatrix(ctable);
		A = A.combine(SimpleMatrix.END, 0, C);
		double [][] dtable = { {Math.pow(b2.getX(), 2) + Math.pow(b2.getY(), 2) + Math.pow(b2.getZ(), 2)} };
		SimpleMatrix D= new SimpleMatrix(dtable);
		B=B.combine(SimpleMatrix.END,0,D );
		try {
		if(A.numRows()>9){
			coefficientEllipsoide=A.solve(B);
			}
		} catch (Exception e){
			System.out.println("pas encore assez de valeurs");
		}

	}
	public void print(){
		//A.printDimensions();
		//B.printDimensions();
		//A.print();
		coefficientEllipsoide.transpose().print();
	}
}
