import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Utils {


    String keccakInput(String N,String S,int w){
        String hex = textToHexString(S);
        String hex1 = textToHexString(N);
        String z = left_encode((hex.length()/2)*8)+hex;
        String y = left_encode((hex1.length()/2)*8)+hex1;
        //printHex(y+z);
        String output = bytepad(y+z,w);
        printHex(output);


        return output;
    }
    long[] hexToLong(String hex){
        long[] L = new long[25];
        Arrays.fill(L,0x000000000L);

        for (int i = 0; i*16 < hex.length(); i++) {
            L[i] = new BigInteger(hex.substring(i*16, i*16+16),16).longValue();
            System.out.println(L[i]);
        }

        return L;




        // System.out.println(new BigInteger(hex.substring(0, 16),16).longValue());
        // for (int i = 0; i < bytes.length; i++) {
        //     int index = i * 2;
        //     int j = Integer.parseInt(hex.substring(index, index + 2), 16);
        //     bytes[i] = (byte) j;
        // }
        // //byte[] bytes = { 1,2,3,4 };
        // //long l = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt() & 0x0FFFFFFFFL;
        // int z = 0;
        // int j = 0;
        // int end = 0;
        // int longIndex =0;
        // byte[] tempByte = new byte[8];
        // while (j < bytes.length && longIndex<25){
        //     if( z%8 == 0 && z != 0){
        //         z=0;
        //         end++;
        //         L[longIndex] = ByteBuffer.wrap(tempByte).order(ByteOrder.BIG_ENDIAN).getInt();
        //         longIndex++;
                
        //     }
        //     tempByte[z] =  bytes[j];
        //     z++;
        //     j++;
        // }

        // return L;
    }

    String longToHex(long[] input){
        String hex ="";
        for (int i = 0; i < input.length; i++) {
            //System.out.println(input[i]);
            byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(input[i]).array();
            //System.out.println();
            for (int j = 0; j < 8; j++) {
                String temp = (Long.toHexString(bytes[j] & 0xff));
                
                if(temp.length()<2){
                    temp = "0"+temp;
                }
                hex += temp;
            }
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
        int c = 0;
        for (int i = 0; i < hex.length()-1; i=i+2) {
            System.out.print(hex.charAt(i)+""+hex.charAt(i+1)+" ");
            c++;
            if(c%16==0){
                System.out.println("");
                c=0;
            }
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

    String XORhex(String hex1, String hex2){
        String xord = "";
        int loopSize = 0;
        
        hex1 = appendHex(hex1);
        hex2 = appendHex(hex2);


        // hex1 longer than hex2
        if(hex1.length() > hex2.length()){
            loopSize = hex2.length();
            xord = hex1.substring(loopSize);
        // hex2 is longest    
        }else{
            loopSize = hex1.length();
            xord = hex2.substring(loopSize);
        }

        String str = "";
        while(loopSize > 0){
            String[] temp = {"0x"+hex1.substring(0,2),"0x"+hex2.substring(0,2)};
            hex1 = hex1.substring(2);
            hex2 = hex2.substring(2);
            String hex = Integer.toHexString(Integer.decode(temp[0]) ^ Integer.decode(temp[1]));
            if(hex.length()<2){
                hex = "0"+hex;
            }
            loopSize -= 2;
            str += hex;  
        }

        xord = str + xord;
        
        return xord;
    }
    String appendHex(String hex){
        if(hex.length()%2 != 0){
            hex += "0";
        }
        return hex;
    }

}



/*
public class Utils {


    String keccakInput(String N,String S,int w){
        String hex = textToHexString(S);
        String hex1 = textToHexString(N);
        String z = left_encode((hex.length()/2)*8)+hex;
        String y = left_encode((hex1.length()/2)*8)+hex1;
        printHex(y+z);
        String output = bytepad(y+z,136);
        printHex(output);

 
        return output;
    }
    Long[] hexToLong(String hex){
        Long[] L = new Long[25]; 
        Byte[] bytes = new Byte[hex.length()/2];
        for (int i = 0; i*64 < hex.length(); i++) {
            
        }
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) j;
         }

        for (int i = 0; i < bytes.length; i++) {
            long temp = bytes[4];
        }

        return L;
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
    String appendHex(String hex){
        if(hex.length()%2 != 0){
            hex += "0";
        }
        return hex;
    }

    String XORhex(String hex1, String hex2){
        String xord = "";
        int loopSize = 0;
        
        hex1 = appendHex(hex1);
        hex2 = appendHex(hex2);


        // hex1 longer than hex2
        if(hex1.length() > hex2.length()){
            loopSize = hex2.length();
            xord = hex1.substring(loopSize);
        // hex2 is longest    
        }else{
            loopSize = hex1.length();
            xord = hex2.substring(loopSize);
        }

        String str = "";
        while(loopSize > 0){
            String[] temp = {"0x"+hex1.substring(0,2),"0x"+hex2.substring(0,2)};
            hex1 = hex1.substring(2);
            hex2 = hex2.substring(2);
            System.out.println(temp[0] +"   "+ temp[1]);
            System.out.println(Integer.decode(temp[0]));
            System.out.println(Integer.decode(temp[0]) ^ Integer.decode(temp[1]));
            String hex = Integer.toHexString(Integer.decode(temp[0]) ^ Integer.decode(temp[1]));
            if(hex.length()<2){
                hex = "0"+hex;
            }
            loopSize -= 2;
            str += hex;  
        }

        xord = str + xord;
        
        return xord;
    }



}
*/
