package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import pl.techbrat.spigot.globalapitb.modules.Module;

public class GlobalNetwork extends Module {

    private ReceiverModule receiver;
    private SenderModule sender;

    private final GlobalNetworkCommands commands;

    public GlobalNetwork() {
        super("global_network");

        commands = new GlobalNetworkCommands(this);
    }

    private void loadServerReceivers() {
        for (String serverdata : getConfig().getConfigList("server_list")) {
            new ServerReceiver(
                    serverdata.split("@")[0],
                    serverdata.split("@")[1].split(":")[0],
                    Integer.parseInt(serverdata.split("@")[1].split(":")[1])
            ).register();
        }
    }

    public ReceiverModule getReceiver() {
        return receiver;
    }

    public SenderModule getSender() {
        return sender;
    }

    public GlobalNetworkCommands getCommands() {
        return commands;
    }

    @Override
    public void reload() {
        super.reload();
        if (receiver != null) receiver.close();
        if (sender != null) sender.close();
        ServerReceiver.unregisterAll();
        receiver = new ReceiverModule(getConfig().getConfigInt("global_network_server_port"));
        sender = new SenderModule();
        loadServerReceivers();
    }

    @Override
    public void close() {
        super.close();
        receiver.close();
        sender.close();
        ServerReceiver.unregisterAll();
    }
}
