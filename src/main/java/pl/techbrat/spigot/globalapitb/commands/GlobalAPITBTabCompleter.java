package pl.techbrat.spigot.globalapitb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GlobalAPITBTabCompleter implements TabCompleter {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] arg) {
        final ArrayList<String> completions = new ArrayList<>();
        if (arg.length == 1) {
            StringUtil.copyPartialMatches(arg[0], Arrays.asList("module", "reload", "help"), completions);
        }
        else if (arg.length == 2) {
            if (arg[0].equals("module")) {
                StringUtil.copyPartialMatches(arg[1], Stream.concat(plugin.getModulesManager().getAllModules().stream(), Arrays.asList("list", "help").stream()).collect(Collectors.toList()), completions);
            }
        } else if (arg.length == 3) {
            if (arg[0].equals("module")) {
                if (arg[1].equals("global_network")) {
                    StringUtil.copyPartialMatches(arg[2], Arrays.asList("info", "test", "reload", "help"), completions);
                } else if (arg[1].equals("text_formatter")) {
                    StringUtil.copyPartialMatches(arg[2], Arrays.asList("info", "help"), completions);
                } else if (arg[1].equals("server_functions")) {
                    StringUtil.copyPartialMatches(arg[2], Arrays.asList("info", "storage", "reload", "help"), completions);
                }
            }
        }
        return completions;
    }
}
