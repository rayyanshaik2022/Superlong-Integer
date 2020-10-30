import java.util.Arrays;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Binteger {
    
    public int[] val;
    public boolean isNegative;
    public String asString;
    public String asAbsString;

    public Binteger(String n) {
        asString = n;
        isNegative = n.charAt(0) == '-';
        if (isNegative) {
            asAbsString  = n.substring(1);
        }
        else {
            asAbsString = n;
        }
        
        char[] split = n.toCharArray();

        if (isNegative) {
            val = new int[split.length-1];
            for (int i=1; i<split.length; i++) {
                val[i-1] = Character.getNumericValue(split[i]);
            }
        }
        else {
            val = new int[n.length()];
            for (int i=0; i<split.length; i++) {
                val[i] = Character.getNumericValue(split[i]);
            }
        }
    }

    public int[] getArray() {
        return val;
    }
    public boolean getIsNegative() {
        return isNegative;
    }

    private int[] reduce(int[] a) {
        
        int start = 0;
    }

    public int compare(Binteger b, boolean ignoreNegative) {
        
        /* 
            returns 1 if parameter is less
            returns 0 if parameter is equal
            returns -1 if parameter is greater
        */

        // If false, this checks for literal value
        // and not absolute value
        if (!ignoreNegative) {
            if ((isNegative == b.getIsNegative()) && Arrays.equals(val, b.val)) {
                return 0;
            }
            else if (isNegative != b.getIsNegative()) {
                 return isNegative ? -1 : 1;
            }
        }

        if (Arrays.equals(val, b.val)) {
            return 0;
        }
        else if (val.length > b.val.length) {
            if (!ignoreNegative) {
                return -1;
            }
            return 1;
        }
        else if (val.length < b.val.length)  {
            if (!ignoreNegative) {
                return 1;
            }
            return -1;
        }

        // If the sizes are the same
        for (int i=val.length-1; i>=0; i--) {
            if (val[i] > b.val[i]) {
                // if one negative is larger than the other
                return 1;
            }
            else if (val[i] < b.val[i]) {
                return -1;
            }
        }

        // backup
        return 0;

    }

    public Binteger add(Binteger b) {
     

        if (compare(b, true) > 0) {
            int[] tval = val;
            boolean tisNegative = isNegative;
            String tasString = asString;
            String tasAbsString = asAbsString;

            val = b.val;
            isNegative = b.isNegative;
            asString = b.asString;
            asAbsString = b.asAbsString;

            b.val = tval;
            b.isNegative = tisNegative;
            b.asString  = tasString;
            b.asAbsString  = tasAbsString;
        }

        int[] bVal = b.getArray();
        int[] sum = new int[Math.max(val.length, bVal.length)+1];
        int carry = 0;
        int posCount = 0;
        int negCount = 0;
        int add = 0;

        boolean flipSign = false;
        boolean subCheck = false;

        if (!isNegative && b.getIsNegative()) {
            isNegative  = true;
            b.isNegative = false;
            flipSign = true;
        }
        else if (isNegative  && b.getIsNegative()) {

            isNegative = !isNegative;
            b.isNegative = !b.isNegative;
            flipSign  = true;
            subCheck = true;
            
        }

        boolean negative = false;

        for (int i=0; i < sum.length; i++) {
            if (i < val.length) {
                add += val[val.length - 1 -i] * (isNegative ? -1 : 1);
            }
            if (i < bVal.length) {
                add += bVal[bVal.length -1-i] * (b.getIsNegative() ? -1 : 1);
            }
            add += carry;
            if (add > 9) {
                carry = (add - (add % 10))/10;
                add = add % 10;
                posCount ++;
            }
            else if (add < 0 && add > -10) {
                add = add + 10;
                carry = 0; 
                
                if (!isNegative && i+1 < val.length) {
                    val[val.length-2-i] -= 1;
                }
                else if (isNegative && i+1 < bVal.length){
                    
                    bVal[bVal.length-2-i] -= 1;
                } 

                //sum[sum.length-2-i] -= 10;
                
                negCount ++;
            }
            else if (add < -9) {

                carry = (add - Math.abs(add % 10))/10;
                add = Math.abs(add % 10);
                negCount ++;
                
            }
            else {
                carry = 0;
                posCount  ++;
            }


            sum[sum.length-1-i] = add;
            add = 0;
        }


        // Determine if negative

        if (flipSign && subCheck) {
            negative = true;
        }
        else if (!flipSign) {
            negative = false;
        }
        else {
            negative = true;
        }
        
        if (flipSign) {
            isNegative  = !isNegative;
            b.isNegative  = !b.isNegative;
        }

        //System.out.println("Is Negative: " + negative);
        //System.out.println(Arrays.toString(sum));
        return new Binteger("-123123");
    }

}