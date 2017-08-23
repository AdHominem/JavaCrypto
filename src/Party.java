import java.util.ArrayList;
import java.util.List;

public class Party {
    List<Message> messageBuffer;
    private String name;

    Party(String name) {
        messageBuffer = new ArrayList<>();
        this.name = name;
    }

    void send(Message message, Party recipient) {
        recipient.receive(message);
    }

    void send(String message, Party recipient) {
        recipient.receive(new Message(this, recipient, message));
    }

    private void receive(Message message) {
        messageBuffer.add(message);
    }

    @Override
    public String toString() {
        return name;
    }
}
