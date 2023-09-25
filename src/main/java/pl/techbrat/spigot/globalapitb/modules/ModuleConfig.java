package pl.techbrat.spigot.globalapitb.modules;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ModuleConfig {
    private static final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private static final File modulesDirectory = new File(plugin.getDataFolder()+"/modules");
    private File moduleConfigFile;

    private String moduleName;

    private final HashMap<String, Object> defaultConfig;
    private final HashMap<String, Object> config;

    public ModuleConfig(String moduleName) {
        if (!modulesDirectory.exists() || !modulesDirectory.isDirectory()) {
            modulesDirectory.mkdir();
        }

        this.moduleName = moduleName;

        createConfig(false);

        plugin.debug("Loading "+moduleName+" module's config file...");
        moduleConfigFile = new File(modulesDirectory+"/"+moduleName+".yml");
        config = (HashMap<String, Object>) YamlConfiguration.loadConfiguration(moduleConfigFile).getConfigurationSection("").getValues(true);
        File temp = new File(plugin.getDataFolder()+"/"+moduleName+".temp");
        try {
            Files.copy(plugin.getResource("modules/"+moduleName+".yml"), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        defaultConfig = (HashMap<String, Object>)YamlConfiguration.loadConfiguration(temp).getConfigurationSection("").getValues(true);
        temp.delete();
        plugin.debug(moduleName+" module's config file loaded.");

    }

    private void createConfig(boolean forceCopy) {
        moduleConfigFile = new File(modulesDirectory+"/"+moduleName+".yml");
        if (!moduleConfigFile.isFile() || forceCopy) {
            try {
                plugin.debug("Creating "+moduleName+" module's config file...");
                Files.copy(plugin.getResource("modules/"+moduleName+".yml"), moduleConfigFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                plugin.debug("File "+moduleName+".yml created.");
            } catch (Exception e) {
                e.printStackTrace(); plugin.stopPlugin();
            }
        }
    }

    private Object getReliabilityConfig(String value) {
        if (config.containsKey(value)) return config.get(value);
        else {
            plugin.getLogger().warning("");
            plugin.getLogger().warning("Can't find '"+value+"' in modules/"+moduleName+".yml!");
            plugin.getLogger().warning("Default value has been got! ("+defaultConfig.get(value)+")");
            plugin.getLogger().warning("To set that option, config file must be recreated!");
            plugin.getLogger().warning("Paste manually new structures or delete modules"+moduleName+".yml to auto recreate!");
            plugin.getLogger().warning("");
            return defaultConfig.get(value);
        }
    }

    public String getConfigString(String path) {
        return (String) getReliabilityConfig(path);
    }

    public Integer getConfigInt(String path) {
        return (Integer) getReliabilityConfig(path);
    }

    public Double getConfigDouble(String path) {
        return (Double) getReliabilityConfig(path);
    }

    public Float getConfigFloat(String path) {
        return (Float) getReliabilityConfig(path);
    }

    public List<String> getConfigList(String path) {
        return (List<String>) getReliabilityConfig(path);
    }

    public Object getConfigObject(String path) {
        return getReliabilityConfig(path);
    }
}
