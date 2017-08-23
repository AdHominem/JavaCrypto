import org.jetbrains.annotations.NotNull;

public class Message {
    private final Party sender;
    private final Party recipient;
    private final String content;

    Message(final Party sender, final Party recipient, final String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    @NotNull
    @Override
    public String toString() {
        return sender + " -> " + recipient + ": " + content;
    }
}
