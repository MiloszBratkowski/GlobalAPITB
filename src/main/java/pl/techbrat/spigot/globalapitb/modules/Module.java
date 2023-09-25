package pl.techbrat.spigot.globalapitb.modules;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

public class Module {

    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private ModuleConfig config = null;
    private final String moduleName;

    protected Module(String moduleName) {
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

    protected void reload() {
        config = new ModuleConfig(moduleName);
    }

    protected void close() {
        plugin.getLogger().info("Disabling "+moduleName+" module.");
    }
}
