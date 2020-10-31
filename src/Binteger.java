import java.util.Arrays;


public class Binteger {
    
    public int[] val;
    public boolean isNegative;
    public String asString;

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

    public Binteger(int[] a, boolean negative) {
        val = reduce(a);
        isNegative = negative;
        asString = null;

    }

    private String toString(int[] a) {

        System.out.println(Arrays.toString(a));
        
        String finalString = "";
        for (int i = 0; i<a.length; i++) {
            finalString  += a[i];
        }
        return (isNegative ? "-" : "") + finalString;
    } 
    public String toString() {
        if (asString == null) {
            asString = toString(val);
        }
        return asString;
    }

    public int[] getArray() {
        return val;
    }
    public boolean getIsNegative() {
        return isNegative;
    }

    private int[] reduce(int[] a) {
        
        int start = 0;
        for (int i=0; i<a.length; i++) {
            if (a[i] != 0) {
                start = i;
                break;
            }
        }

        int[] b = new int[a.length-start];
        for (int i=0; i<b.length; i++) {
            b[b.length-1-i] = a[a.length-1-i];
        }

        return b;
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
            else if (isNegative  && b.asString == "0") {
                return -1;
            }
            else if (!isNegative && b.asString == "0") {
                return 1;
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
        for (int i=0; i<val.length; i++) {
            if (val[i] > b.val[i]) {
                if (isNegative && !ignoreNegative) {
                    return -1;
                }
                return 1;
            }
            else if (val[i] < b.val[i]) {

                if (isNegative && !ignoreNegative) {
                    return 1;
                }
                return -1;
            }
        }

        // backup
        return 0;

    }

    public Binteger add(Binteger b) {
     
        int c2 = compare(b, true);

        int[] BOval = Arrays.copyOf(val, val.length);

        if (c2 > 0 && (b.isNegative || isNegative)) {
            int[] tval = Arrays.copyOf(val, val.length);;
            boolean tisNegative = isNegative;
            String tasString = asString;

            val = Arrays.copyOf(b.val, b.val.length);;
            isNegative = b.isNegative;
            asString = b.asString;

            b.val = tval;
            b.isNegative = tisNegative;
            b.asString  = tasString;
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
            if (c2 == 1 && b.compare(new Binteger("0"), false) == -1) {
                negative = true;
            }
            else {
                negative = false;
            }
        }
        else {
            negative = true;
        }
        
        if (!flipSign) {
            isNegative  = !isNegative;
            b.isNegative  = !b.isNegative;
        }

        b.val = BOval;
        b.asString = null;
        asString = null;

        return new Binteger(sum, negative);
    }

    public Binteger subtract(Binteger b) {
        
        b.isNegative = !b.isNegative;
        Binteger x = add(b);
        b.isNegative  = !b.isNegative;

        
        return x;
    }

    //TODO change to private
    public int[] simpleMultiply(int b, int place) {
        int[] product = new int[val.length+1+(place-1)];

        int carry = 0;
        int mult;
        for (int i = 0; i<val.length; i++) {
            mult = (val[val.length-1-i] * b) + carry;

            if (mult > 9) {
                carry = (mult - (mult%10))/10;
                mult = mult%10;
            }
            else {
                carry = 0;
            }

            product[product.length-1-i-place+1] = mult;
        }
        if (carry % 10 == 0 && carry != 0) {
            product[product.length-1-val.length-place+1] = carry/10;
        }
        else if (carry != 0) {
            product[product.length-1-val.length-place+1] = carry % 10;
        }

        return product;
    }

    public Binteger multiply(Binteger b) {
        int[][] matrix = new int[b.val.length][val.length];

        /*
        For index of b, multiply against whole of self
        */

        for (int i=0; i<b.val.length; i++) {
            matrix[i] = simpleMultiply(b.val[i], b.val.length-i);
        }

        Binteger x = new Binteger("0");
        Binteger y;
        for (int i=0; i<matrix.length; i++) {
            y = new Binteger(matrix[i], false);
            x = x.add(y);
        }

        if (!(isNegative == b.isNegative)) {
            x.isNegative = !x.isNegative;
        }
        return x;

    }

    public double toDouble(int[] a) {
        double representation = 0;
        
        for (int i=0; i<a.length; i++) {
            representation += a[i] * Math.pow(10,-i);
        }

        return representation;
    }

    public double divide(Binteger b) {
        return 1.0;
    }
}