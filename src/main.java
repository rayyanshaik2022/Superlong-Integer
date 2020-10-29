import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //SuperLong a = new SuperLong("4025372428371997405");
        //SuperLong b = new SuperLong("-4344936009049315546");
        //SuperLong m = new SuperLong("4344936009099999999");

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");

        Binteger z = new Binteger("-9999");
        Binteger x = new Binteger("9997");
        //  434493600904 931 5546
        //- 402537242837 199 7405

        //  4025372428371 997 405
        //- 4344936009049 315 546


        // 405
        // 546

        long start = System.currentTimeMillis();
        for (int i=0; i<1; i++) {
            z.add(x);
        }
        long end = System.currentTimeMillis();

        System.out.println("ms: " + (end-start));

        //SuperLong c = a.add(b);
        //System.out.println(c.toString());

        /*
        BigInteger q = new BigInteger("-4025372428371997405");
        BigInteger p = new BigInteger("-4344936009049315546");

        start = System.currentTimeMillis();
        for (int i = 0; i<1000000; i++) {
            q.add(p);
        }
        end = System.currentTimeMillis();
        System.out.println(end-start + " milliseconds");

        
        SuperLong c = a.add(b);
        System.out.println(c);

        start = System.currentTimeMillis();
        for (int i = 0; i<0; i++) {
            BigInteger m = q.add(p);
        }
        stop = System.currentTimeMillis();
        System.out.println(stop-start + " milliseconds");
        */

        
    }

}