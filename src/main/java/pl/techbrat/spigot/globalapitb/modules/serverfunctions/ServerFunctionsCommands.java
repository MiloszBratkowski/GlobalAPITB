package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

public class ServerFunctionsCommands {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    public void help(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Server Functions &7commands:");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module server_functions info &7- display installed module's submodules.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module server_functions help &7- display all module commands.");
    }

    public void info(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Server Functions&7's submodules:");
        plugin.sendMessage(false, sender, "  &7- &3ServerMethods");
        plugin.sendMessage(false, sender, "  &7- &3BasicMethods");
    }
}
