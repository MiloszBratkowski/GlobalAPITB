package pl.techbrat.spigot.globalapitb.modules.textformatter;

import pl.techbrat.spigot.globalapitb.modules.Module;

public class TextFormatter extends Module {

    private ColorFormatter colorFormatter;
    private InteractiveMessage interactiveMessage;

    private final TextFormatterCommands commands;

    public TextFormatter() {
        super("text_formatter");

        commands = new TextFormatterCommands();
    }

    public ColorFormatter getColorFormatter() {
        return colorFormatter;
    }
    public InteractiveMessage getInteractiveMessage() {
        return interactiveMessage;
    }

    public TextFormatterCommands getCommands() {
        return commands;
    }

    @Override
    protected void close() {
        super.close();
    }

    @Override
    protected void reload() {
        super.reload();

        colorFormatter = new ColorFormatter();
        interactiveMessage = new InteractiveMessage();
    }

    private final static String VERSION = "1.0.1";

    public static String getVersion() {
        return VERSION;
    }
}
