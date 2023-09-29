package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class PlayerData {
    private String uuid;
    private String nickname;
    private Date first_join;
    private Date last_join;
    private int join_count;
    private long join_time;

    public PlayerData(String uuid, String nickname, Date first_join, Date last_join, int join_count, long join_time) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.first_join = first_join;
        this.last_join = last_join;
        this.join_count = join_count;
        this.join_time = join_time;
    }

    public void downloadPlayerData() {
        this.uuid = uuid;
        this.nickname = nickname;
        this.first_join = first_join;
        this.last_join = last_join;
        this.join_count = join_count;
        this.join_time = join_time;
    }

    public @Nullable OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    }

    public boolean isOnline() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        return offlinePlayer != null && offlinePlayer.isOnline();
    }

    public int addJoinCount() {
        return ++join_count;
    }

    public int getJoinCount() {
        return join_count;
    }

    public Date getFirstJoin() {
        return first_join;
    }

    public Date getLastJoin() {
        return last_join;
    }

    public long getJoinTime() {
        if (isOnline()) return join_time+last_join.getTime()/1000;
        return join_time;
    }
}
