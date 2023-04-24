public class Main {
    public static void main(String[] args) {
        String test = "T";

        encode_string(test);
    }
    static void bytepad(){

    }

    static void encode_string(String input){

    }

    static void left_encode(int X){

    }
//Let n be the smallest positive integer for which 2^8n > x
//Let x1, x2,…, xn be the base-256 encoding of x satisfying:
//x = ∑ 2^(8_(n-i))
//x_i, for i = 1 to n.
    //Let O_i = enc8(xi), for i = 1 to n.
    //Let O_n+1 = enc8(n).
    //Return O = O1 || O2 || … || On || On+1.

    static void right_encode(){
 hupla
    }
    static byte[] bytepad(byte[] X, int w) {
// Validity Conditions: w > 0
        assert w > 0;
// 1. z = left_encode(w) || X.
        byte[] wenc = left_encode(w);
        byte[] z = new byte[w*((wenc.length + X.length + w - 1)/w)];
// NB: z.length is the smallest multiple of w that fits wenc.length + X.length
        System.arraycopy(wenc, 0, z, 0, wenc.length);
        System.arraycopy(X, 0, z, wenc.length, X.length);
// 2. (nothing to do: len(z) mod 8 = 0 in this byte-oriented implementation)
// 3. while (len(z)/8) mod w ≠ 0: z = z || 00000000
        for (int i = wenc.length + X.length; i < z.length; i++) {
            z[i] = (byte)0;
        }
// 4. return z
        return z;
    }

    void ASCII_CODE(String input) {
        StringBuilder encoded = new StringBuilder();
        char[] letters = input.toCharArray();
        for (char ch : letters) {
            encoded.append(Integer.toBinaryString(ch) + " ");
        }
        System.out.println(encoded);
    }
}
