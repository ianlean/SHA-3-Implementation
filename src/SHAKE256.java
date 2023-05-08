//Authors:   Patrick Tibbals, Iam McLean


class SHAKE256 {
    Utils utils = new Utils();
    Keccak keccak = new Keccak();
    public String Sponge(String X, String N, String S , int L){
        String input = "";
        String temp = "";
        String bytePad= "";

        bytePad = utils.keccakInput(N, S, 136);


        //utils.printHex(X);
        //utils.printHex(bytePad);


        long[] state = utils.hexToLong(bytePad);
        state = keccak.sha3_keccakf(state);
        //utils.printHex(utils.longToHex(state));

        while(X.length()-272 > 0){
            input = X.substring(0,272);
            X = X.substring(272);
            temp = utils.XORhex(input,utils.longToHex(state));
            //System.out.println("-----------xor-------------");
            //utils.printHex(temp);
            //System.out.println("------------------------");
            state = keccak.sha3_keccakf(utils.hexToLong(temp));
            //utils.printHex(utils.longToHex(state));

        }

        input = X;
        temp = utils.XORhex(padInput(input),utils.longToHex(state));
        state = keccak.sha3_keccakf(utils.hexToLong(temp));
        //utils.printHex(utils.longToHex(state));

        //utils.printHex(utils.longToHex(state));
        int finalSize = L;
        String output = "";
        while(finalSize-272 > 0){

            finalSize-=272;
            output += utils.longToHex(state).substring(0,272);
            state = keccak.sha3_keccakf(state);
        }
        output += utils.longToHex(state).substring(0,L/2);

        return output;

    }
    public static String padInput(String text) {

        if (text.length() > 272) {
            return text;
        }
        String paded = text + "04";
        while (paded.length() < 270) {
            paded += "00";
        }
        return paded + "80";

    }
}