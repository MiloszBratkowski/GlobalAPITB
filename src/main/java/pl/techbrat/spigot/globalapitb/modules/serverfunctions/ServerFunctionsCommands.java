package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;

public class ServerFunctionsCommands {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private final ServerFunctions serverFunctions;

    protected ServerFunctionsCommands(ServerFunctions serverFunctions) {
        this.serverFunctions = serverFunctions;
    }

    public void help(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Server Functions &7commands:");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module server_functions info &7- display installed module's submodules.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module server_functions storage &7- display storage configuration.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module server_functions reload &7- reload module's configuration.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module server_functions help &7- display all module commands.");
    }

    public void info(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Server Functions&7's submodules:");
        plugin.sendMessage(false, sender, "  &7- &3BasicMethods");
        plugin.sendMessage(false, sender, "  &7- &3ServerMethods");
        plugin.sendMessage(false, sender, "  &7- &3ServerSaver (storage)");
    }

    public void reload(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&3Server Functions &7module's config reloading...");
        serverFunctions.reload();
        plugin.sendMessage(true, sender, "&3Server Functions &7module's config reloaded.");
    }

    public void storage(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Server Functions&7's storage:");
        plugin.sendMessage(false, sender, "&7type: &3"+serverFunctions.getServerSaver().getStorage().getType());
        plugin.sendMessage(false, sender, "&7url driver: &3"+serverFunctions.getServerSaver().getStorage().getUrl());
        plugin.sendMessage(false, sender, "&7table prefix: &3"+serverFunctions.getServerSaver().getStorage().getTable_prefix());
        plugin.sendMessage(false, sender, "&7table suffix: &3"+serverFunctions.getServerSaver().getStorage().getTable_suffix());
    }


}
