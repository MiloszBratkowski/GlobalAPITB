package pl.techbrat.spigot.globalapitb.modules;

import org.bukkit.command.CommandSender;
import pl.techbrat.spigot.globalapitb.ConfigData;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;

import java.util.*;

public class ModulesManager {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private final HashMap<String, Module> modules = new HashMap<>();

    private final List<String> allModules = new ArrayList<>(Collections.singletonList("global_network"));

    public ModulesManager() {
        ConfigData config = plugin.getConfiguration();
        plugin.getLogger().info("Enabling modules...");
        if (config.getModule("global_network")) modules.put("global_network", new GlobalNetwork());
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
