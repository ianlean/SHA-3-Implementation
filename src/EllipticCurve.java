import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

public class EllipticCurve implements Serializable {
    private Utils utils = new Utils();
    static BigInteger r = BigInteger.TWO.pow(446).subtract(new BigInteger("13818066809895115352007386748515426880336692474882178609894547503885"));
    static BigInteger d = new BigInteger("39081").negate();
    static BigInteger s;

    static BigInteger p = (BigInteger.TWO.pow(448).subtract(BigInteger.TWO.pow(224))).subtract(BigInteger.ONE);
    static EdwardsPoint V;
    static BigInteger n = r.multiply(new BigInteger("4"));
    static EdwardsPoint G = new EdwardsPoint();
    private ArrayList keyPair = new ArrayList();

    public EllipticCurve() {
        this.G = new EdwardsPoint(BigInteger.valueOf(8),false);
    }

    public ArrayList getKeyPair(String password) {
        KMACXOF256 k = new KMACXOF256();
  
        String str = k.KMACJOB( utils.textToHexString(password), "", "SK", 1024 / 4);

        s = new BigInteger(str, 16).multiply(new BigInteger("4")); //private key
        V = multScalar(s,G);

        keyPair.add(s);
        keyPair.add(V);

        return keyPair;
    }

    /**
     * Compute a square root of v mod p with a specified
     * least significant bit, if such a root exists.
     *
     * @param v the radicand.
     * @param p the modulus (must satisfy p mod 4 = 3).
     * @param lsb desired least significant bit (true: 1, false: 0).
     * @return a square root r of v mod p with r mod 2 = 1 iff lsb = true
     * if such a root exists, otherwise null.
     */
    public static BigInteger sqrt(BigInteger v, BigInteger p, boolean lsb) {
        assert (p.testBit(0) && p.testBit(1)); // p = 3 (mod 4)
        if (v.signum() == 0) {
            return BigInteger.ZERO;
        }
        BigInteger r = v.modPow(p.shiftRight(2).add(BigInteger.ONE), p);
        if (r.testBit(0) != lsb) {
            r = p.subtract(r); // correct the lsb
        }
        return (r.multiply(r).subtract(v).mod(p).signum() == 0) ? r : null;
    }

    public static EdwardsPoint multScalar(BigInteger s, EdwardsPoint G){

        if (s.equals(BigInteger.ZERO)) {return new EdwardsPoint();}

        if (s.signum() == -1) {
            s = s.negate();
            G = EllipticCurve.obtain0pposite(G);
        }

        String binaryS = s.toString(2);
        EdwardsPoint P = G;
        if (binaryS.length() == 1) return P;
        for (int i = 1; i < binaryS.length(); i++) {
            P = sumPoints(P,P);
            if (binaryS.charAt(i) == '1') {
                P = sumPoints(P,G);
            }
        }
        return P;

    }

    public static EdwardsPoint sumPoints(EdwardsPoint p1, EdwardsPoint p2) {
        BigInteger numeratorX = (p1.getX().multiply(p2.getY())).add(p1.getY().multiply(p2.getX())).mod(p);
        BigInteger denominatorX = (BigInteger.ONE.add(d.multiply(p1.getX()).multiply(p2.getX()).multiply(p1.getY()).multiply(p2.getY())));
        BigInteger X =numeratorX.multiply(denominatorX.modInverse(EllipticCurve.p)).mod(p);

        BigInteger numeratorY = (p1.getY().multiply(p2.getY())).subtract(p1.getX().multiply(p2.getX()));
        BigInteger denominatorY = (BigInteger.ONE.subtract(d.multiply(p1.getX()).multiply(p2.getX()).multiply(p1.getY()).multiply(p2.getY())));
        BigInteger Y = numeratorY.multiply(denominatorY.modInverse(EllipticCurve.p)).mod(p);

        return new EdwardsPoint(X,Y);
    }

    static EdwardsPoint obtain0pposite(EdwardsPoint P) {
        P.setX(P.getX().multiply(BigInteger.valueOf(-1)));
        return P;
    }

    void printG() {
        System.out.println(this.G);
    }
}
