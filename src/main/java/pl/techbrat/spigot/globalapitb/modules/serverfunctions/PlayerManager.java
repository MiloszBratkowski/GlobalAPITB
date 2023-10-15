package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

public class PlayerManager implements Listener {

    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private final ServerFunctions module;

    private final HashMap<String, PlayerData> playerDataList = new HashMap<>();

    private final boolean use_uuid;

    PlayerManager(ServerFunctions module, boolean use_uuid) {
        this.module = module;
        this.use_uuid = use_uuid;

        for (Player player : Bukkit.getOnlinePlayers()) {
            downloadPlayerData(player);
        }

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public PlayerData getPlayerData(OfflinePlayer player) {
        return getPlayerData(player.getUniqueId().toString(), player.getName());
    }
    public PlayerData getPlayerData(String uuid, String nickname) {
        return playerDataList.get(use_uuid?uuid:nickname);
    }

    public PlayerData registerPlayerData(OfflinePlayer player) {
        return registerPlayerData(player.getUniqueId().toString(), player.getName());
    }
    public PlayerData registerPlayerData(String uuid, String nickname) {
        playerDataList.put((use_uuid?uuid:nickname), new PlayerData(uuid, nickname, new Date(), new Date(), 0, 0));
        return playerDataList.get((use_uuid?uuid:nickname));
    }

    public void unregisterPlayerData(OfflinePlayer player) {
        unregisterPlayerData(player.getUniqueId().toString(), player.getName());
    }

    public void unregisterPlayerData(String uuid, String nickname) {
        if (playerDataList.containsKey((use_uuid?uuid:nickname))) {
            playerDataList.remove((use_uuid?uuid:nickname));
        }
    }

    public void downloadPlayerData(OfflinePlayer player) {
        downloadPlayerData(player.getUniqueId().toString(), player.getName());
    }

    public void downloadPlayerData(String uuid, String nickname) {
        try {
            PlayerData playerData = getPlayerData(uuid, nickname);
            if (playerData == null) {
                playerData = registerPlayerData(uuid, nickname);
            }
            ResultSet resultSet = module.getServerSaver().getStorage().downloadPlayerData(getSQLIdentification(uuid, nickname));

            if (resultSet != null && resultSet.next()) {
                playerData.setPlayerData(
                        resultSet.getString("player_uuid"),
                        resultSet.getString("player_name"),
                        Date.from(Instant.ofEpochSecond(resultSet.getInt("first_join"))),
                        Date.from(Instant.ofEpochSecond(resultSet.getInt("last_join"))),
                        resultSet.getInt("join_count"),
                        resultSet.getInt("join_time")
                );
            } else {
                module.getServerSaver().getStorage().insertPlayerData(new ArrayList<>(Arrays.asList(
                        playerData.getUuid(),
                        playerData.getNickname(),
                        String.valueOf(playerData.getFirstJoin().getTime()/1000),
                        String.valueOf(playerData.getLastJoin().getTime()/1000),
                        String.valueOf(playerData.getJoinCount()),
                        String.valueOf(playerData.getJoinTime()))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerData(OfflinePlayer player) {
        updatePlayerData(player.getUniqueId().toString(), player.getName());
    }

    public void updatePlayerData(String uuid, String nickname) {
        PlayerData playerData = getPlayerData(uuid, nickname);
        module.getServerSaver().getStorage().updatePlayerData(getSQLIdentification(uuid, nickname), new ArrayList<>(Arrays.asList(
                playerData.getUuid(),
                playerData.getNickname(),
                String.valueOf(playerData.getFirstJoin().getTime()/1000),
                String.valueOf(playerData.getLastJoin().getTime()/1000),
                String.valueOf(playerData.getJoinCount()),
                String.valueOf(playerData.getJoinTime()))
        ));
    }

    public void deletePlayerData(OfflinePlayer player) {
        deletePlayerData(player.getUniqueId().toString(), player.getName());
    }

    public void deletePlayerData(String uuid, String nickname) {
        unregisterPlayerData(uuid, nickname);
        module.getServerSaver().getStorage().deletePlayerData(new ArrayList<>(Arrays.asList(uuid, nickname)));
    }

    public String getSQLIdentification(String uuid, String nickname) {
        return (use_uuid?"player_uuid = '"+uuid+"'":"player_name = '"+nickname+"'");
    }

    public void close() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerDataList.containsKey((use_uuid?player.getUniqueId().toString():player.getName()))) {
                updatePlayerData(player);
            }
        }
        for (String playerId : new ArrayList<>(playerDataList.keySet())){
            unregisterPlayerData(use_uuid?playerId:null, use_uuid?null:playerId);
        }
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        downloadPlayerData(event.getPlayer());

        PlayerData playerData = getPlayerData(event.getPlayer());
        playerData.setLastJoin();
        playerData.addJoinCount(1);
        updatePlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        updatePlayerData(event.getPlayer());
    }
}
