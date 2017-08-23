import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        byte[] cipher = Crypto.xor("Hello my 3 mates!".getBytes(), 13);
        Crypto.crackXor(cipher).forEach((x,y) -> System.out.printf("%s:\t%d\n", x, y));

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
