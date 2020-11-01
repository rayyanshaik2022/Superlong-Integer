import java.util.Arrays;
import java.math.BigInteger;

public class Ginteger {
    public static void main(String[] args) {
        Ginteger a = new Ginteger("2617240171759825033");
        Ginteger b = new Ginteger("219811953138254038");

        long start = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {
            //System.out.println(a.modulo(b));
            a.divide(b);
            
        }
        long end = System.currentTimeMillis();
        System.out.println("ms: " + (end-start));

    }

    long[] chunks = new long[6];

    public Ginteger(String n) {

        // Account for if the string is in scientific notation
        if (n.contains(".")) {
            if (n.contains("E")) {
                n = n.replace(".", "");
                int tenCount = Integer.parseInt(n.substring(n.indexOf("E")+1));
                n = n.substring(0, n.indexOf("E")) + "0".repeat(tenCount);
            }
            else if (n.contains(".")) {
                n = n.substring(0, n.indexOf("."));
            }
        }


        if (n.charAt(0) == '-') {
            chunks[0] = -1;
            n = n.substring(1);
        }
        else {
            chunks[0] = 1;
        }

        int counter = 0;
        int arraycounter = 0;
        for (int i=n.length()-1; i>=0; i--) {
            if (counter == 9) {

                // chunks[5-arraycounter] -> 5 is the last index of chunks
                chunks[5-arraycounter] = Long.parseLong(n.substring(i+1,i+10));

                arraycounter++;
                counter = 0;
            }
            counter ++;
        }

        if (counter != 0 && arraycounter != 5) {
            chunks[5-arraycounter] = Long.parseLong(n.substring(0,counter));
        }
    }
    public Ginteger(long[] a) {
        chunks = a;
    }

    public String toString() {
        String val = "";
        for (int i = 5; i>=1; i--) {
            if (this.chunks[i] == 0) {
                continue;
            }
            val = this.chunks[i] + val;
        }
        if (this.chunks[0] == -1) {
            val = "-" + val;
        }
        if (val.equals("-")) {
            return "0";
        }
        else if (val.equals("")) {
            return "0";
        }
        return val;
    }

    public Ginteger add(Ginteger b) {
        return new Ginteger(rawAdd(this.chunks, b.chunks));
    }

    public Ginteger subtract(Ginteger b) {
        return new Ginteger(rawSubtract(this.chunks, b.chunks));
    }

    public Ginteger multiply(Ginteger b) {
        return new Ginteger(rawMultiply(this.chunks, b.chunks));
    }

    public Ginteger divide(Ginteger b) {
        long[] p = this.chunks.clone();
        long[] q = b.chunks.clone();
        p[0] = -1;
        q[0] = -1;

        Ginteger quotient = new Ginteger(rawDivide(p, q));
        quotient.chunks[0] = this.chunks[0] * b.chunks[0];

        return quotient;
    }

    public Ginteger modulo(Ginteger b) {
        Ginteger quotient = divide(b);
    
        Ginteger remainder = subtract(new Ginteger(rawMultiply(b.chunks, quotient.chunks)));
        remainder.chunks[0] = b.chunks[0];
        return remainder;
    }

    private long[] format(long[] a) {
        long[] reformatted = new long[6];

        reformatted[0] = a[0];

        for (int i=1; i<6; i++) {
            reformatted[i] = Math.abs(a[i]);
        }

        return reformatted;
    }
    
