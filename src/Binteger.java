import java.util.Arrays;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Binteger {
    
    int[] val;
    boolean isNegative;
    String asString;
    String asAbsString;

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

    public int compare(Binteger b) {
        
        /* 
            returns 1 if parameter is less
            returns 0 if parameter is equal
            returns -1 if parameter is greater
        */

        if (val == b.val) {
            return 0;
        }
        
        if (val.length > b.val.length) {
            return 1;
        }
        else if (1) {
            
        }

    }

    public Binteger add(Binteger b) {

        int comparison = asString.compareTo(b.asString);
        int comparison2 = asAbsString.compareTo(b.asAbsString);

        if (val.hashCode() > b.val.hashCode()) {
            comparison2 = -1;
        }
        else if (val.hashCode() < b.val.hashCode()) {
            comparison2 = 1;
        }

        System.out.println("C1: " +comparison + "  C2: " + comparison2);
        System.out.println("x: " +isNegative + "  z: " + b.isNegative);

        if ((comparison < 0 && comparison2 > 0) || (comparison > 0 && comparison2 > 0)) {
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
            System.out.println("triggered");
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

        System.out.println(negative);
        System.out.println(Arrays.toString(sum));
        return new Binteger("-123123");
    }

}
