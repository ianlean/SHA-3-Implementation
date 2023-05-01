import java.util.Arrays;
//rc The function that generates the variable bits of the round constants.
//RC For a round of a KECCAK-p permutation, the round constant.
public class Keccak_f {
    void print(int[][][] A){
        for (int y = 0; y < 5; y++) {
            System.out.println("");
            for (int x = 0; x < 5; x++) {
                System.out.println("");
                for (int z = 0; z < 64; z++) {
                    System.out.print(A[x][y][z]+",");
                }
            }
        }
    }
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

    final int keccakf_rotc[] = new int[] {
        0, 1,  3,  6,  10, 15, 21, 28, 36, 45, 55, 2,  14, 27, 41, 56, 8,  25, 43, 62, 18, 39, 61, 20, 44
    };

    public Keccak_f(String bitString){
        int[][][] A = bitStringToMatrix(bitString);
        //int[][][] B = roundFunction(24,A);
        //System.out.println(A.equals(B));
        print(A);
        System.out.println("\n---------------\n\n\n\n");
        print(theta(A));
        System.out.println("\n---------------\n\n\n\n");
        print(rho(A));
        System.out.println("\n---------------\n\n\n\n");
        print(pi(A));
        System.out.println("\n---------------\n\n\n\n");
        print(chi(A));
        System.out.println("\n---------------\n\n\n\n");
        print(iota(A,2));
    }

    int[][][] roundFunction(int rounds, int[][][] A){
        for (int i = 0; i < rounds; i++) {
            print(iota(chi(pi(rho(theta(A)))),i));
        }
        return A;
    }

    int[][][] bitStringToMatrix(String bitString){
        //System.out.println(bitString.length());
        int[][][] A = new int[5][5][64];
        int count = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 64; z++) {
                    A[x][y][z] = Integer.parseInt(String.valueOf(bitString.charAt(count)));
                    count++;
                }
            }
        }
//        for (int y = 0; y < 5; y++) {
//            System.out.println("");
//            for (int x = 0; x < 5; x++) {
//                System.out.println("");
//                for (int z = 0; z < 64; z++) {
//                    System.out.print(A[x][y][z]+",");
//                }
//            }
//                //System.out.println("x:"+x+ "y:"+y+  Arrays.toString(A[x][y]));
//
//        }

        return A;
    }

    int[][][] keccak(int[][][] A){
        int b = 1600;
        int n = 25;
        // [1600,24]
        for (int i = 0; i < n-1; i++){
            A = Round(A, toHex(RC[i]));
        }
        return A;
    }

    int toHex(String hex) {
        return Integer.parseInt(hex,16);
    }

    int[][][] Round(int[][][] A, int ir){


        return A;
    }





    int[][][] theta(int[][][] A){


        int[][] C = new int[5][64], D= new int[5][64];
        for (int x = 0; x < 5; x++) {
            C[x] = A[x][0];
      //      System.out.println(Arrays.toString(C[x]));
            for (int y = 1; y < 5; y++) {
//                C[x] = C[x]("XOR HERE")A[x][y];
                C[x] = XOR(C[x],A[x][y]);
            }
        }
        for (int x = 0; x < 5; x++) {
            D[x] =  XOR(C[(x-1+5)%5],(ROT(C[(x+1)%5],1)));
            for (int y = 0; y < 5; y++) {
                A[x][y] = XOR(A[x][y],D[x]);
            }
        }

        return A;
    }

    int[][][] rho(int[][][] a){
        int[][][] A = new int[5][5][64];
        for (int z = 0; z < 64; z++) {
            A[0][0][z] = a[0][0][z];
        }

        int[] cord = new int[]{1,0};
        //                    (x,y)

        for (int t = 0; t < 23; t++) {
            for (int z = 0; z < 64; z++) {
                A[cord[0]][cord[1]][z] = a[cord[0]][cord[1]][((((t+1)*(t+2)/2)%64))];
            }

            int x = cord[0];
            int y = cord[1];
            cord[0] = y;
            cord[1] = (2*x+3*y)%5;
        }

        return A;
    }

    int[][][] pi(int[][][] a){
        int[][][] A = new int[5][5][64];
        for (int z = 0; z < 64; z++) {
            for (int x = 0; x < 5; x++) {
                for (int y = 1; y < 5; y++) {
                    A[x][y][z] = a[(x+3*y)%5][x][z];
                }
            }
        }
        return A;
    }

    int[][][] chi(int[][][] a){
        int[][][] A = new int[5][5][64];
        for (int z = 0; z < 64; z++) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    A[x][y][z]= (A[x][y][z])^((a[(x+1)%5][y][z]^1) * a[(x+2)%5][y][z]);
                }
            }
        }
        return A;
    }
//1. For all triples (x, y,z) such that 0≤x<5, 0≤y<5, and 0≤z<w, let A′[x, y,z] = A[x, y,z].
//2. Let RC=O^w capital o not zero
//3. For j from 0 to l, let RC[2j
//–1]=rc(j+7ir).
//4. For all z such that 0≤z<w, let A′[0, 0,z]=A′[0, 0,z] ⊕ RC[z].
//5. Return A′.
    int[][][] iota(int[][][]a ,int r){
        int[][][] A = new int[5][5][64];
        for (int z = 0; z < 64; z++) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    A[x][y][z]= a[x][y][z];
                }
            }
        }
        int[] RC = new int[64];
        int l = 6;
        for (int j= 0; j < l; j++) {
            RC[(int) Math.pow(2.0,j)-1] = rc(j+7*r);
        }
        System.out.print(Arrays.toString(RC));
        for (int z = 0; z < 64; z++) {
            A[0][0][z] = A[0][0][z]^RC[z];
        }
        return A;
    }

    int rc(int t){
        if(t % 255 == 0){
            return 1;
        }
        StringBuilder R = new StringBuilder("10000000");
        for (int i = 1; i < (t % 255); i++) {
            R.insert(0,R);
            R.replace(0,1,String.valueOf((int) R.charAt(0)^((int) R.charAt(8))));
            R.replace(4,5,String.valueOf((int) R.charAt(4)^((int) R.charAt(8))));
            R.replace(5,6,String.valueOf((int) R.charAt(5)^((int) R.charAt(8))));
            R.replace(6,7,String.valueOf((int) R.charAt(6)^((int) R.charAt(8))));
            R = new StringBuilder(truncate(R,8));
            System.out.println(R);
        }
        System.out.println(R.charAt(0));
        return R.charAt(0);
    }
    String truncate(StringBuilder s,int l) {
        return s.substring(0,l);
    }

    int[] ROT(int[] a, int d){
        for (int z = 0; z < 64; z++) {
            a[(z+d)%64] = a[z];
        }
        return a;
    }
    int[] XOR(int[] a, int[] b){
        int i = 0;
        int[] c = new int[a.length];
        for (int z : a) {
            c[i] = z ^ b[i++];
        }
        return c;
    }





}


