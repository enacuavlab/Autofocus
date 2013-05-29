package ellipsoide;

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
		A = A.combine(A.numRows()+1, 1, C);
		double [][] dtable = { {Math.pow(b2.getX(), 2) + Math.pow(b2.getY(), 2) + Math.pow(b2.getZ(), 2)} };
		SimpleMatrix D= new SimpleMatrix(dtable);
		B.combine(B.numRows()+1,1,D );
		if(A.numRows()>5){
			coefficientEllipsoide=A.solve(B);
		}

	}
	public void print(){
		A.printDimensions();
		coefficientEllipsoide.print();
	}
}
