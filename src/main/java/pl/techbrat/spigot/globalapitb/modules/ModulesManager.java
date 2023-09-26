package pl.techbrat.spigot.globalapitb.modules;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import pl.techbrat.spigot.globalapitb.ConfigData;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.ServerFunctions;
import pl.techbrat.spigot.globalapitb.modules.textformatter.TextFormatter;

import java.util.*;
import java.util.stream.Collectors;

public class ModulesManager {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private final HashMap<String, Module> modules = new HashMap<>();

    private final List<String> allModules = new ArrayList<>(Arrays.asList("global_network", "text_formatter", "server_functions"));

    public ModulesManager() {
        ConfigData config = plugin.getConfiguration();
        plugin.getLogger().info("Enabling modules...");
        if (config.getModule("global_network")) modules.put("global_network", new GlobalNetwork());
        if (config.getModule("text_formatter")) modules.put("text_formatter", new TextFormatter());
        if (config.getModule("server_functions")) modules.put("server_functions", new ServerFunctions());
    }

    public void closeAll() {
        for (Module module : modules.values()) {
            module.close();
        }
        modules.clear();
    }

    public void close(Module module) {
        module.close();
        modules.remove(module.getName());
    }

    public boolean isEnabledModule(Module module) {
        return (modules.containsValue(module));
    }

    public boolean isEnabledModule(String name) {
        return modules.containsKey(name);
    }

    public Module getModule(String name) {
        return modules.getOrDefault(name, null);
    }

    public List<String> getAllModules() {
        return allModules;
    }
}
