package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import pl.techbrat.spigot.globalapitb.modules.Module;

public class ServerFunctions extends Module {

    private BasicMethods basicMethods;
    private ServerMethods serverMethods;

    private final ServerFunctionsCommands commands;
    public ServerFunctions() {
        super("server_functions");

        commands = new ServerFunctionsCommands();
    }

    public BasicMethods getBasicMethods() {
        return basicMethods;
    }

    public ServerMethods getServerMethods() {
        return serverMethods;
    }

    public ServerFunctionsCommands getCommands() {
        return commands;
    }

    @Override
    protected void close() {
        super.close();
    }

    @Override
    protected void reload() {
        super.reload();

        basicMethods = new BasicMethods();
        serverMethods = new ServerMethods();
    }
}
