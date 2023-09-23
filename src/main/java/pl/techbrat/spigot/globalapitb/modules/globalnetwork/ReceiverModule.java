package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReceiverModule {

    private final GlobalAPITB plugin;

    private final List<ReceiverListener> listeners = new ArrayList<ReceiverListener>();

    private final int port;

    private ServerSocket serverSocket;

    public ReceiverModule(int port) {
        this.port = port;
        this.plugin = GlobalAPITB.getPlugin();
        server();
    }

    private void server() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.debug("Registering receiver on port "+port+"...");
            try {
                serverSocket = new ServerSocket(port);
                Socket socket;
                InputStream inputStream;
                ObjectInputStream objectInputStream;
                DataPacket dataPacket;
                while (true) {
                    plugin.debug("Waiting for data packets...");
                    try {
                        socket = serverSocket.accept();
                        inputStream = socket.getInputStream();
                        if (inputStream != null) {
                            objectInputStream = new ObjectInputStream(inputStream);
                            dataPacket = (DataPacket) objectInputStream.readObject();
                            plugin.debug("Received data from " + socket);
                            for (ReceiverListener listener : listeners) {
                                listener.receivePacketEvent(dataPacket);
                            }
                        }
                        socket.close();
                    } catch (Exception e) {
                        plugin.getLogger().severe("An error occurred while receiving the packet!");
                        plugin.getLogger().severe("Info:");
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Receiver hasn't been registered!");
                plugin.getLogger().severe("Info: "+e.getLocalizedMessage());
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

    public void addListener(ReceiverListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ReceiverListener listener) {
        listeners.remove(listener);
    }
}
