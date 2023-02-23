package de.kleingeschrieben.testcommunication.commands;

import de.kleingeschrieben.testcommunication.entitys.Response;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestCommand implements CommandExecutor {

    private RestTemplate restTemplate;
    public TestCommand(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ResponseEntity<Response> responseEntity = restTemplate.getForEntity("http://localhost:1405/ping/" + p.getUniqueId(), Response.class);
            if (!responseEntity.getBody().isSuccess()) {
                Bukkit.getLogger().info(responseEntity.getBody().getMessage());
                return false;
            }
        }
        return true;
    }
}
