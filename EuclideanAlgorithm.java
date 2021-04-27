import java.math.BigInteger;

public class EuclideanAlgorithm {




    public EuclideanResult doEuclideanAlgorithm(BigInteger numA, BigInteger numB){
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger lastX = BigInteger.ONE;
        BigInteger lastY = BigInteger.ZERO;
        BigInteger temp = BigInteger.ZERO;
        BigInteger greatestCommonDivisor = BigInteger.ZERO;


        while(!numB.equals(BigInteger.ZERO)){
            BigInteger quotient = numA.divide(numB);
            BigInteger remainder = numA.mod(numB);

            numA = numB;
            numB = remainder;

            if(!remainder.equals(BigInteger.ZERO)) {
                greatestCommonDivisor = remainder;

                temp = x;
                x = lastX.subtract(quotient.multiply(x));
                lastX = temp;

                temp = y;
                y = lastY.subtract(quotient.multiply(y));
                lastY = temp;
            }



        }



        return new EuclideanResult.EuclideanResultBuilder().greatestCommonDivisor(greatestCommonDivisor)
                                                            .X(x)
                                                            .Y(y)
                                                            .build();
    }


}
