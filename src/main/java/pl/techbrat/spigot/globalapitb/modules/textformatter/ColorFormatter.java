package pl.techbrat.spigot.globalapitb.modules.textformatter;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorFormatter {
    private final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    public String hexToColor(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

    public String addColors(String message) {
        message = hexToColor(message);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String removeAllColors(String message) {
        while (message.startsWith(" ")) {
            message = message.substring(1);
        }
        StringBuilder newMessage = new StringBuilder();
        if (message.contains("&")) {
            boolean first = message.startsWith("&");
            String[] words = message.split("&");
            for (int i = 0; i < words.length; i++) {
                if ((i != 0 || first) && !words[i].isEmpty()) {
                    if (words[i].startsWith("#")) words[i] = words[i].substring(7);
                    else words[i] = words[i].substring(1);
                }
                newMessage.append(words[i]);
            }
        } else {
            newMessage = new StringBuilder(message);
        }
        return newMessage.toString();
    }

    public Color getColor(String colorName) {
        try {
            Field field = Class.forName("java.awt.Color").getField(colorName);
            return (Color) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }





}
