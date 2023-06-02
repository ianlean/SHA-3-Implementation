import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ecc {
    final static Utils utils = new Utils();
    final static KMACXOF256 k = new KMACXOF256();
    static EllipticCurve EC = new EllipticCurve();

    static ArrayList encodedECCFile = null;

    static boolean run = true;
    static EdwardsPoint W;
    static EdwardsPoint Z;

    static void menuPrompt(Scanner s) {

        String choice = null;
        while (run) {
            System.out.println("""
                    Select the service you would like:
                        A) Generate elliptic key pair (s,V)
                        B) Encrypt a given data file
                        C) Decrypt a given symmetric cryptogram
                        D) Sign a file
                        E) Verify file and signature
                        F) EXIT
                    """);
            while(choice==null){
                choice = s.next();
            }
            switch (choice.toLowerCase()) {
                case "a":
                    generateKeyPair();
                    return;
                case "b":
                    encryptionECC();
                    return;
                case "c":
                    decryptionECC();
                    return;
                case "d":
                    signFile();
                    return;
                case "e":
                    verifySign();
                    return;
                case "f":
                    System.out.println("Good Bye");
                    run = false;
                    return;
                default:
                    System.out.println("That is not a service try again");
                    choice = null;
            }
        }
    }

    private static void generateKeyPair() {
        Scanner scanner = new Scanner(System.in);
        FileOutputStream publicKeyOut = null;
        FileOutputStream privateKeyOut = null;
        ObjectOutputStream objectOut = null;
        ObjectOutputStream objectOut2 = null;
        try {
            System.out.println("Enter the file you want to write the public key to: ");
            publicKeyOut = new FileOutputStream(scanner.nextLine());
            objectOut = new ObjectOutputStream(publicKeyOut);

            System.out.println("Enter the file you want to write the private key to: ");
            privateKeyOut = new FileOutputStream(scanner.nextLine());
            objectOut2 = new ObjectOutputStream(privateKeyOut);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Enter your password: ");
            String pw = scanner.nextLine();
            EC.getKeyPair(pw);
            assert publicKeyOut != null;

            assert objectOut != null;
            objectOut.writeObject(EC.V);
            objectOut.close();
            assert objectOut2 != null;
            objectOut2.writeObject(EC.s);
            objectOut2.close();
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("V "+EC.V);
    }


    static void encryptionECC()  {
        Scanner s = new Scanner(System.in);
        byte[] values = new byte[512];
        System.out.println("Enter the file with the public key: ");
        EdwardsPoint temp = null;
        while (temp == null){
            try {
                FileInputStream fileInputStream = new FileInputStream(s.nextLine());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                temp = (EdwardsPoint) objectInputStream.readObject();
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Invalid file location");
            }
        }


        SecureRandom sr = new SecureRandom();
        sr.nextBytes(values);
        BigInteger K = new BigInteger(utils.bytesToHex(values), 16).multiply(new BigInteger("4"));
        W = EC.multScalar(K , temp);
        Z = EC.multScalar(K , EC.G);


        String m = "";

        System.out.println("Enter 1 to input custom text or 2 for file loading");
        int choice = 0;
        while(choice != 1 && choice !=2){
            choice = s.nextInt();

        }
        if(choice == 1){
            System.out.println("Enter text for encryption");
            String text = s.nextLine();
            text = s.nextLine();
            m = utils.textToHexString(text);
        }else{
            m = utils.textToHexString(utils.gettingFileInfo(s));
        }
        System.out.println(m);

        String keka = Main.k.KMACJOB(String.valueOf(W.getX()), "", "PK", 1024 / 4);
        String ke = keka.substring(0, keka.length() / 2);
        String ka = keka.substring(keka.length() / 2);

        String c = utils.XORhex(Main.k.KMACJOB(ke, "", "PKE", m.length()), m);
        String t = Main.k.KMACJOB(ka, m, "PKA", 512 / 2);

        encodedECCFile = new ArrayList<>();
        encodedECCFile.add(Z);
        encodedECCFile.add(c);
        encodedECCFile.add(t);


            System.out.println("Z = "+(EdwardsPoint) encodedECCFile.get(0));
            System.out.println("c = "+encodedECCFile.get(1));
            System.out.println("t = "+encodedECCFile.get(2));
            System.out.println("G = "+EC.G);
            System.out.println("V = "+EC.V);
            System.out.println("W = "+W);
        FileOutputStream ZCTout = null;
        ObjectOutputStream objectOut = null;

        try {
            System.out.println("Enter the file you want to write the ciphertext to: ");
            ZCTout = new FileOutputStream(s.nextLine());
            objectOut = new ObjectOutputStream(ZCTout);

        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }

        try {
            assert ZCTout != null;
            assert objectOut != null;

            objectOut.writeObject(encodedECCFile);
            objectOut.close();
            encodedECCFile = null;
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }

        System.out.println("Encoded successfully");

    }

    static void decryptionECC() {
        Scanner scanner = new Scanner(System.in);

        while (encodedECCFile == null){
            System.out.println("Enter file name of ciphertext:");
            try {
                FileInputStream fileInputStream = new FileInputStream(scanner.nextLine());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                encodedECCFile = (ArrayList) objectInputStream.readObject();
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Invalid file location");
            }
        }
        System.out.println(encodedECCFile.toString());
        Z = (EdwardsPoint) encodedECCFile.get(0);
        try {

            System.out.println("Please enter the password: ");
            String pw = scanner.nextLine();

            String str = k.KMACJOB( utils.textToHexString(pw), "", "SK", 1024 / 4);
            System.out.println("Another W" +EC.multScalar(new BigInteger(str, 16).multiply(new BigInteger("4")),Z ));
            BigInteger s = new BigInteger(str, 16).multiply(new BigInteger("4"));
            W = EllipticCurve.multScalar(s, (EdwardsPoint) encodedECCFile.get(0));
            String keka = Main.k.KMACJOB(String.valueOf(W.getX()), "", "PK", 1024 / 4);
            String ke = keka.substring(0, keka.length() / 2);
            String ka = keka.substring(keka.length() / 2);

            String m = utils.XORhex(Main.k.KMACJOB(ke, "", "PKE", encodedECCFile.get(1).toString().length()),encodedECCFile.get(1).toString());
            String tPrime = Main.k.KMACJOB(ka, m, "PKA", 512 / 2);
            System.out.println(utils.hextoString(m));

            if (encodedECCFile.get(2).toString().equals(tPrime)) {
                System.out.println("Accepted Input");
            } else {
                System.out.println("Incorrect t != t'");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Z = "+(EdwardsPoint) encodedECCFile.get(0));
        System.out.println("c = "+encodedECCFile.get(1));
        System.out.println("t = "+encodedECCFile.get(2));
        System.out.println("G = "+EC.G);
        System.out.println("V = "+EC.V);
        System.out.println("W = "+W);
        encodedECCFile = null;
    }


    static EdwardsPoint U ;

    
    private static void signFile(){
                Scanner scanner = new Scanner(System.in);

        EdwardsPoint V = null;
        EdwardsPoint signature = null;

       while (V == null){
           System.out.println("Enter the file with the public key:");
           try {
               FileInputStream fileInputStream = new FileInputStream(scanner.nextLine());
               ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
               V = (EdwardsPoint) objectInputStream.readObject();
           } catch (IOException |ClassNotFoundException e) {
               e.printStackTrace();
               System.out.println("Invalid file location");
           }
       }


        System.out.println("Enter 1 to input custom text or 2 for file loading");
        int choice = 0;
        while(choice != 1 && choice != 2){
            choice = scanner.nextInt();
            if(choice != 1 && choice != 2){
                System.out.println("Try valid inputs 1 or 2");
            }
        }
        String m = "";
        if(choice == 1){
            System.out.println("Enter text for signing");
            String text = scanner.nextLine();
            text = scanner.nextLine();
            m = utils.textToHexString(text);
        }else{
            m = utils.textToHexString(utils.gettingFileInfo( new Scanner(System.in)));
        }
        System.out.println("Step 1   " + m);


        System.out.println("Enter passphrase");
        String pw = scanner.nextLine();
        pw = scanner.nextLine();
        String str = k.KMACJOB(utils.textToHexString(pw), "", "SK", 1024 / 4);
        BigInteger s = new BigInteger(str, 16).multiply(new BigInteger("4"));
        String str1 = k.KMACJOB(s.toString(16), m, "N", 1024 / 4);
        BigInteger kNum = new BigInteger(str1, 16).multiply(new BigInteger("4")); // this is 'k' in the documentation
        U = EC.multScalar(kNum,EC.G);

        System.out.println("Step 2   " + m);

        String h = k.KMACJOB(String.valueOf(U.getX()), m, "T", 1024 / 4);
        System.out.println("h ===="+ new BigInteger(h,16));
        BigInteger z = (kNum.subtract(new BigInteger(h,16).multiply(s))).mod(EC.r);
        System.out.println("z ===="+z);

        System.out.println(EC.multScalar(z, EC.G));
        FileOutputStream ZCTout = null;
        ObjectOutputStream objectOut = null;
        try {
            System.out.println("Enter the file you want to write the signature to: ");
            ZCTout = new FileOutputStream(scanner.nextLine());
            objectOut = new ObjectOutputStream(ZCTout);
            objectOut.writeObject(new EdwardsPoint(new BigInteger(h,16),z));
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }


        System.out.println("U = "+U);

    }

    private static void verifySign() {
        Scanner scanner = new Scanner(System.in);
        EdwardsPoint V = null;
        EdwardsPoint signature = null;

       while (V == null){
           System.out.println("Enter the file with the public key:");
           try {
               FileInputStream fileInputStream = new FileInputStream(scanner.nextLine());
               ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
               V = (EdwardsPoint) objectInputStream.readObject();
           } catch (IOException |ClassNotFoundException e) {
               e.printStackTrace();
               System.out.println("Invalid file location");
           }
       }

        String m;
        System.out.println("Enter the file text you want signature verified");
        m = utils.textToHexString(utils.gettingFileInfo(new Scanner(System.in)));
        System.out.println("Step 3    " + m);

        while (signature == null){
            System.out.println("Enter the file with the signature:");
            try {
                FileInputStream fileInputStream = new FileInputStream(scanner.nextLine());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                signature = (EdwardsPoint) objectInputStream.readObject();
            } catch (IOException |ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Invalid file location");
            }
        }
        BigInteger z = signature.getY();

        BigInteger h = signature.getX();
        System.out.println("h ===="+h);
        System.out.println("z ===="+z);

        // System.out.println("h = "+h.toString(16));

        // System.out.println("z = "+z);
        // System.out.println("G = "+EC.G);

        EdwardsPoint p = EllipticCurve.multScalar(z, EC.G);
        System.out.println(p);
        EdwardsPoint p1 = EllipticCurve.multScalar(h,V);

        EdwardsPoint finalU = EllipticCurve.sumPoints(p,p1);
        //src/signtext.txt hi src/signature.txt hi.txt src/signtext.txt src/signature.txt
        // System.out.println("G = "+EC.G);
        // System.out.println("V = "+V);

        System.out.println("U = "+finalU);
        System.out.println("STring x"+String.valueOf(finalU.getX()));
        System.out.println("hex x"+ (finalU.getX().toString(16)));
        System.out.println("h ===="+h);

        System.out.println("Step 4    " + m);

        if (new BigInteger(k.KMACJOB(String.valueOf(finalU.getX()),m,"T",1024/4),16).equals(h)) {
            System.out.println("Verified: Correct Signature");
        } else {
            System.out.println("Incorrect Signature");
        }

    }
}
