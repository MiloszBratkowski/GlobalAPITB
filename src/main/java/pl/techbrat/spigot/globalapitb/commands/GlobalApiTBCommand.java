package pl.techbrat.spigot.globalapitb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.ServerReceiver;

public class GlobalApiTBCommand implements CommandExecutor {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (arg[0].equals("help")) {
            //TODO help command
        } else if (arg[0].equals("module")) {
            if (arg[1].equals("help")) {
                //TODO help modules
            } else if (arg[1].equals("list")) {
                //TODO list modules
            } else if (arg[1].equals("global_network")) {
                if (!plugin.isGlobalNetworkEnabled()) {
                    plugin.sendMessage(sender, "Module global_network isn't enabled!");
                    plugin.sendMessage(sender, "Go to plugin config -> modules, set global_network: true and reload plugin.");
                    return true;
                } else if (arg.length >= 3) {
                    if (arg[2].equals("reload")) {
                        plugin.sendMessage(sender, "global_network module's config reloading...");
                        plugin.getGlobalNetwork().reloadConfig();
                        plugin.sendMessage(sender, "global_network module's config reloaded.");
                        return true;
                    } else if (arg[2].equals("info")) {
                        plugin.sendMessage(sender, "Module (global_network) configuration:");
                        plugin.sendMessage(sender, "Server is receiving packets on port: "+plugin.getGlobalNetwork().getConfig().getConfigInt("global_network_server_port"));
                        plugin.sendMessage(sender, "All assigned servers:");
                        for (String server : plugin.getGlobalNetwork().getConfig().getConfigList("server_list")) {
                            plugin.sendMessage(sender, "  - "+server);
                        }
                        plugin.sendMessage(sender, "To check connection use /"+s+" module global_network test.");
                        return true;
                    } else if (arg[2].equals("test")) {
                        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                            plugin.sendMessage(sender, "Pinging all assigned servers in global_network module:");
                            for (String server : ServerReceiver.getRegisteredServers()) {
                                plugin.sendMessage(sender, "  - "+server+" -> "+(plugin.getGlobalNetwork().getSender().ping(ServerReceiver.getServerReceiver(server))?"SUCCESS":"FAIL"));
                            }
                        });
                        return true;
                    }
                }
                plugin.sendMessage(sender, "Module global_network commands:");
                plugin.sendMessage(sender, "/"+s+" module global_network info - display module's configuration");
                plugin.sendMessage(sender, "/"+s+" module global_network reload - reload module's configuration");
                plugin.sendMessage(sender, "/"+s+" module global_network test - test connection with all assigned servers");
                return true;
            }
        }
        return true;
    }
}
