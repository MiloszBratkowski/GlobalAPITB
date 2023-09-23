package pl.techbrat.spigot.globalapitb;

import org.bukkit.plugin.java.JavaPlugin;
import pl.techbrat.spigot.globalapitb.globalnetwork.GlobalNetwork;

public final class GlobalAPITB extends JavaPlugin {

    private static GlobalAPITB plugin;

    private static GlobalNetwork globalNetwork;

    private ConfigData config;

    @Override
    public void onEnable() {
        plugin = this;

        config = new ConfigData();

        System.out.println("Enabling modules...");

        if (config.getModule("global_network")) globalNetwork = new GlobalNetwork();
    }

    @Override
    public void onDisable() {
        if (isGlobalNetworkEnabled()) globalNetwork.close();
    }


    public boolean isGlobalNetworkEnabled() {
        return globalNetwork != null;
    }

    public GlobalNetwork getGlobalNetwork() {
        return globalNetwork;
    }


    public ConfigData getConfiguration() {
        return config;
    }

    public void debug(String log) {
        if (ConfigData.getInstance().isDebugEnabled()) getPlugin().getLogger().info(log);
    }

    public void stopPlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }


    public static GlobalAPITB getPlugin() {
        return plugin;
    }
}