package pl.techbrat.spigot.globalapitb;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.techbrat.spigot.globalapitb.api.GlobalAPITBManager;
import pl.techbrat.spigot.globalapitb.commands.GlobalAPITBCommand;
import pl.techbrat.spigot.globalapitb.commands.GlobalAPITBTabCompleter;
import pl.techbrat.spigot.globalapitb.modules.ModulesManager;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.DataPacket;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.ServerReceiver;

public final class GlobalAPITB extends JavaPlugin {

    private static GlobalAPITB plugin;

    private ModulesManager modulesManager;
    private ConfigData config;

    private static GlobalAPITBManager globalAPITBManager;

    @Override
    public void onEnable() {
        plugin = this;

        config = new ConfigData();

        modulesManager = new ModulesManager();

        getCommand("globalapitb").setExecutor(new GlobalAPITBCommand());
        getCommand("globalapitb").setTabCompleter(new GlobalAPITBTabCompleter());

        globalAPITBManager = new GlobalAPITBManager();



    }

    @Override
    public void onDisable() {
        modulesManager.closeAll();
    }

    public void reload() {
        debug("Starting reloading...");
        modulesManager.closeAll();
        config = new ConfigData();
        modulesManager = new ModulesManager();
        globalAPITBManager = new GlobalAPITBManager();
    }

    public ConfigData getConfiguration() {
        return config;
    }

    public ModulesManager getModulesManager() {
        return modulesManager;
    }

    public void debug(String log) {
        if (ConfigData.getInstance().isDebugEnabled()) getPlugin().getLogger().info("[DEBUG] "+log);
    }
    public void sendMessage(boolean prefix, CommandSender receiver, String msg) {
        if (prefix) msg = "&7[&3GlobalAPI&bTB&7] "+msg;
        receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void stopPlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }

    public static GlobalAPITBManager getAPI() {
        return globalAPITBManager;
    }

    public static GlobalAPITB getPlugin() {
        return plugin;
    }

}