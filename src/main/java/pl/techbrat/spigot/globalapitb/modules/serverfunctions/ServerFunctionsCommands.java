package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

public class ServerFunctionsCommands {
        private final GlobalAPITB plugin = GlobalAPITB.getPlugin();
        private final ServerFunctions serverFunctions;

        public ServerFunctionsCommands(ServerFunctions serverFunctions) {
            this.serverFunctions = serverFunctions;
        }
}
