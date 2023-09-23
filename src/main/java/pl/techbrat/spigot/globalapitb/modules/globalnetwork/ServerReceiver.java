package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ServerReceiver {
    private final static HashMap<String, ServerReceiver> serverslist = new HashMap<>();

    public static ServerReceiver getServerReceiver(String name) {
        return serverslist.get(name);
    }

    public static Set<String> getRegisteredServers() {
        return serverslist.keySet();
    }

    private String name;
    private String host;
    private int port;

    public ServerReceiver(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public void register() {
        serverslist.put(name, this);
    }

    public void unregister() {
        serverslist.remove(name);
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
