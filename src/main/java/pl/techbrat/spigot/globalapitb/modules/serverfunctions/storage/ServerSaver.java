package pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage;

import com.sun.istack.internal.Nullable;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.ModuleConfig;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage.MySQLDatabase;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage.SQLiteDatabase;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage.Storage;

import java.sql.SQLException;

public class ServerSaver {

    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private Storage storage;

    public ServerSaver(ModuleConfig config) {
        String type = config.getConfigString("storage_type");
        if (type.equalsIgnoreCase("SQLITE")) {
            storage = new SQLiteDatabase(
                    config.getConfigString("storage.sqlite.filename"),
                    config.getConfigString("table_prefix"),
                    config.getConfigString("table_suffix")
            );
        } else if (type.equalsIgnoreCase("MYSQL")) {
            storage = new MySQLDatabase(
                    config.getConfigString("storage.mysql.host"),
                    config.getConfigInt("storage.mysql.port"),
                    config.getConfigBoolean("storage.mysql.ssl"),
                    config.getConfigString("storage.mysql.user"),
                    config.getConfigString("storage.mysql.password"),
                    config.getConfigString("storage.mysql.database"),
                    config.getConfigString("table_prefix"),
                    config.getConfigString("table_suffix")
            );
        } else {
            plugin.getLogger().severe("Wrong storage type - "+type+"! Change type to sqlite or mysql and reload server or plugin (/gapi reload).");
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                plugin.getModulesManager().close(plugin.getModulesManager().getModule("server_functions"));
            }, 5);
            return;
        }

        storage.connect();
        storage.createTable(null);


    }

    public void close() {
        if (storage != null) {
            storage.disconnect();
        }
    }

    @Nullable
    public Storage getStorage() {
        return storage;
    }
}