import java.io.Serializable;
import java.math.BigInteger;

public class EdwardsPoint implements Serializable  {
    private BigInteger x = BigInteger.ZERO;
    private BigInteger y = BigInteger.ZERO;

    private static BigInteger d = new BigInteger("39081").negate();

    EdwardsPoint(BigInteger x, boolean leastSignificantBit) {
        this.x = x;

        //𝑦 = ±√(1 − 𝑥2)/(1 + 376014𝑥2) mod 𝑝
        BigInteger x2 = x.modPow(new BigInteger("2"),  EllipticCurve.p);
        BigInteger numerator = BigInteger.ONE.subtract(x2);
        BigInteger denominator = BigInteger.ONE.add(d.multiply(BigInteger.valueOf(-1))
                .multiply(x2).mod( EllipticCurve.p));
        this.y = EllipticCurve.sqrt(numerator.multiply(denominator.modInverse(EllipticCurve.p)),
                EllipticCurve.p, leastSignificantBit);
    }
    public EdwardsPoint() {
        this.x = new BigInteger("0");
        this.y = new BigInteger("1");
    }
    public EdwardsPoint(BigInteger xCoordinate, BigInteger yCoordinate) {
        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    public BigInteger getX() {
        return x;
    }
    public BigInteger getY() {
        return y;
    }

    public void setX(BigInteger X){
        this.x = X;
    }
    public void setY(BigInteger Y){
        this.y = Y;
    }

    EdwardsPoint sumPoints(EdwardsPoint other) {
        BigInteger xNumerator = x.multiply(other.y).add(y.multiply(other.x));
        BigInteger intermediate = d
                .multiply(x).multiply(y).multiply(other.x).multiply(other.y);
        BigInteger xDenominator = BigInteger.ONE.add(intermediate);
        BigInteger yNumerator = y.multiply(other.y).subtract(x.multiply(other.x));
        BigInteger yDenominator = BigInteger.ONE.subtract(intermediate);
        BigInteger newX = xNumerator.multiply(xDenominator.modInverse(EllipticCurve.p))
                .mod(EllipticCurve.p);
        BigInteger newY = yNumerator.multiply(yDenominator.modInverse(EllipticCurve.p))
                .mod(EllipticCurve.p);
        return new EdwardsPoint(newX, newY);
    }


    @Override
    public String toString() {
        return "x -> " + this.x + "\n" + "y -> " + this.y;
    }
}
