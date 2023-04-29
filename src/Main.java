import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //left_encode(0);
        //right_encode(0);
        bytepad(encode_string(""),1);
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

    void keccak(){
        // [1600,24]

    }
    /*
    void keccak(){
        // [1600,24]
        Keccak-f[1600]
    }
    
    void chi(){
        for(int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                A[x,y]=a[x,y]("XOR HERE")((!(a[x+1,y])&a[x+2,y]));
            }
        }

    }

    void theta(){
        for (int x = 0; x < 5; x++) {
            C[x]= a[x,0];
            for (int y = 1; y < 5; y++) {
                C[x] = C[x]("XOR HERE")a[x,y];
            }
        }
        for (int x = 0; x < 5; x++) {
            D[x] = C[x-1]("XOR HERE")(ROT(C[x+1],1);
            for (int y = 0; y < 5; y++) {
                A[x,y] = a[x,y]("XOR HERE")(D[x]);
            }
        }
    }
    void pi(){
        for (int x = 0; x < 5; x++) {
            for (int y = 1; y < 5; y++) {
                //As a matrix
                //( X ) = ( 0 1 )(x)
                //( Y ) = ( 2 3 )(y)
                A[X,Y] = a[x,y];
            }
        }
    }

//y ROT(a, d) a translation of a over d bits where bit in position z is mapped to position  z + d mod w


    void phi(){
        A[0,0] = a[0,0];
        //(x)=(1)
        //(y)=(0)
        for (int t = 0; t < 23; t++) {
            A[x,y] = ROT(a[x,y],(t+1)(t+2)/2);
            //( x ) = ( 0 1 )(x)
            //( y ) = ( 2 3 )(y)
        }
    }
    */
}
