package pl.techbrat.spigot.globalapitb.modules.textformatter;

import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

public class TextFormatterCommands {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    public void help(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Text Formatter &7commands:");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module text_formatter info &7- display installed module's submodules.");
        plugin.sendMessage(false, sender, "&3/"+aliasCmd+" module text_formatter help &7- display all module commands.");
    }

    public void info(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module &3Text Formatter&7's submodules: &8(v"+TextFormatter.getVersion()+")");
        plugin.sendMessage(false, sender, "  &7- &3ColorFormatter &8(v"+ColorFormatter.getVersion()+")");
        plugin.sendMessage(false, sender, "  &7- &3InteractiveMessage &8(v"+InteractiveMessage.getVersion()+")");
    }
}
