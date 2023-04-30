public class Keccak_f {

    final String[] RC = {
            "0000000000000001", "0000000000008082", "800000000000808a",
            "8000000080008000", "000000000000808b", "0000000080000001",
            "8000000080008081", "8000000000008009", "000000000000008a",
            "0000000000000088", "0000000080008009", "000000008000000a",
            "000000008000808b", "800000000000008b", "8000000000008089",
            "8000000000008003", "8000000000008002", "8000000000000080",
            "000000000000800a", "800000008000000a", "8000000080008081",
            "8000000000008080", "0000000080000001", "8000000080008008"
    };

     int[][][] keccak(int[][][] A){
        int b = 1600;
        int n = 25;
        // [1600,24]
        for (int i = 0; i < n-1; i++){
            A = Round(A, fuckingThing(RC[i]));
        }
        return A;
    }

    int fuckingThing(String hex) {
        return Integer.parseInt(hex,16);
    }

    int[][][] Round(int[][][] A, int ir){


        return A;
    }



//    void chi(){
//        for(int y = 0; y < 5; y++){
//            for(int x = 0; x < 5; x++){
//                A[x,y]=a[x,y]("XOR HERE")((!(a[x+1,y])&a[x+2,y]));
//            }
//        }
//
//    }

    void theta(int[][][] A){
        int[][] C;
        int[][] D;
        for (int x = 0; x < 5; x++) {
            C[x] = A[x][0];
            for (int y = 1; y < 5; y++) {
                //C[x] = C[x]("XOR HERE")A[x][y];
                C[x] = XOR(C[X],A[x][y]);
            }
        }
        for (int x = 0; x < 5; x++) {
            D[x] =  XOR(C[x-1],(ROT(C[x+1],1)));
            for (int y = 0; y < 5; y++) {
                //A[x,y] = a[x,y]("XOR HERE")(D[x]);
            }
        }
    }
    int[] XOR(int[] a, int[] b){
        int i = 0;
        int[] c = new int[a.length];
        for (int z : a) {
            c[i] = z ^ b[i++];
        }
        return c;
    }

//    void pi(){
//        for (int x = 0; x < 5; x++) {
//            for (int y = 1; y < 5; y++) {
//                //As a matrix
//                //( X ) = ( 0 1 )(x)
//                //( Y ) = ( 2 3 )(y)
//                A[X,Y] = a[x,y];
//            }
//        }
//    }
//
////y ROT(a, d) a translation of a over d bits where bit in position z is mapped to position  z + d mod w
//
//
//    void phi(){
//        A[0,0] = a[0,0];
//        //(x)=(1)
//        //(y)=(0)
//        for (int t = 0; t < 23; t++) {
//            A[x,y] = ROT(a[x,y],(t+1)(t+2)/2);
//            //( x ) = ( 0 1 )(x)
//            //( y ) = ( 2 3 )(y)
//        }
//    }



}


