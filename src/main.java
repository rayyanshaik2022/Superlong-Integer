import java.util.*;

import java.math.BigInteger;

class Main{
    public static void main(String[] args){

        //Binteger z = new Binteger("4025372428371997405");
        //Binteger x = new Binteger("-4344936009049315546");
        
        Binteger z = new Binteger("87428974793282013");
        Binteger x = new Binteger("2149557966519333943");

        long start = System.currentTimeMillis();
        for (int i=0; i<3; i++) {
            Binteger l = z.multiply(x);
            //System.out.println(l.toString());
        }
        Binteger l = z.add(x);
        System.out.println(l.toString());
        long end = System.currentTimeMillis();

        System.out.println("ms: " + (end-start));
        
    }

}