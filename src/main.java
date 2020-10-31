import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");
        
        Binteger z = new Binteger("4025372428371997405");
        Binteger x = new Binteger("4344936009049315546");
        long q;

        long start = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {

            z.add(x);
            //z.multiply(x);
            //System.out.println(l.toString());
        }
        long end = System.currentTimeMillis();

        //System.out.println(x.toDouble(x.val));

        System.out.println("ms: " + (end-start));
        
    }

}