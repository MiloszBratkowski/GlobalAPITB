package pl.techbrat.spigot.globalapitb.modules;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

public class Module {

    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private ModuleConfig config = null;
    private final String moduleName;

    public Module(String moduleName) {
        plugin.getLogger().info("Enabling "+moduleName+" module.");
        this.moduleName = moduleName;
        reload();
    }

    public ModuleConfig getConfig() {
        return config;
    }

    public String getName() {
        return moduleName;
    }

    public void reload() {
        config = new ModuleConfig(moduleName);
    }

    public void close() {
        plugin.getLogger().info("Disabling "+moduleName+" module.");
    }
}
