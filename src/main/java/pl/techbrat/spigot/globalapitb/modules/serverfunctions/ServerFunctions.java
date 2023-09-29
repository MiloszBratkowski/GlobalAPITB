package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import pl.techbrat.spigot.globalapitb.modules.Module;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage.ServerSaver;

public class ServerFunctions extends Module {

    private BasicMethods basicMethods;
    private ServerMethods serverMethods;
    private ServerSaver serverSaver;
    private PlayerManager playerManager;

    private final ServerFunctionsCommands commands;
    public ServerFunctions() {
        super("server_functions");

        commands = new ServerFunctionsCommands(this);

    }

    public BasicMethods getBasicMethods() {
        return basicMethods;
    }

    public ServerMethods getServerMethods() {
        return serverMethods;
    }

    public ServerSaver getServerSaver() {
        return serverSaver;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ServerFunctionsCommands getCommands() {
        return commands;
    }

    @Override
    protected void close() {
        super.close();
        serverSaver.close();
    }

    @Override
    protected void reload() {
        super.reload();
        if (serverSaver != null) serverSaver.close();

        basicMethods = new BasicMethods(this);
        serverMethods = new ServerMethods(this);
        serverSaver = new ServerSaver(this, getConfig());
        playerManager = new PlayerManager(this, getConfig().getConfigBoolean("use_uuid"));
    }
}
