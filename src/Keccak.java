public class Keccak {
    /*Round functions*/
    void sha3_keccakf(long[] st) {


        final long[] RC = {
                0x0000000000000001L, 0x0000000000008082L, 0x800000000000808AL,
                0x8000000080008000L, 0x000000000000808BL, 0x0000000080000001L,
                0x8000000080008081L, 0x8000000000008009L, 0x000000000000008AL,
                0x0000000000000088L, 0x0000000080008009L, 0x000000008000000AL,
                0x000000008000808BL, 0x800000000000008BL, 0x8000000000008089L,
                0x8000000000008003L, 0x8000000000008002L, 0x8000000000000080L,
                0x000000000000800AL, 0x800000008000000AL, 0x8000000080008081L,
                0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L
        };


        final int[] keccakf_rotc = {
                1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 2, 14,
                27, 41, 56, 8, 25, 43, 62, 18, 39, 61, 20, 44
        };
        final int[] keccakf_piln = {
                10, 7, 11, 17, 18, 3, 5, 16, 8, 21, 24, 4,
                15, 23, 19, 13, 12, 2, 20, 14, 22, 9, 6, 1
        };
        final int rounds = 24;

        int i, j, r;
        long t;
        long[] bc = new long[5];


        //#if __BYTE_ORDER__ != __ORDER_LITTLE_ENDIAN__
        byte[] v;

        // endianess conversion. this is redundant on little-endian targets
        for (i = 0; i < 25; i++) {
            v = longToBytes(st[i]);
            st[i] = (v[0]) | ((v[1]) << 8) |
                    ((v[2]) << 16) | ((v[3]) << 24) |
                    ((v[4]) << 32) | ((v[5]) << 40) |
                    (( v[6]) << 48) | (( v[7]) << 56);
        }




        //ROTL64(x, y) (((x) << (y)) | ((x) >> (64 - (y))))
        // actual iteration
        for (r = 0; r < rounds; r++) {

            // Theta
            for (i = 0; i < 5; i++)
                bc[i] = st[i] ^ st[i + 5] ^ st[i + 10] ^ st[i + 15] ^ st[i + 20];

            for (i = 0; i < 5; i++) {
                t = bc[(i + 4) % 5] ^ ROTL64(bc[(i + 1) % 5], 1);
                for (j = 0; j < 25; j += 5)
                    st[j + i] ^= t;
            }

            // Rho Pi
            t = st[1];
            for (i = 0; i < 24; i++) {
                j = keccakf_piln[i];
                bc[0] = st[j];
                st[j] = ROTL64(t, keccakf_rotc[i]);
                t = bc[0];
            }

            //  Chi
            for (j = 0; j < 25; j += 5) {
                for (i = 0; i < 5; i++)
                    bc[i] = st[j + i];
                for (i = 0; i < 5; i++)
                    st[j + i] ^= (~bc[(i + 1) % 5]) & bc[(i + 2) % 5];
            }

            //  Iota
            st[0] ^= RC[r];
        }
        //#if __BYTE_ORDER__ != __ORDER_LITTLE_ENDIAN__
        // endianess conversion. this is redundant on little-endian targets
        for (i = 0; i < 25; i++) {
            v = longToBytes(st[i]);
            t = st[i];
            v[0] = (byte) (t & 0xFF);
            v[1] = (byte) ((t >> 8) & 0xFF);
            v[2] = (byte) ((t >> 16) & 0xFF);
            v[3] = (byte) ((t >> 24) & 0xFF);
            v[4] = (byte) ((t >> 32) & 0xFF);
            v[5] = (byte) ((t >> 40) & 0xFF);
            v[6] = (byte) ((t >> 48) & 0xFF);
            v[7] = (byte) ((t >> 56) & 0xFF);
        }

    }

    long ROTL64(long x, long y) {
        return (((x) << (y)) | ((x) >> (64 - (y))));
    }
    byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }
}
