import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crypto {

    static byte[] xor(byte[] data, int key) {
        if (key > 255) {
            throw new InvalidParameterException("Key is too large!");
        }
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = xor(data[i], key);
        }
        return result;
    }

    static byte[] xor(byte[] data, byte key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = xor(data[i], key);
        }
        return result;
    }

    static Map<String, Integer> crackXor(byte[] cipher) {
        Map<String, Integer> result = new HashMap<>();
        for (byte key = 0; key != (byte) 255; key++) {
            byte[] plain = xor(cipher, key);
            int score = getScore(plain);
            if (score > 10) {
                result.put(String.valueOf(byteArrayToCharArray(plain)), score);
            }
        }
        return result;
    }

    private static int getScore(byte[] plaintext) {
        int score = 0;
        for (byte b : plaintext) {
            if (Character.isLetterOrDigit(b) || Character.isWhitespace(b)) {
                ++score;
            } else {
                --score;
            }
        }
        return score;
    }

    private static byte xor(int a, int b) {
        if (a > 255 || b > 255) {
            throw new InvalidParameterException("Parameters too large!");
        }
        return (byte) (a ^ b);
    }

    private static byte xor(byte a, byte b) {
        return (byte) (a ^ b);
    }

    static byte[] mbxor(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = xor(data[i], key[i % key.length]);
        }
        return result;
    }

    private static char[] byteArrayToCharArray(byte[] bytes) {
        char[] result = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (char) bytes[i];
        }
        return result;
    }

    private static List<Byte> byteArrayToByteList(byte[] bytes) {
        List<Byte> result = new ArrayList<>();
        for (byte _byte : bytes) {
            result.add(_byte);
        }
        return result;
    }

    private static byte[] byteListToByteArray(List<Byte> list) {
        byte[] result = new byte[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    // Consider inlining this function if you call it frequently since it would create a lot of arrays
    private static char[] byteToHex(byte b) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] result = new char[2];
        // we need int to use the operators & and >>> here
        final int byteAsInt = b & 0xFF;         // zero out the 3 MSBs
        result[0] = hexArray[byteAsInt >>> 4];  // get the most significant nibble
        result[1] = hexArray[byteAsInt & 0x0F]; // ...and the least significant one
        return result;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(byteToHex(b));
        }
        return result.toString();
    }

    public static String MD5(String message) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(message.getBytes());
            result = byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Your provider could not supply MD5 as an algorithm");
        }
        return result;
    }
}
