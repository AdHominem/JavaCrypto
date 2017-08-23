public class Message {
    private Party sender;
    private Party recipient;
    private String content;

    Message(Party sender, Party recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    @Override
    public String toString() {
        return sender + " -> " + recipient + ": " + content;
    }
}
