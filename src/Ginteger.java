import java.util.Arrays;

public class Ginteger {
    public static void main(String[] args) {
        Ginteger a = new Ginteger("-4475415898249202558");
        Ginteger b = new Ginteger("5570207363899646880");

        long start = System.currentTimeMillis();
        for (int i=0; i<1; i++) {
            a.add(a.chunks, b.chunks);
        }
        long end = System.currentTimeMillis();
        System.out.println("ms: " + (end-start));

        System.out.println(a.compare(a.chunks, b.chunks, false));

        
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
    public void add(long[] a, long[] b) {
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

        // Only works if the abs() larger one is subtracting
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
        }
        else {

            // i>=1 because we do not want to include first index (sign value)
            for (int i=sum.length-1; i>=1; i--) {

                tempSum = a[i] - b[i];
                tempSum += carry;

                if (tempSum > 999999999) {
                    System.out.println("never trig");
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
        System.out.println(Arrays.toString(sum));
    }


}