    private int compare(long[] a, long[] b, boolean abs) {
        // Starts at 1 to skip the sign value
        if (Arrays.equals(a, b)) {
            return 0;
        }
        if (!abs && a[0] != b[0]) {
            if (a[0] == -1 && b[0] == 1) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else {
            for (int i=1; i<6; i++) {
                if (a[i] > b[i]) {

                    if (abs) {
                        return 1;
                    }

                    if (a[0] == 1) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
                else if (a[i] < b[i]) {

                    if (abs) {
                        return -1;
                    }

                    if (a[0] == 1) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
            }
        }
        
        return 0;
    }

    private long[] rawAdd(long[] a, long[] b) {
        long[] sum = new long[6];

        long tempSum;
        long carry = 0;

        // If they share the same sign
        if (a[0] == b[0]) {
            // i>=1 because we do not want to include first index (sign value)
            for (int i=sum.length-1; i>=1; i--) {

                tempSum = a[i] + b[i];
                tempSum += carry;

                if (tempSum > 999999999) {
                    carry = (tempSum - (tempSum % 1000000000))/1000000000;
                    tempSum = tempSum % 1000000000;
                }
                else {
                    carry = 0;
                }
                
                sum[i] = tempSum;
                tempSum = 0;

            }
            sum[0] = a[0];
        }

        else if (compare(a, b, true) != compare(a, b, false)) {
            for (int i=sum.length-1; i>=1; i--) {

                tempSum = (a[i]*a[0]) + (b[i]*b[0]);
                tempSum += carry;

                if (Math.abs(tempSum) < 999999999) {

                    carry = (tempSum - (tempSum % 1000000000))/1000000000;
                    tempSum = tempSum % 1000000000;
                    if (tempSum > 0) {
                        
                        tempSum = 1000000000 - tempSum;
                        carry += 1;
                    }
                }
                else {
                    carry = 0;
                }

                if ((tempSum) < 0){
                }
                
                sum[i] = tempSum;
                tempSum = 0;

            }

            if (sum[sum.length-1] < 0) {
                sum[0] = 1;
            }
            else {
                sum[0] = -1;
            }
            
        }
        else {

            // i>=1 because we do not want to include first index (sign value)
            for (int i=sum.length-1; i>=1; i--) {

                tempSum = a[i] - b[i];
                tempSum += carry;

                if (tempSum > 999999999) {
                    carry = (tempSum - (tempSum % 1000000000))/1000000000;
                    tempSum = tempSum % 1000000000;
                }
                else {
                    carry = 0;
                }
                
                sum[i] = tempSum;
                tempSum = 0;

            }

            if (sum[sum.length-1] < 0) {
                sum[0] = 1;
            }

        }
        
        return format(sum);
    }

    private long[] rawSubtract(long a[], long[] b) {
        long[] copy = b.clone();
        copy[0] = b[0] * -1;

        return rawAdd(a, copy);
    }

    private long[] simpleMultiply(long[] a, long b, int place) {
        long[] product = new long[6];

        long carry = 0;
        long mult;
        for (int i=5; i>=1; i--) {
            mult = (a[i] * b) + carry;

            if (mult > 999999999) {
                carry = (mult - (mult%1000000000))/1000000000;
                mult = mult%1000000000;
            }
            else {
                carry = 0;
            }
            
            if (i-place > 0) {
                product[i-place] = mult;
            }
            
        }
        if (carry % 1000000000 == 0 && carry != 0) {
            product[place-1] = carry/1000000000;
        }
        else if (carry != 0) {
            product[place-1] = carry % 1000000000;
        }

        return product;
    }
    
    private long[] rawMultiply(long[] a, long[] b) {
        long[][] matrix = new long[6][6];
        long[] product = new long[6];
        long sign;

        for (int i=5; i>=1; i--) {
            matrix[i] = simpleMultiply(a, b[i], 5-i);
        }
        for (int i=0; i<6; i++) {
            product = rawAdd(product, matrix[i]);
        }

        if (a[0] == b[0]) {
            sign = 1;
        }
        else {
            sign = -1;
        }

        product[0] = sign;
        return product;
    }

    private double toDouble(long[] a) {
        double representation = 0;
        
        for (int i=5; i>=1; i--) {
            if (a[i] != 0) {
                representation += a[i] * Math.pow(10,-(i*9));
            }   
        }
        return representation * a[0];
    }

    private String rawDivide(long[] a, long[] b) {
        double aDouble = toDouble(a);
        double bDouble = toDouble(b);

        double sum = 0;

        if(bDouble < aDouble){
            sum =  0.0;
        }
        else if(bDouble > aDouble && b.length == a.length){
            sum = Math.floor(aDouble/bDouble);
        }
        else if(bDouble > aDouble && b.length != a.length){
            int periodIndex = 0;
            int[] x = new int[a.length];
            int difference = Math.abs(b.length - a.length);
            sum = aDouble/bDouble;
            int k = 0;
            String y = Double.toString(sum);
            for(int i = 0; i < y.length() ; i++){
                if(y.charAt(i) != '.'){
                    x[i] = Integer.parseInt(String.valueOf(y.charAt(i)));
                }
                else{
                    periodIndex = i;
                    x[i] = 10;
                }
            }
            for(int i = periodIndex; i < y.length(); i++){

                if(i - periodIndex == difference){
                    break;
                }
                if(x[i] == 10){
                    k = x[i];
                    x[i] = x[i+1];
                    x[i+1] = k;
                }
            }
            String quotient = "";
            for(int i = 0; i < y.length(); i++){
                if(x[i] < 10){
                    quotient += Integer.toString(x[i]);
                }
                else if(x[i] == 10){
                    quotient += ".";
                }
                

            }
        return quotient;
        }
        else{
            sum =  1.0;
        }

        return Double.toString(sum);

    }

}
