package pl.techbrat.spigot.globalapitb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.Module;
import pl.techbrat.spigot.globalapitb.modules.ModulesManager;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.ServerFunctions;
import pl.techbrat.spigot.globalapitb.modules.textformatter.TextFormatter;

import java.util.stream.Collectors;

public class GlobalAPITBCommand implements CommandExecutor {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (arg.length == 0 || arg[0].equals("help")) {
            mainHelp(sender, s);
        } else if (arg[0].equals("module")) {
            if (arg.length == 1 || arg[1].equals("help")) {
                helpModules(sender, s);
            } else {
                Module module = plugin.getModulesManager().getModule(arg[1]);
                if (module != null) {
                    if (module.getName().equals("global_network")) {
                        GlobalNetwork globalNetwork = (GlobalNetwork) module;
                        if (arg.length >= 3) {
                            switch (arg[2]) {
                                case "reload":
                                    globalNetwork.getCommands().reload(sender);
                                    break;
                                case "info":
                                    globalNetwork.getCommands().info(sender, s);
                                    break;
                                case "test":
                                    globalNetwork.getCommands().test(sender);
                                    break;
                                default:
                                    globalNetwork.getCommands().help(sender, s);
                            }
                        } else {
                            globalNetwork.getCommands().help(sender, s);
                        }
                    }
                    else if (module.getName().equals("server_functions")) {
                        ServerFunctions serverFunctions = (ServerFunctions) module;
                        if (arg.length >= 3) {
                            switch (arg[2]) {
                                case "info":
                                    serverFunctions.getCommands().info(sender);
                                    break;
                                case "storage":
                                    serverFunctions.getCommands().storage(sender);
                                    break;
                                case "reload":
                                    serverFunctions.getCommands().reload(sender);
                                    break;
                                default:
                                    serverFunctions.getCommands().help(sender, s);
                                    break;
                            }
                        } else {
                            serverFunctions.getCommands().help(sender, s);
                        }
                    }
                    else if (module.getName().equals("text_formatter")) {
                        TextFormatter textFormatter = (TextFormatter) module;
                        if (arg.length >= 3) {
                            if (arg[2].equals("info")) {
                                textFormatter.getCommands().info(sender);
                            } else {
                                textFormatter.getCommands().help(sender, s);
                            }
                        } else {
                            textFormatter.getCommands().help(sender, s);
                        }
                    }
                } else if (plugin.getModulesManager().getAllModules().contains(arg[1])) {
                    moduleDisabled(sender, arg[1]);
                } else {
                    listModules(sender);
                }
            }
        } else if (arg[0].equals("reload")) {
            reload(sender);
        }
        return true;
    }

    private void mainHelp(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7All plugin commands:");
        plugin.sendMessage(false, sender, " &3/"+aliasCmd+" module &7- modules commands.");
        plugin.sendMessage(false, sender, " &3/"+aliasCmd+" reload &7- reload plugin.");
        plugin.sendMessage(false, sender, " &3/"+aliasCmd+" help &7- display all plugin commands.");
    }

    private void moduleDisabled(CommandSender sender, String moduleName) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(false, sender, "&cModule &3"+moduleName+" &cisn't enabled!");
        plugin.sendMessage(false, sender, "&7Go to plugin config -> modules, set "+moduleName+": true and reload server or plugin (/gapi reload).");
    }

    private void listModules(CommandSender sender) {
        ModulesManager modulesManager = plugin.getModulesManager();
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7All modules:");
        for (String moduleName : modulesManager.getAllModules()) {
            plugin.sendMessage(false, sender, "  &7- &3"+moduleName+" &7-> "+(modulesManager.isEnabledModule(moduleName)?"&aENABLED":"&8DISABLED"));
        }
    }

    private void helpModules(CommandSender sender, String aliasCmd) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Module's commands:");
        plugin.sendMessage(false, sender, " &3/"+aliasCmd+" module &8<module name> &7- module's commands");
        plugin.sendMessage(false, sender, " &3/"+aliasCmd+" module list &7- list all modules and them status.");
        plugin.sendMessage(false, sender, " &3/"+aliasCmd+" module help &7- display all commands.");
    }

    private void reload(CommandSender sender) {
        plugin.sendMessage(false, sender, "");
        plugin.sendMessage(true, sender, "&7Reloading plugin...");
        plugin.reload();
        plugin.sendMessage(true, sender, "&7Plugin reloaded. To more information check console.");
    }

}
