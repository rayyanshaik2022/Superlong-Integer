import java.util.Arrays;

public class Ginteger {
    public static void main(String[] args) {
        Ginteger a = new Ginteger("4938224890876513426");
        Ginteger b = new Ginteger("6485117280682360484");

        long start = System.currentTimeMillis();
        System.out.println(Arrays.toString(a.chunks));
        System.out.println(Arrays.toString(b.chunks));
        for (int i=0; i<1; i++) {
            System.out.println(Arrays.toString(a.multiply(a.chunks, b.chunks)));
            //a.subtract(a.chunks, b.chunks);
            
        }
        long end = System.currentTimeMillis();
        System.out.println("ms: " + (end-start));

        
    }

    long[] chunks = new long[6];
    String chunkString = null;

    public Ginteger(String n) {

        chunkString  = n;

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
        //TODO toString here?
    }

    public long[] format(long[] a) {
        long[] reformatted = new long[6];

        reformatted[0] = a[0];

        for (int i=1; i<6; i++) {
            reformatted[i] = Math.abs(a[i]);
        }

        return reformatted;
    }
    public int compare(long[] a, long[] b, boolean abs) {
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
    
    public long[] add(long[] a, long[] b) {
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
                    System.out.println(tempSum + " " +carry);
                }
                
                sum[i] = tempSum;
                tempSum = 0;

            }

            if (sum[sum.length-1] < 0) {
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

    public long[] subtract(long a[], long[] b) {
        long[] copy = b.clone();
        copy[0] = b[0] * -1;

        return add(a, copy);
    }

    public long[] simpleMultiply(long[] a, long b, int place) {
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
    
    public long[] multiply(long[] a, long[] b) {
        long[][] matrix = new long[6][6];
        long[] product = new long[6];
        long sign;

        for (int i=5; i>=1; i--) {
            matrix[i] = simpleMultiply(a, b[i], 5-i);
        }
        for (int i=0; i<6; i++) {
            product = add(product, matrix[i]);
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
}
