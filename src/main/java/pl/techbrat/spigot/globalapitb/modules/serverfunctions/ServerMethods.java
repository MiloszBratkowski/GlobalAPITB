package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerMethods {

    ServerFunctions module;

    ServerMethods(ServerFunctions module) {
        this.module = module;
    }

    public Player getAnyPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            return player;
        }
        return null;
    }


    private final static String VERSION = "1.0";

    public static String getVersion() {
        return VERSION;
    }
}
