import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");

        Binteger z = new Binteger("100000");
        Binteger x = new Binteger("-1");

        long start = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            z.add(x);
        }
        long end = System.currentTimeMillis();

        System.out.println("ms: " + (end-start));

        /*
        BigInteger q = new BigInteger("-4025372428371997405");
        BigInteger p = new BigInteger("-4344936009049315546");

        start = System.currentTimeMillis();
        for (int i = 0; i<1000000; i++) {
            q.add(p);
        }
        end = System.currentTimeMillis();
        System.out.println(end-start + " milliseconds");
        */

        
    }

}