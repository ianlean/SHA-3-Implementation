import java.util.Arrays;

public class thing {

    public static void main(String[] args) {
        String s = "Email Signature";
        String poop = "";
        String S = textToHexString(s);
        //System.out.println(textToHexString(S));
        System.out.println(binaryToHex(right_encode(256)));
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
        String thing = "";
        for (int i = 0; i <= binary.length()-4; i += 4) {
           int decimal = Integer.parseInt(binary.substring(i, i+4), 2);
           thing += Integer.toHexString(decimal) + " ";
       }
        return thing;
    }

    String bytepad(String X, int w) {
        String z = "";
        String y = "";
        if(w > 0){
            z = left_encode(w)+X;

            while ((z.length()*4+y.length()) % 8 != 0){
                y += "0";
            }
            for (int i = 0; i < y.length(); i=i+4) {
                z+="0";
            }

            while ((z.length()*4/8) % w != 0){
                z += "00";
            }
        }
        return z;
    }

    static String left_encode(int X){
        // find binary representation of x
        String binary = Integer.toBinaryString(X);
        //find n such that 2^(8n) > x, which gives the number of bytes to split into
        int n = find_n(X);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).toString();
        StringBuilder s = new StringBuilder(binary);

        String temp = Integer.toBinaryString(n);
        temp = base_two_five_six(temp).toString();
        StringBuilder finalN = new StringBuilder(temp);
        System.out.println(finalN);
        return String.valueOf(finalN.toString() + s);
    }

    static String right_encode(int X){
        // find binary representation of x
        String binary = Integer.toBinaryString(X);
        //find n such that 2^(8n) > x, which gives the number of bytes to split into
        int n = find_n(X);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).toString();
        StringBuilder s = new StringBuilder(binary);

        String temp = Integer.toBinaryString(n);
        temp = base_two_five_six(temp).toString();
        StringBuilder finalN = new StringBuilder(temp);
        System.out.println(finalN);
        return String.valueOf(s + finalN.toString());
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
        while(!(Math.pow(base,n) > x)) {
            n++;
        }
        return n;
    }
}
