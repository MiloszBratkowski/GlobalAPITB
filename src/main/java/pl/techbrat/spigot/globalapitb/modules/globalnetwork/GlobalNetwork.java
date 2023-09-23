package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.Module;

public class GlobalNetwork extends Module {
    private GlobalAPITB plugin;

    private final ReceiverModule receiver;
    private final SenderModule sender;

    public GlobalNetwork() {
        super("global_network");
        this.plugin = GlobalAPITB.getPlugin();

        reloadConfig();

        plugin.getLogger().info("Enabling global_network module.");

        receiver = new ReceiverModule(getConfig().getConfigInt("global_network_server_port"));
        sender = new SenderModule();
        loadServerReceivers();

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

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        loadServerReceivers();
    }
}
