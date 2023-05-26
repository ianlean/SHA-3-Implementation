import java.math.BigInteger;

public class EdwardsPoint {
    private BigInteger x;
    private BigInteger y;

    public EdwardsPoint() {
        this.x = new BigInteger("0");
        this.y = new BigInteger("0");
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


}
