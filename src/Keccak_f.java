import java.util.Arrays;
//rc The function that generates the variable bits of the round constants.
//RC For a round of a KECCAK-p permutation, the round constant.
public class Keccak_f {
    final int[] rhoStep = {21,136,105,45,15,120,78,210,66,253,28,91,0,1,190,55,276,36,300,6,153,231,3,10,171};

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

  
    void initState(){
        for (int i = 0; i < (r+c); i++) {
            currState += "0";            
        }

    }
    // Each pass is size r = 1024 and c=576
    // Save the prev state to apply on current for sponge
     public Keccak_f(){
        initState();
    //     prevR = bitString;
    //     //int[][][] A = bitStringToMatrix(bitString);
    //    // print(A);
    //     //System.out.println("\n----------------------------------------" +
    //            // "-----------------------------------------------------" +
    //            // "-----------------------------------");
    //     //roundFunction(24,A);
    //     //print(A);
    //     //int[][][] B = Arrays.copyOf(A,A.length);
    //     //System.out.println(B);
     }

    // Sponge Requirements -> f,pad,r
    // Input: N= a bit string and d= bitlength
    //String prevState = "";
    String currState = "";
    int r = 1024;
    int c = 576;
    public String Sponge(String message , int d){
        // Absorb
        while(message.length() > 0){
            //XOR currState against message block size 1024
            currState.replace(currState.substring(0,r),XOR(currState.substring(0, r).toCharArray(), message.substring(0, r).toCharArray()).toString());
            message = message.substring(r);
            //Run state through keccak-f
            currState = f(currState);
            //xor message against r
        }

        // Sequeeze
        String z = "";
        while(z.length() >= d){
            z += truncate(new StringBuilder(currState),r);
            if(d <= z.length()){
                return truncate(new StringBuilder(z), d);
            }
            currState = f(currState);

        }
        return z;

    }
    public String f(String bitString){
        int[][][] A = bitStringToMatrix(bitString);
        int[][][] B = new int[5][5][64];
       // print(A);
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");
        B = roundFunction(24,A);
        return matrixToBitString(B);
    }

    int[][][] roundFunction(int rounds, int[][][] A){
        print(A);
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < rounds; i++) {
           A = iota(chi(pi(rho(theta(A)))),i);
        
           //System.out.println("\n A above B below -------------------------------------------------------------\n");
        }
        String temp = matrixToBitString(A);
        System.out.println(temp);
        System.out.println(temp.length());
        return A;
    }

    int[][][] theta(int[][][] a){
        int[][][] A = new int[5][5][64];
        A = a;
      
        //System.out.println("\n\nStarting form above--------------------------------------------------------------------------------------\n");
        int[][] C = new int[5][64], D= new int[5][64];
        for (int x = 0; x < 5; x++) {
            C[x] = a[x][0];
      //      System.out.println(Arrays.toString(C[x]));
            for (int y = 1; y < 5; y++) {
//                C[x] = C[x]("XOR HERE")A[x][y];
                C[x] = XOR(C[x],a[x][y]);
            }
        }
        for (int x = 0; x < 5; x++) {
            D[x] =  XOR(C[(x-1+5)%5],(ROT(C[(x+1)%5],1)));
            for (int y = 0; y < 5; y++) {
                A[x][y] = XOR(a[x][y],D[x]);
            }
        }
        //print(A);
        //System.out.println("\n\nEndTheta--------------------------------------------------------------------------------------\n");

        return A;
    }

    int[][][] rho(int[][][] a){
       // print(a);
        //System.out.println("Starting above new below \n");
        int[][][] A = new int[5][5][64];
        for (int z = 0; z < 64; z++) {
            A[0][0][z] = a[0][0][z];
        }

        int[] cord = new int[]{1,0};
        //                    (x,y)

        for (int t = 0; t < 23; t++) {
            for (int z = 0; z < 64; z++) {
                A[cord[0]][cord[1]][z] = a[cord[0]][cord[1]][(z+rhoStep[cord[0]+cord[1]])%64];
            }

            int x = cord[0];
            int y = cord[1];
            cord[0] = y;
            cord[1] = (2*x+3*y)%5;
        }
        //print(A);
        //System.out.println("\n\nEndRho--------------------------------------------------------------------------------------\n");

        return A;
    }

    int[][][] pi(int[][][] a){
        //print(a);
        //System.out.println("\n\nStarting above ending below--------------------------------------------------------------------------------------\n");

        int[][][] A = new int[5][5][64];
        //A = a;
        for (int z = 0; z < 64; z++) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    A[x][y][z] = a[(x+3*y)%5][x][z];
                }
            }
        }
        //print(A);
        return A;
    }

    int[][][] chi(int[][][] a){
        //print(a);
        //System.out.println("\n\nStarting above ending below--------------------------------------------------------------------------------------\n");

        int[][][] A = new int[5][5][64];
        for (int z = 0; z < 64; z++) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    A[x][y][z]= (A[x][y][z])^((a[(x+1)%5][y][z]^1) * a[(x+2)%5][y][z]);
                }
            }
        }
        
        //print(A);
        //System.out.println("\n\n---------------------------------------------\n\n");
        return A;
    }

    int[][][] iota(int[][][]a ,int r){
        //System.out.println("\n\nIOTA--------------------------------------------------------------------------------------\n");

        //print(a);
        //System.out.println("\n\nStarting above ending below--------------------------------------------------------------------------------------\n");

        int[][][] A = new int[5][5][64];
        A = a;

        int[] RC = new int[64];
        int l = 6;
        for (int j= 0; j < l; j++) {
            RC[(int) Math.pow(2.0,j)-1] = rc(j+7*r);
        }
        //System.out.print(Arrays.toString(RC));
        for (int z = 0; z < 64; z++) {
            A[0][0][z] = (a[0][0][z])^(RC[z]);
        }
        //print(A);
        //System.out.println("\n\n---------------------------------------------\n\n");
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
        return A;
    }

    String matrixToBitString(int[][][] A){
        String bitString = "";
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 64; z++) {
                    bitString += Integer.toString(A[x][y][z]);
                }
            }
        }
        return bitString;
    }

    int toHex(String hex) {
        return Integer.parseInt(hex,16);
    }

    int rc(int t){
        if(t % 255 == 0){
            return 1;
        }
        StringBuilder R = new StringBuilder("10000000");
        for (int i = 1; i < (t % 255); i++) {
            //R.insert(0,R);
            R.insert(0,'0');
            R.replace(0,1,String.valueOf((int) R.charAt(0)^((int) R.charAt(8))));
            R.replace(4,5,String.valueOf((int) R.charAt(4)^((int) R.charAt(8))));
            R.replace(5,6,String.valueOf((int) R.charAt(5)^((int) R.charAt(8))));
            R.replace(6,7,String.valueOf((int) R.charAt(6)^((int) R.charAt(8))));
            R = new StringBuilder(truncate(R,8));
           // System.out.println(R);
        }
        //System.out.println(R.charAt(0));
        return Integer.parseInt(RC[0]);
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
    char[] XOR(char[] a, char[] b){
        int i = 0;
        char[] c = new char[a.length];
        for (char z : a) {
            if(z == b[i]){
                c[i] = '0';
            }else{
                c[i] = '1';
            }
            
        }
        return c;
    }




}


