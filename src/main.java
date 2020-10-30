import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");
        
        Binteger z = new Binteger("4344936009049315546");
        Binteger x = new Binteger("4025372428371997405");

        /*
        WORKS:
        small positive, large negative ->
        C1: 1, C2: -1
        false, true
        small negative, large positive ->
        C1: -1, C2: 1
        true, false
        Does not work:
        large negative, small positive -> needs trigger
        C1: -1, c2: 1
        true, false
        -999
         500
        large positive, small negative -> needs trigger
        C1: 1, C2: 1
        */


        long start = System.currentTimeMillis();
        for (int i=0; i<1; i++) {
            Binteger l =  z.subtract(x);
            System.out.println(l.getIsNegative());
            System.out.println(l.toString());
        }
        long end = System.currentTimeMillis();

        System.out.println("ms: " + (end-start));
        
    }

}