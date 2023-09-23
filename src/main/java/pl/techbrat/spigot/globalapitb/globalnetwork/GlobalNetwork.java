package pl.techbrat.spigot.globalapitb.globalnetwork;

public class NetworkInitializer {
    private ReceiverModule receiver;
    private SenderModule sender;
    public NetworkInitializer() {
        receiver = new ReceiverModule(30000);
        sender = new SenderModule(30000, "localhost");
    }

    public void close() {
        receiver.close();
        sender.close();
    }

    public ReceiverModule getReceiver() {
        return receiver;
    }

    public SenderModule getSender() {
        return sender;
    }
}
