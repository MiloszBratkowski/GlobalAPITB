package pl.techbrat.spigot.globalapitb.globalnetwork;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SenderModule {
    private GlobalAPITB plugin;


    private int port;
    private String host;

    private Socket socket;

    public SenderModule(int port, String host) {
        this.port = port;
        this.host = host;
        this.plugin = GlobalAPITB.getPlugin();
        client();
    }

    private void client() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                plugin.debug("Registering data packets sender to " + host + ":" + port);
                socket = new Socket(host, port);
                plugin.debug("Sender registered.");
            } catch (IOException e) {
                plugin.getLogger().severe("Data packets sender hasn't been registered!");
                plugin.getLogger().severe("Info: " + e.getMessage());
            }
        });
    }

    public void sendData(DataPacket data) {
        try {
            if (socket.isClosed()) socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            plugin.debug("Sending packet...");
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            plugin.getLogger().severe("Data hasn't been sent!");
            plugin.getLogger().severe("Info: "+e.getMessage());
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (!socket.isClosed()) socket.close();
        } catch (IOException e) {
            plugin.getLogger().severe("Sender hasn't been unregistered!");
            plugin.getLogger().severe("Info: "+e.getMessage());
        }
    }

}

