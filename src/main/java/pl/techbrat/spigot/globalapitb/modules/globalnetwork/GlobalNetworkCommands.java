package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

public class GlobalNetworkCommands {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();
    private final GlobalNetwork globalNetwork;

    protected GlobalNetworkCommands(GlobalNetwork globalNetwork) {
        this.globalNetwork = globalNetwork;
    }
    public void info(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Global Network &7configuration:");
        plugin.sendMessage(false, sender, "&7Server is receiving packets on port: &3"+globalNetwork.getConfig().getConfigInt("global_network_server_port"));
        plugin.sendMessage(false, sender, "&7All assigned servers:");
        for (String server : globalNetwork.getConfig().getConfigList("server_list")) {
            plugin.sendMessage(false, sender, "  &7- &3"+server);
        }
        plugin.sendMessage(false, sender, "&7To check connection use &3/"+aliasCmd+" module global_network test&7.");
    }

    public void reload(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&3Global Network &7module's config reloading...");
        globalNetwork.reload();
        plugin.sendMessage(true, sender, "&3Global Network &7module's config reloaded.");
    }

    public void test(CommandSender sender) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.sendMessage(false, sender, "");
            plugin.sendMessage(true, sender, "&7Pinging all assigned servers in &3Global Network &7module:");
            int allServers = ServerReceiver.getRegisteredServers().size();
            for (String server : ServerReceiver.getRegisteredServers()) {
                boolean status = globalNetwork.getSender().ping(ServerReceiver.getServerReceiver(server));
                if (!status) allServers--;
                plugin.sendMessage(false, sender, "  &7- &3"+server+" &7-> "+(status?"&aSUCCESS":"&cFAIL"));
            }
            plugin.sendMessage(false, sender, "&a"+allServers+"&7/&3"+ServerReceiver.getRegisteredServers().size()+" &7servers are available.");
        });
    }

    public void help(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Global Network &7commands:");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module global_network info &7- display module's configuration.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module global_network test &7- test connection with all assigned servers.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module global_network reload &7- reload module's configuration.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module global_network help &7- display all module commands.");
    }
}
