package pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage;

import com.sun.istack.internal.Nullable;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SQLiteDatabase extends Storage {

    private final String filename;

    public SQLiteDatabase(String filename, String table_prefix, String table_suffix) {
        super("sqlite", "org.sqlite.JDBC", "jdbc:sqlite:"+GlobalAPITB.getPlugin().getDataFolder()+"/modules/"+filename, Arrays.asList(null, null), table_prefix, table_suffix);
        this.filename = filename;
    }


    @Override
    protected void createTable(@Nullable InputStream resource) {
        super.createTable(plugin.getResource("storage/sqlite_players_create.sql"));
    }
}
