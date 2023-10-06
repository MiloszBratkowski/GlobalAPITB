package pl.techbrat.spigot.globalapitb.api;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.Module;
import pl.techbrat.spigot.globalapitb.modules.ModulesManager;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.DataPacket;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.ReceiverListener;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.ServerReceiver;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.ServerFunctions;
import pl.techbrat.spigot.globalapitb.modules.textformatter.TextFormatter;

import java.util.Arrays;
import java.util.List;

public class GlobalAPITBManager {

    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();

    private ModulesManager modulesManager;

    public GlobalAPITBManager() {
        modulesManager = plugin.getModulesManager();
    }

    public static GlobalAPITBManager getAPI() {
        return GlobalAPITB.getPlugin().getAPI();
    }

    public enum Module {
        GLOBAL_NETWORK("global_network"),
        SERVER_FUNCTIONS("server_functions"),
        TEXT_FORMATTER("text_formatter");

        private String module_label;

        Module(String module_label) {
            this.module_label = module_label;
        }

        public String getModuleLabel() {
            return module_label;
        }
    };

    public boolean isModuleEnabled(Module module) {
        return modulesManager.isEnabledModule(module.getModuleLabel());
    }

    public GlobalNetwork getGlobalNetworkModule() {
        return (GlobalNetwork) modulesManager.getModule("global_network");
    }

    public ServerFunctions getServerFunctionsModule() {
        return (ServerFunctions) modulesManager.getModule("server_functions");
    }

    public TextFormatter getTextFormatterModule() {
        return (TextFormatter) modulesManager.getModule("text_formatter");
    }

    public List<String> getAllModules() {
        return plugin.getModulesManager().getAllModules();
    }
}
