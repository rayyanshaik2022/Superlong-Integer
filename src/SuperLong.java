import java.util.Arrays;

public class SuperLong {

    String value;
    long[] splitValue;
    
    public SuperLong(String n){
        value = n;
        splitValue = toLongArray(n);
    }
    public SuperLong(long[] n) {
        splitValue = n;
        value = toString();
    } 

    public String toString() {
        String output = "";
        boolean isNegative = false;

        if (splitValue[splitValue.length-1] < 0) {
            isNegative = true;
        }

        for (long val : splitValue) {
            if (val != 0) {
                if (isNegative) {
                    output += Math.abs(val);
                }
                else {
                    output += val;
                }
            } 
        }

        if (isNegative) {
            return "-" + output;
        }
        return output;
    }

    public long[] getSplitValue() {
        return splitValue;
    }
     
    private long[] toLongArray(String a) {
        long[] arrayA;
        if (a.length() < 10) {
            arrayA = new long[1];
            arrayA[0] = Long.parseLong(a);
        }
        else {
            arrayA = new long[(int)Math.ceil(a.length()/9.0)];
            boolean isNegative = a.charAt(0) == '-';
            if (isNegative) {
                a = a.substring(1,a.length());
            }

            while (a.length() % 9 != 0) {
                a = "0" + a;
            }

            for (int i = 0; i < a.length(); i++) {
                if (i%9 == 0) {
                    if (isNegative) {
                        arrayA[i/8] = Long.parseLong("-"+a.substring(i, i+9));
                    }
                    else {
                        arrayA[i/8] = Long.parseLong(a.substring(i, i+9));
                    }
                    
                }
                
            }
        }

        return arrayA;
    }
    
    public SuperLong add(SuperLong b) {

        long[] aSplit = splitValue;
        long[] bSplit = b.getSplitValue();
        int size = Math.max(aSplit.length, bSplit.length) + 1;
        long sum[] = new long[size];
        long carry = 0;
        int add = 0;

        for (int i=0; i < size-1; i++) {
            if (i < aSplit.length) {
                add += aSplit[aSplit.length-i-1];
            }
            if (i < bSplit.length) {
                add += bSplit[bSplit.length-i-1];
            }
            sum[size-i-1] = add + carry;
            add = 0;
        }

        return new SuperLong(sum);
    }

    public String old(String a, String b) {

        while (b.length() < a.length()) {
            b = "0" + b;
        }
        while (a.length() < b.length()) {
            a = "0" + a;
        }
        
        long[] arrayA = toLongArray(a);
        long[] arrayB = toLongArray(b);

        int size = arrayA.length;

        long[] sum = new long[size+1];

        long carry = 0; 
        
        for (int i = size-1; i >= 0; i--) {
            long newSum = arrayA[i] + arrayB[i] + carry;
            sum[i] = newSum;
            String added = String.valueOf(newSum);
            if ((newSum >= 0 && added.length() > 9) || (newSum < 0 && added.length() > 10)) {
                carry = Long.parseLong(added.substring(0, added.length()-8));
            }
        }

        String output = "";
        for (int i=0; i<sum.length; i++) {
            if (i != 0) {
                output += Math.abs(sum[i]);
            }
            else {
                output += sum[i];
            }
            
        }
        return output.substring(0, output.length()-1);
    }

}