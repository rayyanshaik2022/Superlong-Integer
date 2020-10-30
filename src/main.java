import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");
        
        Binteger z = new Binteger("4344936009049315546");
        Binteger x = new Binteger("-4025372428371997405");


        long start = System.currentTimeMillis();
        for (int i=0; i<1; i++) {
            Binteger l = z.subtract(x);
            //System.out.println(l.toString());
        }
        long end = System.currentTimeMillis();

        System.out.println("ms: " + (end-start));
        
    }

}