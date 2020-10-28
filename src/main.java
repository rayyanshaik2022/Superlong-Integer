import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        SuperLong a = new SuperLong("-4025372428371997405");
        SuperLong b = new SuperLong("-4344936009049315546");

        String z;
        long start = System.currentTimeMillis();
        for (int i = 0; i<1000; i++) {
            z = a.old("-4025372428371997405", "-4344936009049315546");
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop-start + " milliseconds");

        start = System.currentTimeMillis();
        for (int i = 0; i<1000; i++) {
            SuperLong c = a.add(b);
        }
        stop = System.currentTimeMillis();
        System.out.println(stop-start + " milliseconds");
        SuperLong c = a.add(b);
        System.out.println(c);
    }

}