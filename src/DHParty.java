import java.math.BigInteger;
import java.security.SecureRandom;

class DHParty extends Party {
    private final BigInteger exponent;
    private final BigInteger share;

    BigInteger getShare() {
        return share;
    }

    DHParty(String name, BigInteger modulus, BigInteger generator) {
        super(name);
        SecureRandom random = new SecureRandom();
        exponent = generateExponent(modulus);
        share = generator.modPow(exponent, modulus);
    }

    DHParty(String name, long modulusLong, long generatorLong) {
        super(name);
        BigInteger modulus = BigInteger.valueOf(modulusLong);
        BigInteger generator = BigInteger.valueOf(generatorLong);
        exponent = generateExponent(modulus);
        share = generator.modPow(exponent, modulus);
    }

    private BigInteger generateExponent(BigInteger modulus) {
        SecureRandom random = new SecureRandom(this.toString().getBytes());
        BigInteger result = BigInteger.valueOf(random.nextLong()).mod(modulus);
        while (result.compareTo(BigInteger.ZERO) == 0
                && result.compareTo(BigInteger.ONE) == 0
                && result.compareTo(modulus.subtract(BigInteger.ONE)) == 0) {
            result = BigInteger.valueOf(random.nextLong()).mod(modulus);
        }
        return result;
    }
}
