package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class PlayerManager {

    private ServerFunctions module;

    private final HashMap<String, PlayerData> playerDataList = new HashMap<>();

    private boolean use_uuid;

    PlayerManager(ServerFunctions module, boolean use_uuid) {
        this.module = module;
        this.use_uuid = use_uuid;
    }

    public PlayerData getPlayerData(OfflinePlayer player) {
        return getPlayerData(use_uuid?player.getUniqueId().toString():player.getName());
    }
    public PlayerData getPlayerData(String identification) {
        return playerDataList.get(identification);
    }

    public void registerPlayerData(OfflinePlayer player) {
        registerPlayerData(player.getUniqueId().toString(), player.getName());
    }
    public void registerPlayerData(String uuid, String nickname) {
        playerDataList.put((use_uuid?uuid:nickname), new PlayerData(uuid, nickname, new Date(), new Date(), 0, 0));
    }

    public void updatePlayerData(OfflinePlayer player) {
        registerPlayerData(player.getUniqueId().toString(), player.getName());
    }
    public void updatePlayerData(String uuid, String nickname) {
        ResultSet resultSet = module.getServerSaver().getStorage().query("SELECT * FROM %prefix%all_players%suffix% WHERE "+(use_uuid?"player_uuid = '"+uuid+"'":"player_name = '"+nickname+"'")+";");
        try {
            if (resultSet.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
