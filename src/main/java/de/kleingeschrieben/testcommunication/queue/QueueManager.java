package de.kleingeschrieben.testcommunication.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import de.kleingeschrieben.testcommunication.MessageSerializer;
import de.kleingeschrieben.testcommunication.entitys.PingPlayer;
import de.kleingeschrieben.testcommunication.entitys.PluginMessage;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class QueueManager {

    private MessageHandler messageHandler = new MessageHandler();
    private final MessageSerializer messageSerializer = new MessageSerializer();
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public void connectToRabbitMQ() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare("plugin.rpc.response", true, false, false, null);
        channel.queueDeclare("plugin.rpc.request", true, false, false, null);
    }

    public void sendMessageToQueue(String message, String queue) throws IOException {
        channel.basicPublish("", queue, null, message.getBytes());
    }

    public void startListeningToQueue() throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Object message = messageSerializer.deserializeObject(new String(body, StandardCharsets.UTF_8));
                    if (message instanceof PingPlayer) {
                        messageHandler.pingPlayer((PingPlayer) message);
                    } else {
                        Bukkit.getLogger().info("Klasse = " + message.getClass().getName());
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        channel.basicConsume("plugin.rpc.response", true, consumer);
    }

    public void disconnectFromRabbitMQ() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
