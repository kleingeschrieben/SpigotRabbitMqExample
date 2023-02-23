package de.kleingeschrieben.testcommunication.queue;

import de.kleingeschrieben.testcommunication.entitys.PingPlayer;
import org.bukkit.Bukkit;

public class MessageHandler {
    public void pingPlayer(PingPlayer pingPlayer) {
        Bukkit.getPlayer(pingPlayer.getPlayerUuid()).sendMessage(pingPlayer.getMessage());
    }
}
