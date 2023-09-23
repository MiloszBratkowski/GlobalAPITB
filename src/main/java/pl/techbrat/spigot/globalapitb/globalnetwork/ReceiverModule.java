package pl.techbrat.spigot.globalapitb.globalnetwork;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerEngine {

    private final GlobalAPITB plugin;

    private final List<ServerReceiverListener> listeners = new ArrayList<ServerReceiverListener>();

    private final int port;

    private ServerSocket serverSocket;

    public ServerEngine(int port) {
        this.port = port;
        this.plugin = GlobalAPITB.getPlugin();
        server();
    }

    private void server() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.debug("Registering receiver on port "+port+"...");
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    plugin.debug("Waiting for data packets...");
                    Socket socket = serverSocket.accept();
                    plugin.debug("Received data from " + socket);
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    for (ServerReceiverListener listener : listeners) {
                        listener.serverReceiver(objectInputStream);
                    }
                    socket.close();
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Receiver hasn't been registered!");
                plugin.getLogger().severe("Info: "+e.getMessage());
            }
        });
    }

    public void close() {
        try {
            if (!serverSocket.isClosed()) serverSocket.close();
        } catch (IOException e) {
            plugin.getLogger().severe("Receiver hasn't been unregistered!");
            plugin.getLogger().severe("Info: "+e.getMessage());
        }
    }

    public void addListener(ServerReceiverListener listener) {
        listeners.add(listener);
    }
}
