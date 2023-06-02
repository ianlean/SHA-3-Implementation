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

