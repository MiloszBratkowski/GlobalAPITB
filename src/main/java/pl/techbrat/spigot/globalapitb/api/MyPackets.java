package pl.techbrat.spigot.globalapitb.api;

import org.bukkit.Bukkit;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.DataPacket;
import pl.techbrat.spigot.globalapitb.modules.globalnetwork.ReceiverListener;

public class MyPackets implements ReceiverListener {
    @Override
    public void receivePacketEvent(DataPacket data) {
        if (data.getLabel().equals("broadcast")) {
            String message = (String) data.getData();
            Bukkit.broadcastMessage(message);
        }
    }
}
