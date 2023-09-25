package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import org.bukkit.scheduler.BukkitTask;
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

    private BukkitTask task;

    private final List<ReceiverListener> listeners = new ArrayList<ReceiverListener>();

    private final int port;

    private ServerSocket serverSocket;

    ReceiverModule(int port) {
        this.port = port;
        this.plugin = GlobalAPITB.getPlugin();
        server();
    }

    private void server() {
        task = plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.debug("Registering receiver on port "+port+"...");
            try {
                serverSocket = new ServerSocket(port);
                Socket socket;
                InputStream inputStream;
                ObjectInputStream objectInputStream;
                DataPacket dataPacket;
                plugin.debug("Receiver registered.");
                while (serverSocket != null && !serverSocket.isClosed()) {
                    plugin.debug("Waiting for data packets...");
                    try {
                        socket = serverSocket.accept();
                        inputStream = socket.getInputStream();
                        objectInputStream = new ObjectInputStream(inputStream);
                        dataPacket = (DataPacket) objectInputStream.readObject();
                        if (!dataPacket.getLabel().equals("ping")) {
                            plugin.debug("Received data from " + socket);
                            for (ReceiverListener listener : listeners) {
                                listener.receivePacketEvent(dataPacket);
                            }
                        } else {
                            plugin.debug("Received ping from another server by plugin: " + socket);
                        }
                        socket.close();
                    } catch (ClassNotFoundException e) {
                        if (!task.isCancelled()) {
                            plugin.getLogger().severe("An error occurred while receiving the packet!");
                            plugin.getLogger().severe("Info: "+e.getLocalizedMessage());
                        }
                    }
                }
            } catch (IOException e) {
                if (!task.isCancelled()) {
                    plugin.getLogger().severe("Receiver hasn't been registered!");
                    plugin.getLogger().severe("Info: " + e.getLocalizedMessage());
                    plugin.getModulesManager().close(plugin.getModulesManager().getModule("global_network"));
                }
            }
        });
    }

    void close() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                plugin.getLogger().severe("Receiver hasn't been unregistered!");
                plugin.getLogger().severe("Info: "+e.getMessage());
            }
        }
    }

    public void addListener(ReceiverListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ReceiverListener listener) {
        listeners.remove(listener);
    }
}
