import java.util.*;
import java.math.BigInteger;
import java.io.*;

import java.util.Scanner;
import java.io.FileReader;

class Main{
    public static void main(String[] args) throws FileNotFoundException {

        String[] file = readFile("func_data.txt");
        Ginteger a = new Ginteger("0");
        Ginteger b = new Ginteger("0");
        Ginteger output;
        Ginteger answer;

        int c = 0;
        String operator = "";
        for (int i=0; i<file.length; i++) {
            if  (c == 0) {
                operator = file[i].trim();
            }
            else if (c == 1) {
                a = new Ginteger(file[i].trim());
            }
            else if (c == 2) {
                b = new Ginteger(file[i].trim());
            }
            else if (c == 3) {
                output = new Ginteger(file[i].trim());
                if (operator.equals("+")) {
                    answer = a.add(b);
                }
                else if (operator.equals("-")) {
                    answer = a.subtract(b);
                }
                else if (operator.equals("*")) {
                    answer = a.multiply(b);
                }
                else if (operator.equals("/")) {
                    answer = a.divide(b);
                }
                else {
                    answer = a.modulo(b);
                }

                String ans = answer.toString().trim();
                String otp = output.toString().trim();
                if (ans.equals(otp)) {
                    System.out.println("Operation " + (i+1) + " Valid");
                }
                else {
                    System.out.println("Invalid Operation:");
                    System.out.println("   > Operation: " + a + " " +operator + " "+b);
                    System.out.println("   > Answer: " + answer);
                    System.out.println("   > Expected: " + output);
                    break;
                }
                
                c -= 4;
            }
            c++;
        }

        /*
        Ginteger a = new Ginteger("12345");
        Ginteger b = new Ginteger("0");

        long start = System.currentTimeMillis();
        for (int i=0; i<1; i++) {
            System.out.println(a.divide(b));
            a.divide(b);
            
        }
        long end = System.currentTimeMillis();
        System.out.println("ms: " + (end-start));
        */

    }

    public static String[] readFile(String f) throws FileNotFoundException{
        Scanner in = new Scanner(new FileReader("./lib/"+f));
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()) {
            sb.append(in.next()+" ");
        }
        in.close();
        
        System.out.println("Analyzing file " + f);
        String[] file = sb.toString().split(" ");
        return file;
    }


}