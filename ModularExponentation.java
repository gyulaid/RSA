import java.math.BigInteger;

public class ModularExponentation {

    public ModularExponentation(){}
    private boolean firstCalculation;;

    public BigInteger doModularExponentation(BigInteger numA, BigInteger numB, BigInteger modulo){

        BigInteger temp = BigInteger.ONE;

        String exponentBinaryForm = numB.toString(2);

        firstCalculation = false;

        if ( numA.mod(modulo).equals(BigInteger.ZERO)){
            return BigInteger.ZERO;
        }


        for(int i = exponentBinaryForm.length()-1; i >= 0; i--){

            if (!firstCalculation){
                numA = numA.mod(modulo);
                firstCalculation = true;
            }
            else{
                numA = (numA.multiply(numA)).mod(modulo);
            }
            if(exponentBinaryForm.charAt(i) == '1'){
                temp = temp.multiply(numA);

            }
        }

        return temp.mod(modulo);

    }

}
