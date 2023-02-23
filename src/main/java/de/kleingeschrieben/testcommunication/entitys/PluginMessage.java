package de.kleingeschrieben.testcommunication.entitys;

import lombok.Data;

import java.io.Serializable;

@Data
public class PluginMessage implements Serializable {
    private String playerUuid;
    private String indicator;
}
