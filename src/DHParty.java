import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

class DHParty extends Party {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final BigInteger exponent;
    private final BigInteger share;

    BigInteger getShare() {
        return share;
    }

    DHParty(final String name, final BigInteger modulus, final BigInteger generator) {
        super(name);
        exponent = generateExponent(modulus);
        share = generator.modPow(exponent, modulus);
    }

    DHParty(final String name, final long modulusLong, final long generatorLong) {
        super(name);
        final BigInteger modulus = BigInteger.valueOf(modulusLong);
        final BigInteger generator = BigInteger.valueOf(generatorLong);
        exponent = generateExponent(modulus);
        share = generator.modPow(exponent, modulus);
    }

    private BigInteger generateExponent(final BigInteger modulus) {
        BigInteger result = null;
        try {
            final SecureRandom random = new SecureRandom(this.toString().getBytes("UTF-8"));
            result = BigInteger.valueOf(random.nextLong()).mod(modulus);
            while (result.compareTo(BigInteger.ZERO) == 0
                    && result.compareTo(BigInteger.ONE) == 0
                    && result.compareTo(modulus.subtract(BigInteger.ONE)) == 0) {
                result = BigInteger.valueOf(random.nextLong()).mod(modulus);
            }
        } catch (UnsupportedEncodingException e) {
            logger.severe("UTF-8 is not supported on your platform");
        }
        return result;
    }
}
