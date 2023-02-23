package de.kleingeschrieben.testcommunication.entitys;

import lombok.Data;

@Data
public class Response {
    private boolean success;
    private String message = "";
}
