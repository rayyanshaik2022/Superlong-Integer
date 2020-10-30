import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");
        
        Binteger z = new Binteger("7779011542987490619");
        Binteger x = new Binteger("1059828923740232915");

        long start = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            Binteger l = z.multiply(x);
            //System.out.println(l.toString());
        }
        Binteger l = z.multiply(x);
        System.out.println(l.toString());
        long end = System.currentTimeMillis();

        System.out.println("ms: " + (end-start));
        
    }

}