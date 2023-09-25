package pl.techbrat.spigot.globalapitb.modules.textformatter;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.GlobalNetwork;

public class TextFormatterCommands {
    private final GlobalAPITB plugin = GlobalAPITB.getPlugin();
    private final TextFormatter textFormatter;

    protected TextFormatterCommands(TextFormatter textFormatter) {
        this.textFormatter = textFormatter;
    }
}
