package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerMethods {

    public Player getAnyPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            return player;
        }
        return null;
    }
}
