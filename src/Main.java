import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

//Authors:   Patrick Tibbals, Iam McLean


class Main {

    final static KMACXOF256 k = new KMACXOF256();

    static String[] endcodedFile = null;

    static boolean run = true;
    static boolean mainMenu = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (mainMenu) {
            int choice = 0;

            while(choice != 1 && choice !=2 && choice !=3) {
                System.out.println("Enter choice:\n 1 - Symmetric Cryptography\n 2 - Elliptic Cryptography\n 3 - Exit");
                choice = insureValidity(scanner);
            }
            if(choice == 1){
                sym.menuPrompt(scanner);
            }else if (choice == 2){
                ecc.menuPrompt(scanner);
            } else if (choice == 3){
                System.out.println("bye bye");
                System.exit(1);
            }
        }
    }
    public static int insureValidity(Scanner scanner) {
        while(!scanner.hasNextInt()) {
            System.out.println("Enter Valid Choice");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
//    private static void menuPromptECC(Scanner s) {
//        while (run) {
//            System.out.println("""
//                    Select the service you would like:
//                        A) Generate elliptic key pair (s,V)
//                        B) Encrypt a given data file
//                        C) Decrypt a given symmetric cryptogram
//                        D) Sign a file
//                        E) Verify file and signature
//                        F) EXIT
//                    """);
//            String choice = s.nextLine();
//            switch (choice.toLowerCase()) {
//                case "a":
//                    generateKeyPair();
//                    return;
//                case "b":
//                    encryptionECC();
//                    return;
//                case "c":
//                    decryptionECC();
//                    return;
//                case "d":
//                    signFile();
//                    return;
//                case "e":
//                    verifySign();
//                    return;
//                case "f":
//                    System.out.println("Good Bye");
//                    run = false;
//                    break;
//                default:
//                    System.out.println("That is not a service try again");
//            }
//        }
//    }
//    private static void signFile(){}
//    private static void verifySign(){}
//
//    private static void generateKeyPair() {
//        Scanner scanner = new Scanner(System.in);
//        File publicKeyFile = null;
//        File privateKeyFile = null;
//        try {
//            System.out.println("Enter the file you want to write the public key-pair to: ");
//            publicKeyFile = new File(scanner.nextLine());
//
//            System.out.println("Enter the file you want to write the private key to: ");
//            privateKeyFile = new File(scanner.nextLine());
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            System.out.println("Enter your password: ");
//            String pw = scanner.nextLine();
//            EC.getKeyPair(pw);
//            assert publicKeyFile != null;
//            FileWriter publicKeyWriter = new FileWriter(publicKeyFile);
//            FileWriter privateKeyWriter = new FileWriter(privateKeyFile);
//            publicKeyWriter.write(String.valueOf(EC.V));
//            publicKeyWriter.close();
//            privateKeyWriter.write(String.valueOf(EC.s));
//            privateKeyWriter.close();
//        } catch (RuntimeException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//    static EdwardsPoint W;
//    static EdwardsPoint Z;
//
//    static void encryptionECC() {
//        Scanner s = new Scanner(System.in);
//        byte[] values = new byte[512];
//        SecureRandom sr = new SecureRandom();
//        sr.nextBytes(values);
//        BigInteger K = new BigInteger(Main.bytesToHex(values), 16).multiply(new BigInteger("4"));
//        W = EC.multScalar(K , (EdwardsPoint) keyPair.get(1));
//        Z = EC.multScalar(K , EC.G);
//
//
//        String m = "";
//
//        System.out.println("Enter 1 to input custom text or 2 for file loading");
//        int choice = 0;
//        while(choice != 1 && choice !=2){
//            choice = s.nextInt();
//
//        }
//        if(choice == 1){
//            System.out.println("Enter text for encryption");
//            String text = s.nextLine();
//            text = s.nextLine();
//            m = utils.textToHexString(text);
//        }else{
//            m = utils.textToHexString(gettingFileInfo(s));
//        }
//
//        String keka = Main.k.KMACJOB(String.valueOf(W.getX()), "", "PK", 1024 / 4);
//        String ke = keka.substring(0, keka.length() / 2);
//        String ka = keka.substring(keka.length() / 2);
//
//        String c = utils.XORhex(Main.k.KMACJOB(ke, "", "PKE", m.length()), m);
//        String t = Main.k.KMACJOB(ka, m, "PKA", 512 / 2);
//
//        encodedECCFile = new ArrayList<>();
//        encodedECCFile.add(Z);
//        encodedECCFile.add(c);
//        encodedECCFile.add(t);
//
//        System.out.println("Encoded successfully");
//        //System.out.println("Z = "+Z);
//        //System.out.println("c = "+c);
//        //System.out.println("t = "+t);
//        //System.out.println("G = "+EC.G);
//        //System.out.println("V = "+EC.V);
//        //System.out.println("W = "+W);
//
//    }
//
//    static void decryptionECC() {
//        if (encodedECCFile == null) {
//            System.out.println("You need to encrypt a file first!");
//            return;
//        }
//        try (Scanner scan = new Scanner(System.in)) {
//
//
//            System.out.println("Please enter the password: ");
//            String pw = scan.nextLine();
//
//            String str = k.KMACJOB( utils.textToHexString("String please"), "", "SK", 1024 / 4);
//            System.out.println("ANother W" +EC.multScalar(new BigInteger(str, 16).multiply(new BigInteger("4")),Z ));
//            BigInteger s = new BigInteger(str, 16).multiply(new BigInteger("4"));
//            W = EllipticCurve.multScalar(s, (EdwardsPoint) encodedECCFile.get(0));
//            String keka = Main.k.KMACJOB(String.valueOf(W.getX()), "", "PK", 1024 / 4);
//            String ke = keka.substring(0, keka.length() / 2);
//            String ka = keka.substring(keka.length() / 2);
//
//            String m = utils.XORhex(Main.k.KMACJOB(ke, "", "PKE", encodedECCFile.get(1).toString().length()),encodedECCFile.get(1).toString());
//            String tPrime = Main.k.KMACJOB(ka, m, "PKA", 512 / 2);
//
//            if (encodedECCFile.get(2).toString().equals(tPrime)) {
//                System.out.println("Accepted Input");
//            } else {
//                System.out.println("Incorrect t != t'");
//            }
//            //System.out.println("Z = "+(EdwardsPoint) encodedECCFile.get(0));
//            //System.out.println("c = "+encodedECCFile.get(1));
//            //System.out.println("t = "+encodedECCFile.get(2));
//            //System.out.println("G = "+EC.G);
//            //System.out.println("V = "+EC.V);
//            //System.out.println("W = "+W);
//        }
//    }
//
//
//        private static void menuPrompt(Scanner s) {
//        while (run) {
//            System.out.println("""
//                    Select the service you would like:
//                        A) Compute a plain cryptographic hash
//                        B) Compute an authentication tag (MAC)
//                        C) Encrypt a given data file
//                        D) Decrypt a given symmetric cryptogram
//                        E) EXIT
//                    """);
//            String choice = s.nextLine();
//            switch (choice.toLowerCase()) {
//                case "a":
//                   // symmetricMenu plainHash();
//                    return;
//                case "b":
//                    authenticationTag();
//                    return;
//                case "c":
//                    encryption();
//                    return;
//                case "d":
//                    decryption();
//                    return;
//                case "e":
//                    System.out.println("Good Bye");
//                    run = false;
//                    break;
//                default:
//                    System.out.println("That is not a service try again");
//            }
//        }
//    }



//    private static void authenticationTag() {
//        Scanner s = new Scanner(System.in);
//        String X = null;
//        System.out.println("Choose what you would like to hash: \n" +
//                "   A) file input\n   B) user input");
//        String choice = s.nextLine();
//        if (choice.equalsIgnoreCase("A")) {
//            X = gettingFileInfo(s);
//        } else if (choice.equalsIgnoreCase("B")) {
//            System.out.println("Enter the phrase you want to hash: ");
//            X = s.nextLine();
//
//        } else {
//            System.out.println("That is not a service try again: ");
//            authenticationTag();
//        }
//
//        System.out.println("Please enter a passphrase: ");
//        String K = s.nextLine();
//        System.out.println("Please enter a Customization String(optional): ");
//        String S = s.nextLine();
//        utils.printHex(k.KMACJOB(K,X,S,256));
//
//    }

//    private static void encryption() {
//        Scanner s = new Scanner(System.in);
//        byte[] values = new byte[64];
//        SecureRandom sr = new SecureRandom();
//        sr.nextBytes(values);
//        String z = bytesToHex(values);
//
//        String m = utils.textToHexString(gettingFileInfo(s));
//        System.out.println("Please enter a passphrase");
//        String pw = s.nextLine();
//
//
//        String keka = k.KMACJOB(z + pw, "", "S", 1024 / 4);
//        String ke = keka.substring(0, keka.length() / 2);
//        String ka = keka.substring(keka.length() / 2);
//
//        String c = utils.XORhex(k.KMACJOB(ke, "", "SKE", m.length()), m);
//        String t = k.KMACJOB(ka, m, "SKA", 512 / 2);
//        endcodedFile = new String[]{z,c,t};
//        System.out.println("Encoded successfully");
//    }

//    private static void decryption() {
//        if(endcodedFile==null){
//            System.out.println("You need to encrypt a file first!");
//            return;
//        }
//        try (Scanner s = new Scanner(System.in)) {
//            System.out.println("Please enter the password: ");
//            String pw = s.nextLine();
//
//
//            String keka = k.KMACJOB(endcodedFile[0] + pw, "", "S", 1024 / 4);
//            String ke = keka.substring(0, keka.length() / 2);
//            String ka = keka.substring(keka.length() / 2);
//
//            String m = utils.XORhex(k.KMACJOB(ke, "", "SKE", endcodedFile[1].length()), endcodedFile[1]);
//            String tPrime = k.KMACJOB(ka, m, "SKA", 512 / 2);
//
//            if (endcodedFile[2].equals(tPrime)) {
//                System.out.println("Accepted Input");
//            } else {
//                System.out.println("Incorrect t != t'");
//            }
//        }
//    }
