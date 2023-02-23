package de.kleingeschrieben.testcommunication.entitys;

import lombok.Data;

import java.io.Serializable;

@Data
public class PingPlayer extends PluginMessage implements Serializable  {
    private static final long serialVersionUID = 8582433437601788991L;
    private String message;
}
