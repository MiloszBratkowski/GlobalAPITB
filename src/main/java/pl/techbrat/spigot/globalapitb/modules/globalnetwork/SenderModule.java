package pl.techbrat.spigot.globalapitb.modules.globalnetwork;

import pl.techbrat.spigot.globalapitb.GlobalAPITB;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SenderModule {
    private GlobalAPITB plugin;


    private Socket socket;

    public SenderModule() {
        this.plugin = GlobalAPITB.getPlugin();
    }

    public void sendData(DataPacket data, ServerReceiver serverReceiver) {
        try {
            if (socket == null || socket.isClosed()) socket = new Socket(serverReceiver.getHost(), serverReceiver.getPort());
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            plugin.debug("Sending packet...");
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            plugin.getLogger().severe("Data hasn't been sent!");
            plugin.getLogger().severe("Info: "+e.getMessage());
        }
    }

    public boolean ping(ServerReceiver serverReceiver) {
        try {
            if (socket == null || socket.isClosed()) socket = new Socket(serverReceiver.getHost(), serverReceiver.getPort());
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(new DataPacket(plugin, "ping", null));
            objectOutputStream.flush();
            objectOutputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void close() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            plugin.getLogger().severe("Sender hasn't been unregistered!");
            plugin.getLogger().severe("Info: "+e.getMessage());
        }
    }

}

