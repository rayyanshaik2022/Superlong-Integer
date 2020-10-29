import java.util.Arrays;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Binteger {
    
    int[] val;
    boolean isNegative;
    String asString;

    public Binteger(String n) {
        asString = n;
        isNegative = n.charAt(0) == '-';
        
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

    public void add(Binteger b) {
        int[] bVal = b.getArray();
        int[] sum = new int[Math.max(val.length, bVal.length)+1];
        int carry = 0;
        int futureCarry = 0;
        int posCount = 0;
        int negCount = 0;
        int add = 0;

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

                sum[sum.length-2-i] -= 10;
                
                negCount ++;
            }
            else if (add < -9) {
                System.out.println(add + " " + carry);

                carry = (add - Math.abs(add % 10))/10;
                add = Math.abs(add % 10);

                System.out.println(add + " " + carry);
                System.out.println();
                negCount ++;
                
            }
            else {
                carry = 0;
            }


            sum[sum.length-1-i] = add;
            add = 0;
            carry += futureCarry;
        }


        // Determine if negative
        
        if (isNegative && b.getIsNegative() && true) {
            negative = true;
        }
        if (negCount  > posCount) {
            negative  = true;
        }
        
        System.out.println(posCount + " " + negCount);


        System.out.println("Is Negative: " + negative);
        System.out.println("SUM: ");
        System.out.println(Arrays.toString(sum));
    }

}
