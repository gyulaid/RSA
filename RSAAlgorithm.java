import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class RSAAlgorithm implements Encode, Decode {




    private BigInteger EXPONENT;
    private BigInteger privateKey;
    private BigInteger primeP;
    private BigInteger primeQ;
    private BigInteger EulerStatement;
    public BigInteger publicKey;
    private BigInteger N;

    EuclideanAlgorithm euclideanAlgorithm = new EuclideanAlgorithm();
    ModularExponentation modularExponentation = new ModularExponentation();
    MillerRabinPrimeTestAlgorithm millerRabinPrimeTestAlgorithm = new MillerRabinPrimeTestAlgorithm();



    @Override
    public String encode(String text) {
        return buildEncryptedTextString(text);
    }

    @Override
    public String decode(String text) {
        return null;
    }

    private void generateKey(){
      primeP = new BigInteger(1024, new Random());
        primeQ = new BigInteger(1024, new Random());


        while(true) {

            primeP = new BigInteger(1024, new Random());
            if(millerRabinPrimeTestAlgorithm.isPrime(primeP, BigInteger.valueOf(101))) {
                System.out.println("first prime number generated");
                break;
            }

        }

        while(true) {

            primeQ = new BigInteger(1024, new Random());
            if(millerRabinPrimeTestAlgorithm.isPrime(primeQ, BigInteger.valueOf(101)) && !primeQ.equals(primeP)) {
                System.out.println("second prime number generated");
                break;
            }

        }


  //      primeP = BigInteger.probablePrime(16,new Random());
     //   primeQ = BigInteger.probablePrime(16, new Random());
        N = primeP.multiply(primeQ);

        EulerStatement = primeP.subtract(BigInteger.ONE)
                                .multiply(primeQ.subtract(BigInteger.ONE));

        while(true){
            EXPONENT = new BigInteger(1024, new Random());
            if(euclideanAlgorithm.doEuclideanAlgorithm(EXPONENT, EulerStatement).getGreatestCommonDivisor().equals(BigInteger.ONE)){
                System.out.println("Exponent generated");
                break;
            }
        }



        publicKey = euclideanAlgorithm.doEuclideanAlgorithm( (primeP.multiply(primeQ)) ,EXPONENT).getGreatestCommonDivisor();
        privateKey = euclideanAlgorithm.doEuclideanAlgorithm(EulerStatement,EXPONENT).getY();

        if (privateKey.signum() == -1){
            privateKey = modularExponentation.doModularExponentation(privateKey, BigInteger.ONE, EulerStatement);
        }


    }




    public String buildEncryptedTextString(String text ){
        generateKey();

        System.out.println("P: "+primeP);
        System.out.println("Q: "+primeQ);
        System.out.println("E: "+EXPONENT);
        System.out.println("N: "+N);
        System.out.println("PhiN: "+EulerStatement);
        System.out.println("Public key: "+publicKey);
        System.out.println("Private key: "+privateKey);


        System.out.println("Given text: "+text);

        String[] splittedText = text.split(("(?<=\\G....)"));

        String encryptedTestByStream = Arrays.stream(splittedText)
                .map(message -> new BigInteger(message.getBytes()))
                .map(messageBytes -> modularExponentation.doModularExponentation(messageBytes, EXPONENT, N))
                .map(String::valueOf)
                .collect(Collectors.joining(":"));


        System.out.println("encrypted text:");
        System.out.println(encryptedTestByStream);

        String[] splittedEncryptedText = encryptedTestByStream.split((":"));

        //Modular Exponentiation

        /*   String decryptedTextByStream = Arrays.stream(splittedEncryptedText)
                .map(BigInteger::new)
                .map(messageBytes -> modularExponentation.doModularExponentation(messageBytes, privateKey, N))
                .map(textMessage -> new String(textMessage.toByteArray()))
                .collect(Collectors.joining(""));
*/

        // Chinese Remainder

        String decryptedTextByStream = Arrays.stream(splittedEncryptedText)
                .map(BigInteger::new)
                .map(messageBytes -> ChineseRemainder(privateKey, primeP, primeQ, messageBytes, N))
                .map(textMessage -> new String(textMessage.toByteArray()))
                .collect(Collectors.joining(""));



        System.out.println("decrypted text:");
        System.out.println(decryptedTextByStream);

/*
        BigInteger msg = new BigInteger(text.getBytes());

        System.out.println(new String(msg.toByteArray()));

        BigInteger encryptedText = modularExponentation.doModularExponentation(msg, EXPONENT, N);

        System.out.println(new String(encryptedText.toByteArray()));

        BigInteger decryptedText = modularExponentation.doModularExponentation(encryptedText, privateKey, N);

        System.out.println(new String(decryptedText.toByteArray()));

*/







        return null;


    }

    public BigInteger ChineseRemainder(BigInteger privateKey, BigInteger primeP, BigInteger primeQ, BigInteger encryptedBytes, BigInteger N){
        BigInteger d1 = modularExponentation.doModularExponentation(privateKey, BigInteger.ONE, primeP.subtract(BigInteger.ONE));
        BigInteger d2 = modularExponentation.doModularExponentation(privateKey, BigInteger.ONE, primeQ.subtract(BigInteger.ONE));

        BigInteger mp = modularExponentation.doModularExponentation(encryptedBytes, d1, primeP);
        BigInteger mq = modularExponentation.doModularExponentation(encryptedBytes, d2, primeQ);

        BigInteger m1 = modularExponentation.doModularExponentation(mp, BigInteger.ONE, primeP);
        BigInteger m2 = modularExponentation.doModularExponentation(mq, BigInteger.ONE, primeQ);

        EuclideanResult primesEuclideanResult = euclideanAlgorithm.doEuclideanAlgorithm(primeP,primeQ);
        BigInteger m = (mp.multiply(primesEuclideanResult.getY().multiply(primeQ))
                .add(mq.multiply(primesEuclideanResult.getX().multiply(primeP))));

        BigInteger M = modularExponentation.doModularExponentation(m, BigInteger.ONE, N);

        return M;
    }






}
