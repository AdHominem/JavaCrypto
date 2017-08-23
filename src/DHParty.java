import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.SecureRandom;

class DHParty extends Party {

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

    @NotNull
    @Contract(pure = true)
    private BigInteger generateExponent(final BigInteger modulus) {
        final SecureRandom random = new SecureRandom();
        BigInteger result = BigInteger.valueOf(random.nextLong()).mod(modulus);
        while (result.compareTo(BigInteger.ZERO) == 0
                && result.compareTo(BigInteger.ONE) == 0
                && result.compareTo(modulus.subtract(BigInteger.ONE)) == 0) {
            result = BigInteger.valueOf(random.nextLong()).mod(modulus);
        }
        return result;
    }
}
