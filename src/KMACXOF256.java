//Authors:   Patrick Tibbals, Iam McLean


public class KMACXOF256 {
    Utils utils = new Utils();
//      System.out.println("length1111"+utils.textToHexString(K).length());
//      String newX = utils.bytepad((utils.encode_string(utils.textToHexString(K))), 136) + utils.textToHexString(data) + utils.right_encode(0);
//      System.out.println(newX);
//      utils.printHex(new SHAKE256().Sponge(newX,"KMAC","My Tagged Application",256));
   public String KMACJOB(String K, String X, String S, int L) {
       System.out.println(K);
       String newX = utils.bytepad((utils.encode_string(utils.textToHexString(K))), 136) + utils.textToHexString(X) + utils.right_encode(0);
       System.out.println(utils.textToHexString(K).length());
       System.out.println(newX);
       return new SHAKE256().Sponge(newX,"KMAC",S,L);
   }
}