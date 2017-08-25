import javax.crypto.*;
import java.util.Arrays;

public class Main {

    public static void main(final String[] args) throws Exception {

        SecretKey key = CryptoUtil.DESgetKey();
        String message = "Hello";
        byte[] ciphertext = CryptoUtil.DESencrypt(message, key);
        System.out.println(Arrays.toString(ciphertext));

        System.out.println(Arrays.toString(CryptoUtil.DESdecrypt(ciphertext, key)));

//        System.out.println(CryptoUtil.MD5("This is a test!"));
//
//        final KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
//        final SecretKey MD5key = keyGen.generateKey();
//
//        System.out.println(CryptoUtil.HMAC_MD5("This is a test!", MD5key));

//        int[] cipher = CryptoUtil.xor("Hello my 3 mates!", 1);
//        System.out.println(Arrays.toString(cipher));
//        CryptoUtil.crackXor(cipher).forEach((x,y) -> System.out.printf("%s:\t%d\n", x, y));

//        DHParty client = new DHParty("Client", 13, 2);
//        Thread.sleep(3000);
//        DHParty server = new DHParty("Server", 13, 2);
//
//        client.send(String.valueOf(client.getShare()), server);
//        server.send(String.valueOf(client.getShare()), client);
//        server.messageBuffer.forEach(System.out::println);
//        client.messageBuffer.forEach(System.out::println);
    }
}
