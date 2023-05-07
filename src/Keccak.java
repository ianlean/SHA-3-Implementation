public class Keccak {
    /*Round functions*/
    long[] sha3_keccakf(long[] st) {


        final long[] RC = {
            0x0000000000000001L, 0x0000000000008082L, 0x800000000000808aL,
            0x8000000080008000L, 0x000000000000808bL, 0x0000000080000001L,
            0x8000000080008081L, 0x8000000000008009L, 0x000000000000008aL,
            0x0000000000000088L, 0x0000000080008009L, 0x000000008000000aL,
            0x000000008000808bL, 0x800000000000008bL, 0x8000000000008089L,
            0x8000000000008003L, 0x8000000000008002L, 0x8000000000000080L,
            0x000000000000800aL, 0x800000008000000aL, 0x8000000080008081L,
            0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L
        };


        final int[] keccakf_rotc = {
            1,  3,  6,  10, 15, 21, 28, 36, 45, 55, 2,  14,
            27, 41, 56, 8,  25, 43, 62, 18, 39, 61, 20, 44
        };
        final int[] keccakf_piln = {
            10, 7,  11, 17, 18, 3, 5,  16, 8,  21, 24, 4,
            15, 23, 19, 13, 12, 2, 20, 14, 22, 9,  6,  1
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
            st[i] = ((long)v[0]) | (((long)v[1]) << 8) |
                    (((long)v[2]) << 16) | (((long)v[3]) << 24) |
                    (((long)v[4]) << 32) | (((long)v[5]) << 40) |
                    (((long)v[6]) << 48) | (((long)v[7]) << 56);
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
        //endianess conversion. this is redundant on little-endian targets
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

            st[i] = bytesToLong(v);
        }

        return st;
    }

    long ROTL64(long x, int y) {
        return (((x) << (y)) | ((x) >> (64 - (y))));
    }
    byte[] longToBytes(long l) {
        byte[] result = new byte[Long.BYTES];
        for (int i = 0; i < result.length; i++) {
            result[result.length - i - 1] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }
    public static long bytesToLong(final byte[] B) {
        long result = 0;
        for (byte b : B) {
            result = (result << 8) + (b & 0xFF);
        }
        return result;
    }
}
//A1 71 71 AE 5E F9 77 BE EF B5 DB DC 10 3F A4 EE B3 5A D9 A6 D7 5D C4 7D 16 F5 9C D0 CD 42 CE 8A 17 17 5E 6F FF CF AF FF FF FF FF 82 F7 47 FE 73 D6 E1 E5 FA 86 3F FE 73 D6 AA 16 2B 66 DE 85 71 B8 D8 55 49 68 E8 57 FA 48 27 98 A9 EF FF FF FF FF FF FF FE BF FF FF FF FF F2 D6 D4 8F FF FF FF FF F2 D6 D5 CF AA 78 F2 92 D5 3D BF AA 78 F2 92 D5 38 21 B7 6D A1 D7 AA AB 3C 77 44 3D 61 50 83 3B 7D 74 43 D6 3B 35 8D 82 8F FA B3 DE A2 7E D8 AF 31 B2 B3 8A A1 E8 9E 3C 31 94 3B 34 68 7A F7 37 8F 86 AD A6 FC AE 42 79 BE 7F CF B3 2B CB E7 1A E7 6F 6A 2F AE 50 90 40 34 69 53 FE 47 
//63 9F 59 A0 48 A2 6E 03 98 5E 54 AD EF AF CD DA F2 67 2B 24 92 A2 67 7A F2 67 3E 1F F4 C3 FE 7F F5 BB 9B 78 AF 8D D5 5B CB 33 EF 73 B1 E3 D5 5B CB 33 EF 63 3A 79 CC CD 3A D7 5F B2 FD FE EC CD 2A D4 7A 2E 51 CC F7 AE 77 8A CD 49 1D DF FF FF FF FF FF FF FF BF FF FF FF FF F6 3E 46 AF FF FF FF FF F6 3E 47 AF FF FF FF FF FF FE 5C 2F FF FF FF FF FF FE 59 6B AB 8A AF A2 EC 77 EF 83 DD 1E C8 6E 06 18 77 FF FF FF FF FF FF FF E2 2F FE DF 6F CE FA EE 7E 2F A8 5B 0B 4A F2 EC 43 B9 3C 1A 2D 51 7D 2E 35 B8 98 28 78 CE 5E EA 5C 7F FF FF FA FB AE 0A 25 DF FF FF F8 5D DF 07 A6 BF 7F DF AF 75 FD 3E 35