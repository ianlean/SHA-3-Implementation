import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String input = " ";
        System.out.println(bytepad(input,input.length()));

    }
//X encoded String, w integer to prepend with
    static String bytepad(String X, int w) {
        String z = "";
// Validity Conditions: w > 0
        if(w > 0){
// 1. z = left_encode(w) || X.
            z = left_encode(w)+X;
//2. while len(z) mod 8 ≠ 0:
//          z = z || 0
            while (z.length() % 8 != 0){
                z += "0";
            }
//3. while (len(z)/8) mod w ≠ 0:
//          z = z || 00000000
            while ((z.length()/8) % 8 != 0){
                z += "00000000";
            }
        }
        System.out.println(z);
        return z;
    }


    static String encode_string(String input){
        if (input.length() < 22040){
            return left_encode(input.length()) + input;
        }
        return "";
    }

    static String right_encode(int X){
        // find binary representation of x
        String binary = Integer.toBinaryString(X);
        //find n such that 2^(8n) > x, which gives the number of bytes to split into
        int n = find_n(X);
        System.out.println(n);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).toString();
        System.out.println(binary);
        String[] byteArray = new String[n];
        Arrays.fill(byteArray, "");
        // split it into k bytes/base 256
        k_bytes(binary, byteArray);
        System.out.println(Arrays.toString(byteArray));

        String enc = "";
        StringBuilder fill = (base_two_five_six(Integer.toBinaryString(byteArray.length))).reverse();

        for (String s:byteArray){
            StringBuilder t = new StringBuilder(s).reverse();
            enc += String.valueOf(t);
        }
        enc += String.valueOf(fill);

        System.out.println("Right encode:  "+enc);

        return enc;
    }
// 0
    static String left_encode(int X){
        // find binary representation of x
        String binary = Integer.toBinaryString(X);
        //find n such that 2^(8n) > x, which gives the number of bytes to split into
        int n = find_n(X);
        System.out.println(n);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).toString();
        System.out.println(binary);
        String[] byteArray = new String[n];
        Arrays.fill(byteArray, "");
        // split it into k bytes/base 256
        k_bytes(binary, byteArray);

        System.out.println(Arrays.toString(byteArray));
        String enc = "";
        StringBuilder fill = (base_two_five_six(Integer.toBinaryString(byteArray.length))).reverse();
        enc += String.valueOf(fill);

        for (String s:byteArray){
            StringBuilder t = new StringBuilder(s).reverse();
            enc += String.valueOf(t);
        }

        System.out.println("Left encode:  "+enc);

        return enc;
    }
    //String[] enc = new String[byteArray.length+1];
    //Arrays.fill(enc, "");
    //StringBuilder fill = (base_two_five_six(Integer.toBinaryString(byteArray.length))).reverse();
    //enc[0] = String.valueOf(fill);
    private static String enc8(String byteT){

        StringBuilder reversedByte = new StringBuilder(byteT).reverse();
        return String.valueOf(reversedByte);
    }

    private static String[] k_bytes(String binary,String[] byteArray) {
        int index = 0;
        int counter = 0;
        for (int i = 0; i < binary.length(); i++) {
            byteArray[index] += binary.charAt(i);
            counter++;
            if (counter == 8) {
                index++;
                counter = 0;
            }
        }
        return byteArray;
    }
    private static int find_n(int x) {
        int base = 256;
        int n = 1;
        while(Math.pow(base,n) < x) {
            n++;
        }
        return n;
    }

    private static StringBuilder base_two_five_six(String s) {
        StringBuilder sb = new StringBuilder(s);

        while (sb.length()%8 != 0) {
            //System.out.println("hi");
            //System.out.println(sb);
            sb.insert(0,'0');
        }
        return sb;
    }
}
