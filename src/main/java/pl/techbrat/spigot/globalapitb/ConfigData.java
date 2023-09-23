package pl.techbrat.spigot.globalapitb;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.logging.Level;

public class ConfigData {
    private static final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private static ConfigData instance;
    public static ConfigData getInstance() {
        return instance;
    }

    private final HashMap<String, Object> defaultConfig;
    private final HashMap<String, Object> config;

    public ConfigData() {
        instance = this;

        plugin.getDataFolder().mkdir();

        createConfig(false);

        plugin.getLogger().log(Level.INFO, "Loading config file...");
        File configFile = new File(plugin.getDataFolder()+"/config.yml");
        config = (HashMap<String, Object>) YamlConfiguration.loadConfiguration(configFile).getConfigurationSection("").getValues(true);
        File temp = new File(plugin.getDataFolder()+"/config.temp");
        try {
            Files.copy(plugin.getResource("config.yml"), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        defaultConfig = (HashMap<String, Object>)YamlConfiguration.loadConfiguration(temp).getConfigurationSection("").getValues(true);
        temp.delete();
        plugin.getLogger().log(Level.INFO, "Config file loaded.");
    }

    private Object getReliabilityConfig(String value) {
        if (config.containsKey(value)) return config.get(value);
        else {
            plugin.getLogger().warning("");
            plugin.getLogger().warning("Can't find '"+value+"' in config.yml!");
            plugin.getLogger().warning("Default value has been got! ("+defaultConfig.get(value)+")");
            plugin.getLogger().warning("To set that option, config file must be recreated!");
            plugin.getLogger().warning("Paste manually new structures or delete config.yml to auto recreate!");
            plugin.getLogger().warning("");
            return defaultConfig.get(value);
        }
    }

    private void createConfig(boolean forceCopy) {
        File file = new File(plugin.getDataFolder()+"/config.yml");
        if (!file.isFile() || forceCopy) {
            try {
                plugin.getLogger().log(Level.INFO, "Creating config.yml ...");
                Files.copy(plugin.getResource("config.yml"), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                plugin.getLogger().log(Level.INFO, "File config.yml created");
            } catch (Exception e) {
                e.printStackTrace(); plugin.stopPlugin();
            }
        }
    }

    public boolean getModule(String module) {
        return (boolean) getReliabilityConfig("modules."+module);
    }

    public boolean isDebugEnabled() {
        return (boolean) getReliabilityConfig("debug");
    }
}
