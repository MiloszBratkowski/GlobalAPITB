package pl.techbrat.spigot.globalapitb.globalnetwork;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.Module;
import pl.techbrat.spigot.globalapitb.ModuleConfig;

public class GlobalNetwork extends Module {
    private GlobalAPITB plugin;

    private final ReceiverModule receiver;
    private final SenderModule sender;

    public GlobalNetwork() {
        super("global_network");
        this.plugin = GlobalAPITB.getPlugin();

        reloadConfig();

        plugin.getLogger().info("Enabling global_network module.");

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
