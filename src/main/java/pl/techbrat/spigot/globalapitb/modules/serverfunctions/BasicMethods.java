package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

import org.apache.commons.lang.Validate;

import java.util.Collection;
import java.util.Iterator;

public class BasicMethods {

    ServerFunctions module;

    BasicMethods(ServerFunctions module) {
        this.module = module;
    }


    public boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public <T extends Collection<? super String>> T copyPartialMatches(String token, Iterable<String> originals, T collection) throws UnsupportedOperationException, IllegalArgumentException {
        Validate.notNull(token, "Search token cannot be null");
        Validate.notNull(collection, "Collection cannot be null");
        Validate.notNull(originals, "Originals cannot be null");
        Iterator<String> var4 = originals.iterator();

        while(var4.hasNext()) {
            String string = var4.next();
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
            }
        }

        return collection;
    }

    public boolean startsWithIgnoreCase(String string, String prefix) throws IllegalArgumentException, NullPointerException {
        Validate.notNull(string, "Cannot check a null string for a match");
        return string.length() < prefix.length() ? false : string.regionMatches(true, 0, prefix, 0, prefix.length());
    }


    private final static String VERSION = "1.0";

    public static String getVersion() {
        return VERSION;
    }

}
