package iddrone;

import fr.dgac.ivy.IvyException;

public class TestIvyIdlistener {
	
	public static void main(String args[]) throws IvyException, InterruptedException {
		IvyIdListener iil =new IvyIdListener();
		if(iil.idPresent(6)){
			System.out.println("present");
		}
		else{
			System.out.println("absent");
		}
	}
}
