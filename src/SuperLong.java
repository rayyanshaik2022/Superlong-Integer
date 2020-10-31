import java.util.Arrays;

public class SuperLong {

    String value;
    long[] splitValue;
    boolean isNegative = false;
    
    public SuperLong(String n){
        value = n;
        isNegative = n.charAt(0) == '-';
        splitValue = toLongArray(n);
        
    }
    public SuperLong(long[] n) {
        splitValue = n;
        boolean negativeFlag = false;

        for (int i=n.length-1; i >= 0; i--) {
            if (n[i] != 0 && !negativeFlag) {
                isNegative = n[i] < 0;
                negativeFlag = true;
                continue;
            }
            else if (negativeFlag) {
                n[i] = n[i] * -1;
            }
        }

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

    public long[] toLongArray(String a) {

        char[] chars = a.toCharArray();
        long[] array = new long[(int)Math.ceil(a.length()/9.0)];
        
        int c1 = array.length-1;
        int c2 = 0;
        String current = "";
        for (int i=chars.length-1; i>= 0; i--) {
            if (c2 < 9) {
                current = chars[i] + current;
                c2 ++;
            }
            if (c2>=9 || i == 0) {
                System.out.println("c2:"+c2+" i:"+i);
                array[c1] = Long.parseLong(current);
                if (isNegative && array[c1] > 0) {
                    array[c1] *= -1;
                }
                c2 = 0;
                c1 --;
                current = "";
            }
        }


        System.out.println("Converted array");
        System.out.println(Arrays.toString(array));
        return array;
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
            
            
            // set new carry
            if (add + carry > 999999999) {
                carry = add+carry-999999999;
            }
            else if (add + carry < -999999999) {
                carry = add+carry + 999999999;
            }
            add = 0;
            //System.out.println("carry " + carry);
        }

        System.out.println("SUM: ");
        System.out.println(Arrays.toString(sum));
        return new SuperLong(sum);
    }

}



/*abstract
else {

            // -4025372428 371997405
            //  4344936009 049315546
            
            System.out.println("triggered");
            for (int i=sum.length-1; i>=1; i--) {

                tempSum = (a[i]*a[0]) + (b[i]*b[0]);

                if (Math.abs(tempSum) > 999999999) {

                    carry = (tempSum - (tempSum % 1000000000))/1000000000;
                    tempSum = tempSum % 1000000;
                }
                else {
                    carry = 0;
                }

                if ((tempSum) < 0){
                    tempSum += + 1000000000;
                }
                
                sum[i] = tempSum;
                tempSum = 0;

            }
        }
        System.out.println(Arrays.toString(sum));
    }
*/