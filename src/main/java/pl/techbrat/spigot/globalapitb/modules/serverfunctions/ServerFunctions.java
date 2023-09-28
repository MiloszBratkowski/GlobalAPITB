package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import pl.techbrat.spigot.globalapitb.modules.Module;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.storage.ServerSaver;

public class ServerFunctions extends Module {

    private BasicMethods basicMethods;
    private ServerMethods serverMethods;
    private ServerSaver serverSaver;

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

        basicMethods = new BasicMethods();
        serverMethods = new ServerMethods();
        serverSaver = new ServerSaver(getConfig());
    }
}
