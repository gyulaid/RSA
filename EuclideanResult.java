import java.math.BigInteger;

public class EuclideanResult {

    private BigInteger greatestCommonDivisor;
    private BigInteger X;
    private BigInteger Y;


    private EuclideanResult(EuclideanResultBuilder builder){
        this.greatestCommonDivisor = builder.greatestCommonDivisor;
        this.X = builder.X;
        this.Y = builder.Y;
    }


    public BigInteger getGreatestCommonDivisor(){
        return greatestCommonDivisor;
    }

    public BigInteger getX(){
        return X;
    }

    public BigInteger getY(){
        return Y;
    }


    public static class EuclideanResultBuilder{
        private BigInteger greatestCommonDivisor;
        private BigInteger X;
        private BigInteger Y;

        public EuclideanResultBuilder(){}

        public EuclideanResultBuilder greatestCommonDivisor(BigInteger greatestCommonDivisor){
            this.greatestCommonDivisor = greatestCommonDivisor;
            return this;
        }

        public EuclideanResultBuilder X(BigInteger X){
            this.X = X;
            return this;
        }

        public EuclideanResultBuilder Y(BigInteger Y){
            this.Y = Y;
            return this;
        }

        public EuclideanResult build(){
            EuclideanResult results = new EuclideanResult(this);
            return results;
        }
    }


}
