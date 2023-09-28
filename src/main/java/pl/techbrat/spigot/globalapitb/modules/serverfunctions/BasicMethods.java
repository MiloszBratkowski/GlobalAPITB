package pl.techbrat.spigot.globalapitb.modules.serverfunctions;

public class BasicMethods {
    public boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
