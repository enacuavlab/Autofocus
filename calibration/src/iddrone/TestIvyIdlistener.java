package iddrone;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import fr.dgac.ivy.IvyException;

public class TestIvyIdlistener {
	
	public static void main(String args[]) throws IvyException, InterruptedException {
		List<Integer> l= new ArrayList<Integer>();
		IvyIdListener iil =new IvyIdListener();
		
		l=iil.getList();
		ListIterator<Integer> i =l.listIterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		System.out.println("fin de la liste");
	}
}
