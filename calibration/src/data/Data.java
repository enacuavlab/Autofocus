package data;

import java.util.*;
import common.TypeCalibration;

public class Data {

HashMap hm = new HashMap();

public Data(TypeCalibration calibration){

}

public void store(Vecteur v){
hm.put(v);
}

public static void main(String [] args){
Data dt = new Data(ACCELEROMETER)
}
}
