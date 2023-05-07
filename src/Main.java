

class Main{
    final static Utils utils = new Utils();
    final static Keccak keccak = new Keccak();
    public static void main(String[] args){
        String input = "";
        String bitString = utils.keccakInput("","Email Signature",136);

        String h1 = "194CCD058B2A83D60229DC6984D14F158B694FA4BD";
        String h2 = "00 00 00 00 00 01 88 01 00 00 00 00 00 6C 69 61 00 00 00 00 00 75 74 61 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        String h3="018801000178456D61696C205369676E617475726500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        
        //System.out.println("XOR "+utils.XORhex(h1, h2));
        utils.printHex((bitString));
        long[] l = utils.hexToLong(bitString);
        utils.printHex((utils.longToHex(l)));

        System.out.println(utils.longToHex(l).substring(0, bitString.length()).equals(bitString));
        utils.printHex(utils.longToHex(l));
        l = keccak.sha3_keccakf(l);
        utils.printHex(utils.longToHex(l));

    
  
        
    //     System.out.println(utils.longToHex(l));
    //     System.out.println(Arrays.toString(l));

    
     }


    //  public static byte[] longToBytes(long l) {
    //     byte[] result = new byte[8];
    //     for (int i = 7; i >= 0; i--) {
    //         result[i] = (byte)(l & 0xFF);
    //         l >>= 8;
    //     }
    //     return result;
    // }
    
    // public static long bytesToLong(final byte[] b) {
    //     long result = 0;
    //     for (int i = 0; i < 8; i++) {
    //         result <<= 8;
    //         result |= (b[i] & 0xFF);
    //     }
    //     return result;
    // }
    // static String longToHex(long[] input){
    //     String hex ="";
    //     for (int i = 0; i < input.length; i++) {
    //         //System.out.println(input[i]);
    //         byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(input[i]).array();
    //         //System.out.println(bytesToLong(bytes));
    //         for (int j = 0; j < 8; j++) {
    //             String temp = (Long.toHexString(bytes[j] & 0xff));
                
    //             if(temp.length()<2){
    //                 temp = "0"+temp;
    //             }
    //             hex += temp;            
    //             //System.out.println("   temp "+temp);

    //         }
    //     }

    //     return hex;
    // }
}
/*// Initialize the context for SHA3

int sha3_init(sha3_ctx_t *c, int mdlen)
{
    int i;

    for (i = 0; i < 25; i++)
        c->st.q[i] = 0;
    c->mdlen = mdlen;
    c->rsiz = 200 - 2 * mdlen;
    c->pt = 0;

    return 1;
}

// update state with more data

int sha3_update(sha3_ctx_t *c, const void *data, size_t len)
{
    size_t i;
    int j;

    j = c->pt;
    for (i = 0; i < len; i++) {
        c->st.b[j++] ^= ((const uint8_t *) data)[i];
        if (j >= c->rsiz) {
            sha3_keccakf(c->st.q);
            j = 0;
        }
    }
    c->pt = j;

    return 1;
}

// finalize and output a hash

int sha3_final(void *md, sha3_ctx_t *c)
{
    int i;

    c->st.b[c->pt] ^= 0x06;
    c->st.b[c->rsiz - 1] ^= 0x80;
    sha3_keccakf(c->st.q);

    for (i = 0; i < c->mdlen; i++) {
        ((uint8_t *) md)[i] = c->st.b[i];
    }

    return 1;
}

// compute a SHA-3 hash (md) of given byte length from "in"

void *sha3(const void *in, size_t inlen, void *md, int mdlen)
{
    sha3_ctx_t sha3;

    sha3_init(&sha3, mdlen);
    sha3_update(&sha3, in, inlen);
    sha3_final(md, &sha3);

    return md;
}

// SHAKE128 and SHAKE256 extensible-output functionality

void shake_xof(sha3_ctx_t *c)
{
    c->st.b[c->pt] ^= 0x1F;
    c->st.b[c->rsiz - 1] ^= 0x80;
    sha3_keccakf(c->st.q);
    c->pt = 0;
}

void shake_out(sha3_ctx_t *c, void *out, size_t len)
{
    size_t i;
    int j;

    j = c->pt;
    for (i = 0; i < len; i++) {
        if (j >= c->rsiz) {
            sha3_keccakf(c->st.q);
            j = 0;
        }
        ((uint8_t *) out)[i] = c->st.b[j++];
    }
    c->pt = j;
} */