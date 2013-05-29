package ellipsoide;

import org.ejml.simple.SimpleMatrix;

import data.*;

public class Ellipsoide {
	private SimpleMatrix coefficientEllipsoide;
	private SimpleMatrix A;
	private SimpleMatrix B;

	Ellipsoide(Data data) {
		B = new SimpleMatrix(0, 1);
		A = new SimpleMatrix(0, 9);
		coefficientEllipsoide = new SimpleMatrix(1, 9);
	}

	public void ajouter(Vecteur v) {
		double[][] ctable = { {
				(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) - 2 * Math.pow(
						v.getZ(), 2)),
				(Math.pow(v.getX(), 2) - 2 * Math.pow(v.getY(), 2) + Math.pow(
						v.getZ(), 2)), 4 * v.getX() * v.getY(),
				2 * v.getX() * v.getZ(), 2 * v.getY() * v.getZ(), v.getX(),
				v.getY(), v.getZ(), 1 } };
		SimpleMatrix C = new SimpleMatrix(ctable);
		A.combine(A.numRows(), 0, C);
		double [][] dtable = { {Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2)} };
		SimpleMatrix D= new SimpleMatrix(dtable);
		B.combine(B.numRows(),0,D );
		coefficientEllipsoide=A.solve(B);

	}
	public void print(){
		coefficientEllipsoide.print();
	}
}
