import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class PasswordChecker {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, URISyntaxException, InterruptedException {
        System.out.println(isPwned("supersecret".toCharArray()));
    }

    // Submits the HASH of the user password and not the password itself
    static boolean isPwned(char[] password) throws NoSuchAlgorithmException, IOException {
        String hash = CryptoUtil.SHA1(new String(password));
        URL url = new URL("https://haveibeenpwned.com/api/v2/pwnedpassword/" + hash.toLowerCase());
        HttpURLConnection myURLConnection = (HttpURLConnection) url.openConnection();
        myURLConnection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        int returnCode = myURLConnection.getResponseCode();
        System.out.println(returnCode);
        System.out.println(myURLConnection.toString());
        return returnCode == 200;
    }
}
