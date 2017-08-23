import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private final List<Message> messageBuffer;
    private final String name;

    Party(final String name) {
        messageBuffer = new ArrayList<>();
        this.name = name;
    }

    void send(final Message message, final Party recipient) {
        recipient.receive(message);
    }

    void send(final String message, final Party recipient) {
        recipient.receive(new Message(this, recipient, message));
    }

    private void receive(final Message message) {
        messageBuffer.add(message);
    }

    @Override
    public String toString() {
        return name;
    }
}
