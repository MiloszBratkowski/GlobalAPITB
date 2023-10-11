package pl.techbrat.spigot.globalapitb.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;
import pl.techbrat.spigot.globalapitb.modules.serverfunctions.*;
import pl.techbrat.spigot.globalapitb.modules.textformatter.TextFormatter;

public class APItest {
    public APItest() {
        GlobalAPITBManager api = GlobalAPITB.getAPI();

        TextFormatter module = api.getTextFormatterModule();

        module.getColorFormatter().

 }
}
