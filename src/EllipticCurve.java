import java.awt.*;
import java.math.BigInteger;

public class EllipticCurve {
    private Utils utils = new Utils();
    private static final BigInteger r = BigInteger.valueOf(2).pow(446).subtract(new BigInteger("13818066809895115352007386748515426880336692474882178609894547503885"));

    private static BigInteger d = new BigInteger("39081").negate();
    private static BigInteger p = new BigInteger("181709681073901722637330951972001133588410340171829515070372549795146003961539585716195755291692375963310293709091662304773755859649779");
    private EdwardsPoint V = new EdwardsPoint();
    private static BigInteger n = r.multiply(new BigInteger("4"));
    private EdwardsPoint G = new EdwardsPoint();

    public EllipticCurve() {
        System.out.println(r);
        System.out.println(p);
        System.out.println(n);
        BigInteger x = BigInteger.valueOf(8);
        BigInteger radicand = (BigInteger.ONE.subtract(x.pow(2)).modInverse((BigInteger.ONE.subtract(d.multiply(x.pow(2)))).mod(this.p)));
        BigInteger y = sqrt(radicand,this.p,true);
    }

    public void getKeyPair(String password) {
        KMACXOF256 k = new KMACXOF256();
        // s = kmac(pw, "", 512, "SK")
        String s = k.KMACJOB( utils.textToHexString(password), "", "SK", 1024 / 4);
        //s  = 4s
        s = new BigInteger(s, 16).multiply(new BigInteger("4")).toString();
         //BigInteger s = BigInteger.valueOf(4).multiply(new BigInteger(intermediate)); // private key
        // V = s*G
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

    public EdwardsPoint multScalar(BigInteger k, EdwardsPoint P){
        // s = (sk sk-1 ... s1 s0)2, sk = 1.
        V = P; // initialize with sk*P, which is simply P
        for (BigInteger i = k.subtract(BigInteger.ONE); i.compareTo(BigInteger.ZERO)==1; i.subtract(BigInteger.ONE)) {
            V = sumPoints(V,V);
            if (k.testBit(i.intValue())){
                V = sumPoints(V,P);
            }
        }
        return V; // now finally V = s*P
    }

    public EdwardsPoint sumPoints(EdwardsPoint v,EdwardsPoint p) {
        BigInteger numeratorX = (v.getX().multiply(p.getY())).add(v.getY().multiply(p.getX()));
        BigInteger denominatorX = (BigInteger.ONE.add(d.multiply(v.getX()).multiply(p.getX()).multiply(v.getY()).multiply(p.getY())));
        BigInteger X =numeratorX.multiply(denominatorX.modInverse(this.p)).mod(this.p);

        BigInteger numeratorY = (v.getY().multiply(p.getY())).subtract(v.getX().multiply(p.getX()));
        BigInteger denominatorY = (BigInteger.ONE.subtract(d.multiply(v.getX()).multiply(p.getX()).multiply(v.getY()).multiply(p.getY())));
        BigInteger Y = numeratorY.multiply(denominatorY.modInverse(this.p)).mod(this.p);

        return new EdwardsPoint(X,Y);
    }
    public void neutralElement() {

    }
    public void curvePoint(BigInteger x, BigInteger y) {

    }
    void comparePoints() {

    }

    void obtain0pposite() {

    }


}
