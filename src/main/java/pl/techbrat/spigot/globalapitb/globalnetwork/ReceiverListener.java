package pl.techbrat.spigot.globalapitb.globalnetwork;

import java.io.ObjectInputStream;

public interface ServerReceiverListener {
    void serverReceiver(DataPacket data);
}
