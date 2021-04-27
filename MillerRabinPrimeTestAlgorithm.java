import java.math.BigInteger;

public class MillerRabinPrimeTestAlgorithm {

    private BigInteger d;
    private BigInteger S;

    ModularExponentation modularExponentation = new ModularExponentation();

    public boolean isPrime(BigInteger primeToTest, BigInteger base){

        if(primeToTest.equals(BigInteger.TWO)){ return true; }

        if(primeToTest.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            return false;
        }

        S = BigInteger.ZERO;
        BigInteger temp = primeToTest.subtract(BigInteger.ONE);

        while(temp.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            S = S.add(BigInteger.ONE);
            temp = temp.divide(BigInteger.TWO);
            d = temp;

        }

        if(modularExponentation.doModularExponentation(base, d, primeToTest) ==
                modularExponentation.doModularExponentation(BigInteger.ONE,BigInteger.ONE, primeToTest)){
            return true;
        }



        for(BigInteger i = BigInteger.valueOf(0); i.compareTo(S) < 0; i = i.add(BigInteger.ONE)){

            if(modularExponentation.doModularExponentation(base, BigInteger.TWO.pow(i.intValue()).multiply(d), primeToTest).equals(
                    modularExponentation.doModularExponentation(primeToTest.subtract(BigInteger.ONE), BigInteger.TWO, primeToTest))){

                return true;
            }
        }

        return false;
    }
}
