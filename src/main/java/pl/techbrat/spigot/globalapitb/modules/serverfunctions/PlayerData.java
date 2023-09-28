package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.UUID;

public class PlayerData {
    private String uuid;
    private String nickname;
    private Date first_join;
    private Date last_join;
    private int join_count;
    private int join_time;

    public PlayerData(String uuid, String nickname, Date first_join, Date last_join, int join_count, int join_time) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.first_join = first_join;
        this.last_join = last_join;
        this.join_count = join_count;
        this.join_time = join_time;
    }

    public int addJoinCount() {
        return ++join_count;
    }

    public int getJoinCount() {
        return join_count;
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    }
}
