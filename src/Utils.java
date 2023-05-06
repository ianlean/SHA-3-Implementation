import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Utils {


    String keccakInput(String N,String S,int w){
        String hex = textToHexString(S);
        String hex1 = textToHexString(N);
        String z = left_encode((hex.length()/2)*8)+hex;
        String y = left_encode((hex1.length()/2)*8)+hex1;
        printHex(y+z);
        String output = bytepad(y+z,w);
        printHex(output);


        return output;
    }
    long[] hexToLong(String hex){
        long[] L = new long[25];
        Arrays.fill(L,0L);
        Byte[] bytes = new Byte[hex.length()/2];

        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) j;
        }
        //byte[] bytes = { 1,2,3,4 };
        //long l = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt() & 0x0FFFFFFFFL;
        int z = 0;
        int j = 0;
        int end = 0;
        int longIndex =0;
        byte[] tempByte = new byte[8];
        while (j < bytes.length && longIndex<25){
            if( z%8 == 0 && z != 0){
                z=0;
                end++;
                L[longIndex] = ByteBuffer.wrap(tempByte).order(ByteOrder.BIG_ENDIAN).getInt() & 0x0FFFFFFFFL;
                longIndex++;
            }
            tempByte[z] =  bytes[j];
            z++;
            j++;
        }

        return L;
    }

    String longToHex(long[] input){
        String hex ="";
        for (int i = 0; i < input.length; i++) {
            byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(input[i]).array();
            for (int j = 0; j < 8; j++) {
                hex += Long.toHexString(bytes[j] & 0xff);
            }
            hex +=("    new Long   ");
        }

        return hex;
    }

    String textToHexString(String text){
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
    void printHex(String hex) {
        System.out.println("");
        hex = hex.toUpperCase();
        for (int i = 0; i < hex.length(); i=i+2) {
            System.out.print(hex.charAt(i)+""+hex.charAt(i+1)+" ");
        }
        System.out.println("");

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


    String encode_string(String input){
        if (input.length() < 22040){
            return left_encode(input.length()) + input;
        }
        return "";
    }

    String right_encode(int X){
        String hex = Integer.toHexString(X);
        if(hex.length()<2){
            hex = "0"+hex;
        }
        return hex+hexOne;
    }
    // 0
    final String hexOne = "01";
    String left_encode(int X){
        String hex = Integer.toHexString(X);
        if(hex.length()<2){
            hex = "0"+hex;
        }
        return hexOne + hex;
    }




}
