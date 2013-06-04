package ellipsoide;

public class Density {
	int nbMax=3000;
	int colorParameter; // in [0;255}
	double nb;
	public Density(){
		nb=0;
		colorParameter=0;
	}
	/**
	 * Calcul of the density thanks to the surface of the sphere and the one of zone
	 * @param surfaceSphere
	 * @param surfaceZone
	 */
	public void updateDensity(double surfaceSphere,double surfaceZone){
		nb+=1;
		System.out.print("{ "+surfaceSphere +" , "+ surfaceZone+" }");
		double temp =(surfaceSphere*nb*255)/(3000*surfaceZone*nbMax);
		if (temp >255){
			colorParameter=255;
		}
		else{
			colorParameter=(int)temp;
		}

		System.out.print(colorParameter);
	}
	/**
	 * reset the counter of the density 
	 */
	public void reset(){
		nb = 0;
	}
	/**
	 * return the color of the zone
	 * @return return the matching color of the density 
	 */
	public int getColor(){
		return colorParameter;
	}
}
