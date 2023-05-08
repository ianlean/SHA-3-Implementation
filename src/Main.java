import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

//Authors:   Patrick Tibbals, Iam McLean


class Main {
    final static Utils utils = new Utils();
    final static Keccak keccak = new Keccak();
    final static int rate = 256;
    final static int capacity = 576;

    final static KMACXOF256 k = new KMACXOF256();

    public static void main(String[] args) {
        while (true) {
            menuPrompt(new Scanner(System.in));
        }

    }


    private static void menuPrompt(Scanner s) {
        while (true) {
            System.out.println("""
                    Select the service you would like:
                        A) Compute a plain cryptographic hash
                        B) Compute an authentication tag (MAC)
                        C) Encrypt a given data file
                        D) Decrypt a given symmetric cryptogram
                    """);
            String choice = s.nextLine();
            switch (choice.toLowerCase()) {
                case "a":
                    plainHash();
                    return;
                case "b":
                    authenticationTag();
                    return;
                case "c":
                    System.out.println(encryption());
                    return;
                case "d":
                    decryption();
                    return;
                default:
                    System.out.println("That is not a service try again");
            }
        }
    }

    private static void plainHash() {
        Scanner s = new Scanner(System.in);
        System.out.println("Choose what you would like to hash: \n" +
                "   A) file input\n   B) user input");
        String choice = s.nextLine();
        if (choice.equalsIgnoreCase("A")) {
            String data = gettingFileInfo(s);
            System.out.println(k.KMACJOB("", data, "", 512 / 4));
        } else if (choice.equalsIgnoreCase("B")) {
            System.out.println("Enter the phrase you want to hash: ");
            String data = s.nextLine();
            System.out.println(k.KMACJOB("", data, "", 512 / 4));
        } else {
            System.out.println("That is not a service try again: ");
            plainHash();
        }
    }

    private static void authenticationTag() {
        //input will be "file" or "user input"
        String passphrase = null;
        Scanner s = new Scanner(System.in);
        String data = null;
        System.out.println("Choose what you would like to hash: \n" +
                "   A) file input\n   B) user input");
        String choice = s.nextLine();
        if (choice.equalsIgnoreCase("A")) {
            data = gettingFileInfo(s);
        } else if (choice.equalsIgnoreCase("B")) {
            System.out.println("Enter the phrase you want to hash: ");
            data = s.nextLine();

        } else {
            System.out.println("That is not a service try again: ");
            authenticationTag();
        }
        System.out.println(data);
        System.out.println("Please enter a passphrase: ");
        String passPhrase = s.nextLine();
        System.out.println("Please enter a Customization String(optional): ");
        String cStr = s.nextLine();
        System.out.println(k.KMACJOB(passPhrase, data, cStr, 512 / 4));
    }

    private static String encryption() {
        Scanner s = new Scanner(System.in);
        byte[] values = new byte[64];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(values);
        String z = bytesToHex(values);

        String m = utils.textToHexString(gettingFileInfo(s));
        System.out.println("Please enter a passphrase");
        String pw = s.nextLine();


        String keka = k.KMACJOB(z + pw, "", "S", 1024 / 4);
        String ke = keka.substring(0, keka.length() / 2);
        String ka = keka.substring(keka.length() / 2);

        String c = utils.XORhex(k.KMACJOB(ke, "", "SKE", m.length()), m);
        String t = k.KMACJOB(ka, m, "SKA", 512 / 4);

        return Arrays.toString(new String[]{z, c, t});
    }

    private static void decryption() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter a z: '");
        String z = s.nextLine();

        System.out.println("Please enter a c: ");
        String c = s.nextLine();
        System.out.println("Please enter a t: ");
        String t = s.nextLine();
        System.out.println("Please enter the password: ");
        String pw = s.nextLine();


        String keka = k.KMACJOB(z + pw, "", "S", 1024 / 4);
        String ke = keka.substring(0, keka.length() / 2);
        String ka = keka.substring(keka.length() / 2);

        String m = utils.XORhex(k.KMACJOB(ke, "", "SKE", c.length()), c);
        String tPrime = k.KMACJOB(ka, m, "SKA", 512 / 4);

        if (t == tPrime) {
            System.out.println("Accepted Input");
        } else {
            System.out.println("Incorrect t != t'");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String gettingFileInfo(Scanner s) {
        boolean done = false;
        String theString = null;
        while (!done) {
            System.out.println("Please enter the full path of the file:");
            File f = new File(s.nextLine());
            if (f.exists()) {
                try {
                    theString = new String(Files.readAllBytes(f.getAbsoluteFile().toPath()));
                    done = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ERROR: File doesn't exist. try again: ");
            }
        }
        return theString;
    }
}

//kmacxof
//Total input
//01 88 02 01 00 40 41 42 43 44 45 46 47 48 49 4A4B 4C 4D 4E 4F 50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00    00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00       00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00       00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 0000 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F30 31 32 33 34 35 36 37 38 39 3A 3B 3C 3D 3E 3F40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F80 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F90 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9FA0 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AFB0B1B2B3B4B5B6B7B8B9BABBBCBDBEBFC0C1C2C3C4C5C6C70001
//Bytepad
//01 88 01 20 4B 4D 41 43 01 A8 4D 79 20 54 61 67        67 65 64 20 41 70 70 6C 69 63 61 74 69 6F 6E 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00        00 00 00 00 00 00 00 00