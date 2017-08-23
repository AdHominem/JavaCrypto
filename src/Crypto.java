import java.security.InvalidParameterException;
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
}
