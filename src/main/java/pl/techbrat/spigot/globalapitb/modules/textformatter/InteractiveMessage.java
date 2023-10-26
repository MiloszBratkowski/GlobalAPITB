package pl.techbrat.spigot.globalapitb.modules.textformatter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.util.Arrays;

public class InteractiveMessage {


    /*
    *
    *%{text:"tekst"|hover:"text:tekst_hover"|click:"run:/clicable"}%
    * text:"tekst"|hover:"text:tekst_hover"|click:"run:/clicable"
    * text:"tekst"
    * hover:"text:tekst_hover"
    * click:"run:/clicable"
    * click:"url:/clicable"
    * */

    public TextComponent transformMessage(String message) {
        return transformMessage(message, '%');
    }
    public TextComponent transformMessage(String message, char symbol) {
        if (GlobalAPITB.getPlugin().getVersionSymbol() < 12) {
            return null;
        }
        TextComponent mainText = new TextComponent();
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == symbol) {
                if (i + 1 < message.length() && message.charAt(i + 1) == '{') {
                    startIndex = i;
                } else if (message.charAt(i - 1) == '}') {
                    endIndex = i;
                }
            }

            if (startIndex != -1 && endIndex != -1) {

                TextComponent component = new TextComponent("initialized");


                String expression = message.substring(startIndex + 2, endIndex - 2);
                String[] elements = expression.split("\"\\|");
                for (String element : elements) {
                    if (!element.endsWith("\"")) element += "\"";
                    if (element.startsWith("text:")) {
                        component = new TextComponent(TextComponent.fromLegacyText(element.substring(6, element.length() - 1)));
                    } else if (element.startsWith("hover:")) {
                        if (element.substring(7, element.length() - 1).startsWith("text:")) {
                            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(element.substring(12, element.length() - 1))));
                        }
                    } else if (element.startsWith("click:")) {
                        if (element.substring(7, element.length() - 1).startsWith("run:")) {
                            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, element.substring(11, element.length() - 1)));
                        } else if (element.substring(7, element.length() - 1).startsWith("suggest:")) {
                            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, element.substring(15, element.length() - 1)));
                        } else if (element.substring(7, element.length() - 1).startsWith("url:")) {
                            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, element.substring(11, element.length() - 1)));
                        }
                    }
                }
                mainText.addExtra(component);
                startIndex = -1;
                endIndex = -1;
            }
        }
        return mainText;
    }

    public String clearMessage(String message) {
        return clearMessage(message, '%');
    }

    public String clearMessage(String message, char symbol) {
        StringBuilder mainText = new StringBuilder();
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == symbol) {
                if (i + 1 < message.length() && message.charAt(i + 1) == '{') {
                    startIndex = i;
                } else if (message.charAt(i - 1) == '}') {
                    endIndex = i;
                }
            }

            if (startIndex != -1 && endIndex != -1) {
                String expression = message.substring(startIndex + 2, endIndex - 2);
                String[] elements = expression.split("\"\\|");
                for (String element : elements) {
                    if (!element.endsWith("\"")) element += "\"";
                    if (element.startsWith("text:")) {
                        mainText.append(element.substring(6, element.length() - 1));
                    }
                }
                startIndex = -1;
                endIndex = -1;
            }
        }
        return mainText.toString();
    }

    public void sendMessage(CommandSender sender, String message) {
        sendMessage(Arrays.asList(sender).toArray(new CommandSender[0]), message, '%');
    }

    public void sendMessage(CommandSender sender, String message, char symbol) {
        sendMessage(Arrays.asList(sender).toArray(new CommandSender[0]), message, symbol);
    }

    public void sendMessage(CommandSender[] senders, String message) {
        sendMessage(senders, message, '%');
    }

    public void sendMessage(CommandSender[] senders, String message, char symbol) {
        TextComponent component = transformMessage(message, symbol);
        if (component == null) {
            String clearedMessage = clearMessage(message, symbol);
            for (CommandSender sender : senders) {
                sender.sendMessage(clearedMessage);
            }
        } else {
            for (CommandSender sender : senders) {
                sender.spigot().sendMessage(component);
            }
        }
    }


    private final static String VERSION = "1.0";

    public static String getVersion() {
        return VERSION;
    }
}
