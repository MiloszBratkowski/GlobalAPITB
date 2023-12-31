package pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage;

import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Arrays;

public class MySQLDatabase extends Storage {

    private final String host;
    private final Integer port;
    private final Boolean ssl;
    private final String user;
    private final String password;
    private final String database;

    public MySQLDatabase(String host, Integer port, Boolean ssl, String user, String password, String database, String table_prefix, String table_suffix) {
        super("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://"+host+":"+port+"/"+database+"?autoReconnect=true&useSSL="+ssl, Arrays.asList(user, password), table_prefix, table_suffix);
        this.host = host;
        this.port = port;
        this.ssl = ssl;
        this.user = user;
        this.password = password;
        this.database = database;
    }


    @Override
    protected void createTable(@Nullable InputStream resource) {
        super.createTable(plugin.getResource("storage/mysql_players_create.sql"));
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }
}
