package pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage;

import org.bukkit.Bukkit;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private String type;
    private String className;
    private String url;
    private List<String> params;
    private String table_prefix;
    private String table_suffix;

    private Connection connection;
    private Statement statement;

    Storage(String type, String className, String url, List<String> params, String table_prefix, String table_suffix) {
        this.type = type;
        this.className = className;
        this.url = url;
        this.params = params;
        this.table_prefix = table_prefix;
        this.table_suffix = table_suffix;
    }

    void connect() {
        try {
            if(connection != null && !connection.isClosed()) return;
            Class.forName(className);
            Connection connection = DriverManager.getConnection(url, params.get(0), params.get(1));
            this.connection = connection;
            statement = connection.createStatement();
        } catch (SQLException e) {
            plugin.getLogger().severe("Database connection error, maybe was disconnected or typed wrong parameters.");
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                plugin.getModulesManager().close(plugin.getModulesManager().getModule("server_functions"));
            }, 5);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void disconnect() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Database disconnection error, maybe was disconnected.");
        }

    }

    public ResultSet query(String query) {
        try {
            if (connection == null || statement == null || connection.isClosed() || statement.isClosed()) connect();
            return statement.executeQuery(query.replaceAll("%prefix%", table_prefix).replaceAll("%suffix%", table_suffix));
        } catch (SQLException e) {
            plugin.getLogger().severe("Database executing query error.");
            e.printStackTrace();
            return null;
        }
    }

    public void update(String query) {
        try {
            if (connection == null || statement == null || connection.isClosed() || statement.isClosed()) connect();
            statement.executeUpdate(query.replaceAll("%prefix%", table_prefix).replaceAll("%suffix%", table_suffix));
        } catch (SQLException e) {
            plugin.getLogger().severe("Database executing query error.");
            e.printStackTrace();
        }
    }

    protected void createTable(InputStream resource) {
        try {
            if (connection != null && statement != null && !connection.isClosed() && !statement.isClosed()) {
                File file = new File(plugin.getDataFolder() + "/modules/table.temp");
                Files.copy(resource, file.toPath());
                String query = String.join(" ", Files.readAllLines(file.toPath()));
                file.delete();
                update(query);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Copying table creating sql query error.");
            e.printStackTrace();
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                plugin.getModulesManager().close(plugin.getModulesManager().getModule("server_functions"));
            }, 5);
        } catch (SQLException e) {
            plugin.getLogger().severe("Database isn't connected!");
            e.printStackTrace();
        }
    }

    public ResultSet downloadPlayerData(String identification) {
        return query("SELECT * FROM %prefix%all_players%suffix% WHERE "+identification+";");
    }

    public void insertPlayerData(ArrayList<String> values) {
        update("INSERT INTO %prefix%all_players%suffix% VALUES (NULL, '"+values.get(0)+"', '"+values.get(1)+"', "+values.get(2)+", "+values.get(3)+", "+values.get(4)+", "+values.get(5)+");");
    }

    public void updatePlayerData(String identification, ArrayList<String> values) {
        update("UPDATE %prefix%all_players%suffix% SET "+
                "player_uuid = '"+values.get(0)+"', "+
                "player_name = '"+values.get(1)+"', "+
                "first_join = "+values.get(2)+", "+
                "last_join = "+values.get(3)+", "+
                "join_count = "+values.get(4)+", "+
                "join_time = "+values.get(5)+
                " WHERE "+identification+";");
    }

    public void deletePlayerData(ArrayList<String> values) {
        update("DELETE FROM %prefix%all_players%suffix% WHERE player_uuid = '"+values.get(0)+"' AND player_name = '"+values.get(1)+"';");
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getTable_prefix() {
        return table_prefix;
    }

    public String getTable_suffix() {
        return table_suffix;
    }
}
