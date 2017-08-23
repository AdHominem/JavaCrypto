import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public final class CryptoUtil {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static int BYTE_MAX = 255;

    private CryptoUtil() {}

    /**
     * @param data The input as bytes. Note that this function only handles the least significant byte of any integer
     *             and will not guarantee the integrity of the most significant ones
     * @param key  The byte key. Note that if you use any key larger than 255 it will wrap around
     * @return An array of xored bytes
     */
    @NotNull
    @Contract(pure = true)
    static int[] xor(final int[] data, final int key) {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i] ^ key;
        }
        return result;
    }

    /**
     * @param message The input message.
     * @param key  The byte key. Note that if you use any key larger than 255 it will wrap around
     * @return An array of xored bytes. Note that returning a String would be impractical since many chars would
     * be unprintable
     */
    @NotNull
    @Contract(pure = true)
    static int[] xor(final String message, final int key) {
        return xor(message.chars().toArray(), key);
    }

    @NotNull
    @Contract(pure = true)
    static Map<String, Integer> crackXor(final int... cipher) {
        final ConcurrentHashMap<String, Integer> result = new ConcurrentHashMap<>();
        for (int key = 0; key != BYTE_MAX; key++) {
            final int[] plain = xor(cipher, key);
            final int score = getScore(plain);
            final int DETECTION_LIMIT = 10;
            if (score > DETECTION_LIMIT) {
                result.put(String.valueOf(byteArrayToCharArray(plain)), score);
            }
        }
        return result;
    }

    @NotNull
    @Contract(pure = true)
    private static int getScore(final int... plaintext) {
        int score = 0;
        for (final int b : plaintext) {
            if (Character.isLetterOrDigit(b) || Character.isWhitespace(b)) {
                ++score;
            } else {
                --score;
            }
        }
        return score;
    }

    @NotNull
    @Contract(pure = true)
    static int[] mbxor(final int[] data, final int... key) {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i] ^ key[i % key.length];
        }
        return result;
    }

    @NotNull
    @Contract(pure = true)
    private static char[] byteArrayToCharArray(final int... bytes) {
        char[] result = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (char) bytes[i];
        }
        return result;
    }

    // Consider inlining this function if you call it frequently since it would create a lot of arrays
    @NotNull
    @Contract(pure = true)
    private static char[] byteToHex(final byte b) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] result = new char[2];
        // we need int to use the operators & and >>> here
        final int byteAsInt = b & 0xFF;         // zero out the 3 MSBs
        result[0] = hexArray[byteAsInt >>> 4];  // get the most significant nibble
        result[1] = hexArray[byteAsInt & 0x0F]; // ...and the least significant one
        return result;
    }

    @NotNull
    @Contract(pure = true)
    private static String byteArrayToHexString(final byte[] bytes) {
        final StringBuilder result = new StringBuilder();
        for (final byte b : bytes) {
            result.append(byteToHex(b));
        }
        return result.toString();
    }

    @Contract(pure = true)
    public static String MD5(final String message) {
        String result = null;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(message.getBytes("UTF-8"));
            result = byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Your provider could not supply MD5 as an algorithm");
        } catch (UnsupportedEncodingException e) {
            logger.severe("UTF-8 is not supported on your platform");
        }
        return result;
    }

    @Contract(pure = true)
    public static String HMAC_MD5(final String message, final SecretKey MD5key) {
        String result = null;
        try {
            final Mac mac = Mac.getInstance("HmacMD5");
            mac.init(MD5key);
            final byte[] plainText = message.getBytes("UTF8");
            mac.update(plainText);
            result = byteArrayToHexString(mac.doFinal());
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Your provider could not supply HmacMD5 as an algorithm");
        } catch (InvalidKeyException e) {
            logger.severe("The key generated for this HMAC_MD5 was invalid");
        } catch (UnsupportedEncodingException e) {
            logger.severe("UTF-8 is not supported on your platform");
        }
        return result;
    }
}
