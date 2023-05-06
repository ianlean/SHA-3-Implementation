import java.util.Arrays;

public class thing {

    public static void main(String[] args) {
       // System.out.println(binaryToHex(left_encode(0)));
        System.out.println(left_encode(30));
//        String hex = binaryToHex(binary);
//        String n = "";
        String s = "Email Signature";
        String S = textToHexString(s);
        //System.out.println(textToHexString(S));
        System.out.println(binaryToHex(left_encode(s.length()*2)) + binaryToHex(textToBinaryString(s)));
        System.out.println(encode_string(s));
        System.out.println("0 1 7 8 4 5 6 D 6 1 6 9 6 C 2 0 5 3 6 9 6 7 6 E 6 1 7 4 7 5 7 2" +
                "6 5");

    }

    static String textToHexString(String text){
        StringBuffer hex = new StringBuffer();
        char ch[] = text.toCharArray();
        for(int i = 0; i < ch.length; i++) {
            String temp = Integer.toHexString(ch[i]);
            if(temp.length()<2){
                temp = "0"+temp;
            }
            hex.append(temp);
        }
        return hex.toString();
    }

    static String textToBinaryString(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }

    static String encode_string(String input){
        if (input.length() < 22040){
            return binaryToHex(left_encode(input.length()*2)) + binaryToHex(textToBinaryString(input));
        }
        return "";
    }

    static String binaryToHex(String binary) {
//       long decimal = Integer.parseInt(binary,2);
//       return Long.toHexString(decimal);
        String thing = "";
        for (int i = 0; i <= binary.length()-4; i += 4) {
           int decimal = Integer.parseInt(binary.substring(i, i+4), 2);
           thing += Integer.toHexString(decimal) + " ";
       }
        return thing;
    }

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
            while ((z.length()/8) % w != 0){
                z += "00000000";
            }
        }
        System.out.println(z.length());
        return z;
    }

    static String left_encode(int X){
        // find binary representation of x
        String binary = Integer.toBinaryString(X);
        //find n such that 2^(8n) > x, which gives the number of bytes to split into
        int n = find_n(X);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).reverse().toString();

        return base_two_five_six(Integer.toBinaryString(n)) + binary;
    }

    private static StringBuilder base_two_five_six(String s) {
        StringBuilder sb = new StringBuilder(s);

        while (sb.length()%8 != 0) {
            sb.insert(0,'0');
        }
        return sb;
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
}
