import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("b9a5dc0048db9a7d13548781df3cd4b2334606391f75f40c14225a92f4cb3537".length());
      
        String bitString = "1111100010001101110011011111101000000011001010100001111010010111011001010100100011111101001110010000101000110110101011001101010110001111100010000000100010001000000001101010011010011011101110111100011111111000100000100101100111000101000000001111010101110001101000100000010000010010010101011101101011010010001111101100011100100110110000110101110001101001000000111111111110100010111111001011100101001001101001100000011101110111010110010001010110000011010011011111010100100000110000100101100110010011101111011010111100101101011110011101110011100001010110111000011111101110100000000100011100110100111010010100000100100001110100010001010110010100000111110100001000000001100101000011111011101011110100100111100101011010000100111010110001010100000011101010101010111001100101001100011100101101100001111111101011101100101001110011101101011000100010010001110010110101011101110110011100110011000100101000111010110111100101001001101101010101110001110100110000100110110111000010100110000010000111001100011010011100001010010110010010000110100110011100101010011010111111001011111011101100001101110110111000011010010011101101010111010101100111100111100111011010110111011101001110010011101110110000011111001000111010010011111100000111111110101101000110100011111011000111000100101110101011101001111011000011110101010100101011010000000100000110011110001010110001101000101010110111010100110110011110100100111110010101111000110100101110100011100101000111111100010011000111110010001110110111010000110011011001111001100100010100011100111010001110101011111100111111011110010110111111100111001110110101100011011001010100101001";
        Keccak_f keccak = new Keccak_f();
        keccak.f(bitString);
//        String input = " ";
//        System.out.println(bytepad(input,input.length()));



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
      //  System.out.println(n);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).toString();
      //  System.out.println(binary);
        String[] byteArray = new String[n];
        Arrays.fill(byteArray, "");
        // split it into k bytes/base 256
        k_bytes(binary, byteArray);
      //  System.out.println(Arrays.toString(byteArray));

        String enc = "";
        StringBuilder fill = (base_two_five_six(Integer.toBinaryString(byteArray.length))).reverse();

        for (String s:byteArray){
            StringBuilder t = new StringBuilder(s).reverse();
            enc += String.valueOf(t);
        }
        enc += String.valueOf(fill);

       // System.out.println("Right encode:  "+enc);

        return enc;
    }
// 0
    static String left_encode(int X){
        // find binary representation of x
        String binary = Integer.toBinaryString(X);
        //find n such that 2^(8n) > x, which gives the number of bytes to split into
        int n = find_n(X);
     //   System.out.println(n);
        // append extra zeros to the number if needed
        binary = base_two_five_six(binary).toString();
      //  System.out.println(binary);
        String[] byteArray = new String[n];
        Arrays.fill(byteArray, "");
        // split it into k bytes/base 256
        k_bytes(binary, byteArray);

      //  System.out.println(Arrays.toString(byteArray));
        String enc = "";
        StringBuilder fill = (base_two_five_six(Integer.toBinaryString(byteArray.length))).reverse();
        enc += String.valueOf(fill);

        for (String s:byteArray){
            StringBuilder t = new StringBuilder(s).reverse();
            enc += String.valueOf(t);
        }

   //     System.out.println("Left encode:  "+enc);

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
