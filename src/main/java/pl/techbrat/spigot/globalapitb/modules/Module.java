package pl.techbrat.spigot.globalapitb.modules;

public class Module {

    private ModuleConfig config = null;
    private final String moduleName;

    public Module(String moduleName) {
        this.moduleName = moduleName;
        reloadConfig();
    }

    public ModuleConfig getConfig() {
        return config;
    }

    public void reloadConfig() {
        config = new ModuleConfig(moduleName);
    }
}
