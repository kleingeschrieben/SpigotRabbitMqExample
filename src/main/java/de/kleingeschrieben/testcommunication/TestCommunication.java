package de.kleingeschrieben.testcommunication;

import de.kleingeschrieben.testcommunication.commands.TestCommand;
import de.kleingeschrieben.testcommunication.queue.QueueManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class TestCommunication extends JavaPlugin {

    private final QueueManager client = new QueueManager();
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void onEnable() {
        try {
            client.connectToRabbitMQ();
            client.startListeningToQueue();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        this.getCommand("test").setExecutor(new TestCommand(restTemplate));
    }

    @Override
    public void onDisable() {
        try {
            client.disconnectFromRabbitMQ();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
