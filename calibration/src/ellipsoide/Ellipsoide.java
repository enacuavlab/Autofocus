package ellipsoide;
import java.util.Iterator;
import java.util.List;

import Jama.Matrix;
import data.*;
public class Ellipsoide {
	private Data data;
	private Matrix coefficientEllipsoide;
	Ellipsoide(Data data){
		this.data=data;
	}
	public void ajouter(){
		Matrix A=
		List<Vecteur> list = data.getVecteur();
		Iterator i = list.iterator();
		while(i.hasNext()){
			
		}

	}
}
